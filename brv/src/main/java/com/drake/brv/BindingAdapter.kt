/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("PropertyName")

package com.drake.brv


import android.content.Context
import android.util.NoSuchPropertyException
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.animation.*
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.item.ItemBind
import com.drake.brv.item.ItemExpand
import com.drake.brv.item.ItemHover
import com.drake.brv.item.ItemPosition
import com.drake.brv.listener.DefaultItemTouchCallback
import com.drake.brv.listener.OnBindViewHolderListener
import com.drake.brv.listener.OnHoverAttachListener
import com.drake.brv.listener.throttleClick
import com.drake.brv.utils.BRV

/**
 * < Android上最强大的RecyclerView框架 >
 *
 * 一行代码添加多类型 [addType]
 * 数据模型可以为任何对象 [models]
 * 通过接口实现来扩展功能 [com.drake.brv.item]
 * 快速添加触摸事件(防抖点击/快速点击/长按/选择/侧滑/拖拽)
 * 强大的分组/展开/折叠/粘性头部/递归深度/动画/组position [expandOrCollapse]
 * 自定义列表动画 [setAnimation] 默认动画 [com.drake.brv.animation]
 * 头布局/脚布局 [addHeader] [addFooter]
 * 快速设置分隔物
 * 缺省页 [PageRefreshLayout]
 * 下拉刷新/上拉加载/自动分页加载 [PageRefreshLayout]
 * 强大的选择状态 [setChecked] (切换模式/多选/单选/全选/取消全选/反选/选中数据集/选中数量/单选和多选模式切换)
 * 遵守高内聚低耦合原则, 支持功能配合使用, 代码简洁函数分组
 *
 * @property itemTouchHelper 等效于[RecyclerView.addItemDecoration]设置
 *
 */
@Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class BindingAdapter : RecyclerView.Adapter<BindingAdapter.BindingViewHolder>() {

    var rv: RecyclerView? = null
    var listBind = mutableSetOf<OnBindViewHolderListener>()

    companion object {
        /*
        即item的layout布局中的<variable>标签内定义变量名称

        示例:
            <variable
                name="m"
                type="com.drake.brv.sample.mod.CheckModel" />

        则应在Application中的[onCreate]函数内设置:
            BindingAdapter.modelId = BR.m
        */
        var modelId: Int = BRV.modelId
    }


    // <editor-fold desc="生命周期">
    private var onCreate: (BindingViewHolder.(viewType: Int) -> Unit)? = null
    private var onBind: (BindingViewHolder.() -> Unit)? = null
    private var onPayload: (BindingViewHolder.(Any) -> Unit)? = null
    private var onClick: (BindingViewHolder.(viewId: Int) -> Unit)? = null
    private var onLongClick: (BindingViewHolder.(viewId: Int) -> Unit)? = null
    private var onChecked: ((position: Int, checked: Boolean, allChecked: Boolean) -> Unit)? = null
    private var onToggle: ((position: Int, toggleModel: Boolean, end: Boolean) -> Unit)? = null


    /**
     * [onBindViewHolder]执行时回调
     */
    fun onBind(block: BindingViewHolder.() -> Unit) {
        onBind = block
    }

    /**
     * [onCreateViewHolder]执行时回调
     */
    fun onCreate(block: BindingViewHolder.(viewType: Int) -> Unit) {
        onCreate = block
    }

    /**
     * 增量更新回调, 和[onBind]等效
     * @param block 参数model等同于[BindingViewHolder.getModel]
     */
    fun onPayload(block: BindingViewHolder.(model: Any) -> Unit) {
        onPayload = block
    }

    // </editor-fold>1


    // <editor-fold desc="覆写函数">

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        val viewHolder =
                if (viewDataBinding == null) BindingViewHolder(parent.getView(viewType)) else BindingViewHolder(
                    viewDataBinding
                )
        onCreate?.invoke(viewHolder, viewType)
        return viewHolder
    }

    fun ViewGroup.getView(@LayoutRes layout: Int): View {
        return LayoutInflater.from(context).inflate(layout, this, false)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.bind(getModel(position))
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            onPayload?.invoke(holder, payloads[0])
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {

        val model = getModel<Any>(position)
        val modelClass: Class<*> = model.javaClass
        return (typePool[modelClass]?.invoke(model, position)
                ?: throw NoSuchPropertyException("please add item model type : addType<${model.javaClass.simpleName}>(R.layout.item)"))
    }

    override fun getItemCount() = headerCount + modelCount + footerCount

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.rv = recyclerView
        if (context == null) {
            context = recyclerView.context
        }
        itemTouchHelper?.attachToRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: BindingViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutPosition = holder.layoutPosition
        if (animationEnabled && lastPosition < layoutPosition) {
            itemAnimation.onItemEnterAnimation(holder.itemView)
            lastPosition = layoutPosition
        }
    }

    // </editor-fold>


    // <editor-fold desc="多类型">

    val typePool = mutableMapOf<Class<*>, Any.(Int) -> Int>()

    /**
     * 添加多类型
     */
    inline fun <reified M> addType(@LayoutRes layout: Int) {
        typePool[M::class.java] = { layout }
    }

    /**
     * 通过回调函数添加多类型, 一对多多类型
     */
    inline fun <reified M> addType(noinline block: M.(Int) -> Int) {
        typePool[M::class.java] = block as Any.(Int) -> Int
    }
    // </editor-fold>


    // <editor-fold desc="触摸事件">
    private val clickableIds = SparseBooleanArray()
    private val longClickableIds = ArrayList<Int>()

    // 自定义ItemTouchHelper即可设置该属性
    var itemTouchHelper: ItemTouchHelper? = ItemTouchHelper(DefaultItemTouchCallback(this))
        set(value) {
            if (value == null) field?.attachToRecyclerView(null) else value.attachToRecyclerView(rv)
            field = value
        }

    // 防抖间隔时间, 单位毫秒
    var clickPeriod: Long = 500

    /**
     * 添加点击事件
     * 默认500ms防抖, 修改[clickPeriod]属性可以全局设置间隔时间, 单位毫秒
     */
    fun addClickable(@IdRes vararg id: Int) {
        for (i in id) {
            clickableIds.put(i, false)
        }
    }

    /**
     * 指定Id的视图将被监听点击事件(未使用防抖)
     */
    fun addFastClickable(@IdRes vararg id: Int) {
        for (i in id) {
            clickableIds.put(i, true)
        }
    }

    /**
     * 指定Id的视图将被监听长按事件
     */
    fun addLongClickable(@IdRes vararg id: Int) {
        for (i in id) {
            longClickableIds.add(i)
        }
    }

    /**
     * 点击事件回调
     * @param id 添加监听点击事件视图的Id, 等效于[addClickable]
     */
    fun onClick(@IdRes vararg id: Int, block: BindingViewHolder.(id: Int) -> Unit) {
        for (i in id) {
            clickableIds.put(i, false)
        }
        onClick = block
    }

    /**
     * 长按点击事件回调
     * @param id 添加监听长按事件视图的Id, 等效于[addLongClickable]
     */
    fun onLongClick(@IdRes vararg id: Int, block: BindingViewHolder.(id: Int) -> Unit) {
        for (i in id) {
            longClickableIds.add(i)
        }
        onLongClick = block
    }


    // </editor-fold>


    // <editor-fold desc="列表动画">

    private var itemAnimation: ItemAnimation = AlphaItemAnimation()
    private var lastPosition = -1
    private var isFirst = true

    // 是否启用条目动画
    var animationEnabled = false

    /**
     * 自定义条目的动画样式
     */
    fun setAnimation(itemAnimation: ItemAnimation) {
        this.animationEnabled = true
        this.itemAnimation = itemAnimation
    }

    /**
     * 设置当前库自带的条目的动画样式
     */
    fun setAnimation(animationType: AnimationType) {
        this.animationEnabled = true
        when (animationType) {
            AnimationType.ALPHA -> this.itemAnimation = AlphaItemAnimation()
            AnimationType.SCALE -> this.itemAnimation = ScaleItemAnimation()
            AnimationType.SLIDE_BOTTOM -> this.itemAnimation = SlideBottomItemAnimation()
            AnimationType.SLIDE_LEFT -> this.itemAnimation = SlideLeftItemAnimation()
            AnimationType.SLIDE_RIGHT -> this.itemAnimation = SlideRightItemAnimation()
        }
    }


    // </editor-fold>

    // <editor-fold desc="头布局">

    // 头布局的数据模型
    var headers: List<Any?> = mutableListOf()
        set(value) {
            field = value.toMutableList()
            notifyDataSetChanged()
        }

    // 头布局数量
    val headerCount: Int get() = headers.size

    fun addHeader(model: Any?, @IntRange(from = -1) index: Int = -1, animation: Boolean = false) {

        if (index == -1) {
            (headers as MutableList).add(0, model)
            if (animation) notifyItemInserted(0)
        } else if (index <= headerCount) {
            (headers as MutableList).add(index, model)
            if (animation) notifyItemInserted(index)
        }

        if (!animation) notifyDataSetChanged()
    }

    fun removeHeader(model: Any?, animation: Boolean = false) {
        if (headerCount != 0 && headers.contains(model)) {
            val headerIndex = headers.indexOf(model)

            (headers as MutableList).remove(model)
            if (animation) {
                notifyItemRemoved(headerIndex)
            } else notifyDataSetChanged()
        }
    }


    fun removeHeaderAt(@IntRange(from = 0) index: Int = 0, animation: Boolean = false) {
        if (headerCount <= 0 || headerCount < index) return
        (headers as MutableList).removeAt(index)
        if (animation) notifyItemRemoved(index) else notifyDataSetChanged()
    }

    fun clearHeader(animation: Boolean = false) {
        if (headers.isNotEmpty()) {
            val headerCount = this.headerCount
            (headers as MutableList).clear()
            if (animation) notifyItemRangeRemoved(0, headerCount) else notifyDataSetChanged()
        }
    }


    fun isHeader(@IntRange(from = 0) position: Int): Boolean =
            (headerCount > 0 && position < headerCount)

    // </editor-fold>


    // <editor-fold desc="脚布局">


    var footers: List<Any?> = mutableListOf()
        set(value) {

            field = value.toMutableList()
            notifyDataSetChanged()

            if (isFirst) {
                lastPosition = -1
                isFirst = false
            } else {
                lastPosition = itemCount - 1
            }
        }


    val footerCount: Int get() = footers.size


    fun addFooter(model: Any?, @IntRange(from = -1) index: Int = -1, animation: Boolean = false) {
        if (index == -1) {
            (footers as MutableList).add(model)
            if (animation) {
                notifyItemInserted(itemCount)
            }
        } else if (index <= footerCount) {
            (footers as MutableList).add(index, model)
            if (animation) {
                notifyItemInserted(headerCount + modelCount + index)
            }
        }

        if (!animation) {
            notifyDataSetChanged()
        }
    }

    fun removeFooter(model: Any?, animation: Boolean = false) {
        if (footerCount != 0 && footers.contains(model)) {
            val footerIndex = headerCount + modelCount + footers.indexOf(model)
            (footers as MutableList).remove(model)
            if (animation) {
                notifyItemRemoved(footerIndex)
            } else notifyDataSetChanged()
        }
    }

    fun removeFooterAt(@IntRange(from = -1) index: Int = -1, animation: Boolean = false) {

        if (footerCount <= 0 || footerCount < index) return

        if (index == -1) {
            (footers as MutableList).removeAt(0)
            if (animation) {
                notifyItemRemoved(headerCount + modelCount)
            }
        } else {
            (footers as MutableList).removeAt(index)

            if (animation) {
                notifyItemRemoved(headerCount + modelCount + index)
            }
        }

        if (!animation) {
            notifyDataSetChanged()
        }
    }

    fun clearFooter(animation: Boolean = false) {
        if (footers.isNotEmpty()) {
            val footerCount = this.footerCount
            (footers as MutableList).clear()
            if (animation) {
                notifyItemRangeRemoved(headerCount + modelCount, itemCount + footerCount)
            } else notifyDataSetChanged()
        }
    }


    fun isFooter(@IntRange(from = 0) position: Int): Boolean =
            (footerCount > 0 && position >= headerCount + modelCount && position < itemCount)

    // </editor-fold>


    // <editor-fold desc="数据">

    // 数据模型数量(不包含头布局和脚布局)
    val modelCount: Int
        get() {
            return if (models == null) 0 else models!!.size
        }

    // 数据模型集合
    var models: List<Any?>? = null
        set(value) {
            field = when (value) {
                is ArrayList -> flat(value)
                is List -> flat(value.toMutableList())
                else -> null
            }
            notifyDataSetChanged()
            checkedPosition.clear()
            if (isFirst) {
                lastPosition = -1
                isFirst = false
            } else {
                lastPosition = itemCount - 1
            }
        }

    // 可增删的数据模型集合
    var mutable
        get() = models as ArrayList
        set(value) {
            models = value
        }

    private fun flat(
        list: MutableList<Any?>,
        expand: Boolean? = null,
        @IntRange(from = -1) depth: Int = 0
    ): MutableList<Any?> {

        if (list.isEmpty()) return list
        val arrayList = ArrayList(list)
        list.clear()

        arrayList.forEachIndexed { index, item ->
            list.add(item)
            if (item is ItemExpand) {
                item.itemGroupPosition = index
                var nextDepth = depth
                if (expand != null && depth != 0) {
                    item.itemExpand = expand
                    if (depth > 0) nextDepth -= 1
                }

                val itemSublist = item.itemSublist
                val sublist: MutableList<Any?>? =
                        if (itemSublist is ArrayList) itemSublist else itemSublist?.toMutableList()
                if (!sublist.isNullOrEmpty() && (item.itemExpand || (depth != 0 && expand != null))) {
                    val nestedList = flat(sublist, expand, nextDepth)
                    list.addAll(nestedList)
                }
            }
        }
        return list
    }

    fun isModel(@IntRange(from = 0) position: Int): Boolean =
            !(isHeader(position) || isFooter(position))

    /**
     * 根据索引返回数据模型, 如果不存在该模型则返回Null
     */
    inline fun <reified M> getModelOrNull(position: Int): M? {
        return when {
            isHeader(position) -> headers[position] as? M
            isFooter(position) -> footers[position - headerCount - modelCount] as? M
            else -> models?.let { it.getOrNull(position - headerCount) as? M }
        }
    }

    /**
     * 根据索引返回数据模型, 不存在该模型则抛出异常
     */
    fun <M> getModel(@IntRange(from = 0) position: Int): M {
        return when {
            isHeader(position) -> headers[position] as M
            isFooter(position) -> footers[position - headerCount - modelCount] as M
            else -> models!!.let { it[position - headerCount] as M }
        }
    }


    /**
     * 添加新的数据
     */
    fun addModels(models: List<Any?>?, animation: Boolean = true) {

        if (models.isNullOrEmpty()) return

        val data: MutableList<Any?> = when (models) {
            is ArrayList -> models
            else -> models.toMutableList()
        }

        if (this.models.isNullOrEmpty()) {
            this.models = flat(data)
            notifyDataSetChanged()
        } else {
            val realModels = this.models as MutableList
            realModels.addAll(flat(data))
            if (animation) {
                notifyItemRangeInserted(headerCount + modelCount - data.size, data.size)
            } else {
                notifyDataSetChanged()
            }
        }
    }


    /**
     * adapter position  convert to  model position
     * @receiver Int model of position
     * @return Int
     */
    fun Int.toModelPosition(): Int = this - headerCount

    // </editor-fold>

    //<editor-fold desc="切换模式">
    var toggleMode = false // 是否开启切换模式
        private set

    /**
     * 切换模式, 切换模式为遍历每个item
     * @see [onToggle]
     */
    fun toggle() {
        onToggle?.let {
            toggleMode = !toggleMode
            for (position in 0 until itemCount) {
                if (position != itemCount - 1) it.invoke(position, toggleMode, false)
                else it.invoke(position, toggleMode, true)
            }
        }
    }


    /**
     * 设置切换模式, 切换模式为遍历每个item
     * @see [onToggle]
     * @param toggleMode Boolean
     */
    fun toggle(toggleMode: Boolean) {
        if (toggleMode != this.toggleMode) toggle()
    }


    /**
     * 切换模式事件回调
     *
     * @param position 条目位置
     * @param toggleMode 切换模式
     * @param end 全部条目已经切换完成
     */
    fun onToggle(block: (position: Int, toggleModel: Boolean, end: Boolean) -> Unit) {
        onToggle = block
    }
    //</editor-fold>


    // <editor-fold desc="选择模式">

    private var checkableItemTypeList: List<Int>? = null

    val checkedPosition = mutableListOf<Int>() // 已选择的位置

    val checkedCount: Int get() = checkedPosition.size

    private val checkableCount: Int
        get() {
            var count = 0
            if (checkableItemTypeList == null) {
                return models!!.size
            } else {
                for (i in 0 until itemCount) {
                    if (checkableItemTypeList!!.contains(getItemViewType(i))) {
                        count++
                    }
                }
            }
            return count
        }

    var singleMode = false
        set(value) {
            field = value
            val size = checkedPosition.size
            if (field && size > 1) {
                for (i in 0 until size - 1) setChecked(checkedPosition[0], false)
            }
        }

    /**
     * 返回被选中的条目对应的数据模型集合
     */
    fun <M> getCheckedModels(): List<M> {
        val checkedModels = ArrayList<M>()
        for (position in this.checkedPosition) checkedModels.add(getModel(position))
        return checkedModels
    }

    /**
     * 设置可以被选择的item类型
     * @see setChecked
     */
    fun setCheckableType(@LayoutRes vararg checkableItemType: Int) {
        checkableItemTypeList = checkableItemType.toMutableList()
    }


    /**
     * 单选模式下不支持全选, 但支持取消全部选择
     * @param checked true为全选, false 取消全部选择
     */
    fun checkedAll(checked: Boolean = true) {
        if (checked) {
            if (singleMode) return
            for (i in 0 until itemCount) {
                if (!checkedPosition.contains(i)) {
                    setChecked(i, true)
                }
            }
        } else {
            for (i in 0 until itemCount) {
                if (checkedPosition.contains(i)) setChecked(i, false)
            }
        }
    }

    /**
     * 是否全选
     */
    fun isCheckedAll(): Boolean = checkedCount == checkableCount


    /**
     * 反选
     */
    fun checkedReverse() {
        if (singleMode) return
        for (i in 0 until itemCount) {
            if (checkedPosition.contains(i)) {
                setChecked(i, false)
            } else {
                setChecked(i, true)
            }
        }
    }


    /**
     * 设置选中
     */
    fun setChecked(@IntRange(from = 0) position: Int, checked: Boolean) {

        if ((checkedPosition.contains(position) && checked) || (!checked && !checkedPosition.contains(
                    position
                ))
        ) return

        val itemViewType = getItemViewType(position)

        if (checkableItemTypeList != null && checkableItemTypeList!!.contains(itemViewType)) return

        if (onChecked == null) return

        if (checked) checkedPosition.add(position)
        else checkedPosition.remove(Integer.valueOf(position))

        if (singleMode && checked && checkedPosition.size > 1) {
            setChecked(checkedPosition[0], false)
        }
        onChecked?.invoke(position, checked, isCheckedAll())
    }

    /**
     * 切换选中
     */
    fun checkedSwitch(@IntRange(from = 0) position: Int) {
        if (checkedPosition.contains(position)) {
            setChecked(position, false)
        } else {
            setChecked(position, true)
        }
    }


    /**
     * 条目选中事件回调
     */
    fun onChecked(block: (position: Int, checked: Boolean, allChecked: Boolean) -> Unit) {
        onChecked = block
    }

    // </editor-fold>

    //<editor-fold desc="分组">

    private var previousExpandPosition = 0
    private var onExpand: (BindingViewHolder.(Boolean) -> Unit)? = null

    // 分组展开和折叠是否启用动画
    var expandAnimationEnabled = true

    // 只允许一个条目展开(展开当前条目就会折叠上个条目)
    var singleExpandMode = false

    fun onExpand(block: BindingViewHolder.(Boolean) -> Unit) {
        this.onExpand = block
    }

    /**
     * 展开
     * @param position 指定position的条目折叠
     * @param scrollTop 展开同时当前条目滑动到顶部
     * @param depth 递归展开子项的深度, 如等于-1则代表展开所有子项, 0表示仅展开当前
     * @return 展开后增加的条目数量
     */
    fun expand(
        @IntRange(from = 0) position: Int,
        scrollTop: Boolean = false,
        @IntRange(from = -1) depth: Int = 0
    ): Int {
        val holder = rv?.findViewHolderForLayoutPosition(position) as? BindingViewHolder ?: return 0
        return holder.expand(scrollTop, depth)
    }

    /**
     * 折叠
     * @param position 指定position的条目折叠
     * @param depth 递归展开子项的深度, 如等于-1则代表展开所有子项, 0表示仅展开当前
     * @return 折叠后消失的条目数量
     */
    fun collapse(@IntRange(from = 0) position: Int, @IntRange(from = -1) depth: Int = 0): Int {
        val holder = rv?.findViewHolderForLayoutPosition(position) as? BindingViewHolder ?: return 0
        return holder.collapse(depth)
    }

    /**
     * 展开或折叠
     * @param scrollTop 展开同时当前条目滑动到顶部
     * @param depth 递归展开子项的深度, 如等于-1则代表展开所有子项, 0表示仅展开当前
     * @return 展开或折叠后变动的条目数量
     */
    fun expandOrCollapse(
        @IntRange(from = 0) position: Int,
        scrollTop: Boolean = false,
        @IntRange(from = -1) depth: Int = 0
    ): Int {
        val holder = rv?.findViewHolderForLayoutPosition(position) as? BindingViewHolder ?: return 0
        return holder.expandOrCollapse(scrollTop, depth)
    }

    //</editor-fold>

    inner class BindingViewHolder : RecyclerView.ViewHolder {

        lateinit var _data: Any private set
        var context: Context = this@BindingAdapter.context!!
        val adapter: BindingAdapter = this@BindingAdapter
        val modelPosition get() = layoutPosition - headerCount

        private var viewDataBinding: ViewDataBinding? = null

        constructor(itemView: View) : super(itemView)

        constructor(viewDataBinding: ViewDataBinding) : super(viewDataBinding.root) {
            this.viewDataBinding = viewDataBinding
        }

        init {
            for (i in 0 until clickableIds.size()) {
                val view = itemView.findViewById<View>(clickableIds.keyAt(i)) ?: continue
                if (clickableIds.valueAt(i)) {
                    view.setOnClickListener {
                        onClick?.invoke(this@BindingViewHolder, view.id)
                    }
                } else {
                    view.throttleClick(clickPeriod) {
                        onClick?.invoke(this@BindingViewHolder, view.id)
                    }
                }
            }

            for (longClickableId in longClickableIds) {
                val view = itemView.findViewById<View>(longClickableId) ?: continue
                view.setOnLongClickListener {
                    onLongClick?.invoke(this, view.id)
                    true
                }
            }
        }

        internal fun bind(model: Any) {
            this._data = model

            listBind.forEach {
                it.onBindViewHolder(rv!!, adapter, this, adapterPosition)
            }

            if (model is ItemPosition) {
                model.itemPosition = layoutPosition
            }

            if (model is ItemBind) {
                model.onBind(this)
            }

            onBind?.invoke(this@BindingViewHolder)
            try {
                viewDataBinding?.setVariable(modelId, model)
            } catch (e: Exception) {
                val message =
                        "${e.message} at file(${context.resources.getResourceEntryName(itemViewType)}.xml:0)"
                Exception(message).printStackTrace()
            }
            viewDataBinding?.executePendingBindings()
        }


        /**
         * 返回匹配泛型的数据绑定对象ViewDataBinding
         */
        fun <B : ViewDataBinding> getBinding(): B = viewDataBinding as B

        /**
         * 查找ItemView上的视图
         */
        fun <V : View> findView(@IdRes id: Int): V = itemView.findViewById(id)

        /**
         * 返回数据模型
         */
        fun <M> getModel(): M = _data as M

        /**
         * 返回数据模型, 如果不匹配泛型则返回Null
         */
        inline fun <reified M> getModelOrNull(): M? = _data as? M

        //<editor-fold desc="分组">
        /**
         * 展开子项
         * @param scrollTop 展开同时当前条目滑动到顶部
         * @param depth 递归展开子项的深度, 如等于-1则代表展开所有子项, 0表示仅展开当前
         * @return 展开后新增的条目数量
         */
        fun expand(scrollTop: Boolean = true, @IntRange(from = -1) depth: Int = 0): Int {
            val itemExpand = getModelOrNull<ItemExpand>()

            val realPosition =
                    if (singleExpandMode && findParentPosition() != previousExpandPosition) {
                        if (layoutPosition > previousExpandPosition) {
                            layoutPosition - adapter.collapse(previousExpandPosition)
                        } else {
                            adapter.collapse(previousExpandPosition)
                            layoutPosition
                        }
                    } else layoutPosition

            onExpand?.invoke(this, true)

            return if (itemExpand != null && !itemExpand.itemExpand) {
                val itemSublist = itemExpand.itemSublist
                itemExpand.itemExpand = true

                if (itemSublist.isNullOrEmpty()) {
                    notifyItemChanged(realPosition)
                    previousExpandPosition = realPosition
                    0
                } else {
                    val sublist =
                            if (itemSublist is ArrayList) itemSublist else itemSublist.toMutableList()
                    val sublistFlat = flat(sublist, true, depth)

                    (this@BindingAdapter.models as MutableList).addAll(
                        realPosition + 1,
                        sublistFlat
                    )
                    if (expandAnimationEnabled) {
                        notifyItemChanged(realPosition)
                        notifyItemRangeInserted(realPosition + 1, sublistFlat.size)
                    } else {
                        notifyDataSetChanged()
                    }
                    if (scrollTop) {
                        rv?.postDelayed({
                                            rv?.smoothScrollToPosition(realPosition)
                                        }, 200)
                    }
                    previousExpandPosition = realPosition
                    sublistFlat.size
                }
            } else 0
        }

        /**
         * 折叠子项
         * @param depth 递归折叠子项的深度, 如等于-1则代表展开所有子项, 0表示仅展开当前
         * @return 折叠后减少的条目数量
         */
        fun collapse(@IntRange(from = -1) depth: Int = 0): Int {
            val itemExpand = getModelOrNull<ItemExpand>()

            onExpand?.invoke(this, false)

            return if (itemExpand != null && itemExpand.itemExpand) {
                val itemSublist = itemExpand.itemSublist
                itemExpand.itemExpand = false

                if (itemSublist.isNullOrEmpty()) {
                    notifyItemChanged(layoutPosition, itemExpand)
                    0
                } else {
                    val sublist =
                            if (itemSublist is ArrayList) itemSublist else itemSublist.toMutableList()
                    val sublistFlat = flat(sublist, false, depth)
                    (this@BindingAdapter.models as MutableList).removeAll(sublistFlat)
                    if (expandAnimationEnabled) {
                        notifyItemChanged(layoutPosition, itemExpand)
                        notifyItemRangeRemoved(layoutPosition + 1, sublistFlat.size)
                    } else {
                        notifyDataSetChanged()
                    }
                    sublistFlat.size
                }
            } else 0
        }

        /**
         * 展开或折叠子项
         * @param scrollTop 展开同时当前条目滑动到顶部
         * @param depth 递归展开子项的深度, 如等于-1则代表展开所有子项, 0表示仅展开当前
         * @return 展开|折叠后变动的条目数量
         */
        fun expandOrCollapse(scrollTop: Boolean = false, @IntRange(from = -1) depth: Int = 0): Int {
            val itemExpand = getModelOrNull<ItemExpand>()
            return if (itemExpand != null) {
                if (itemExpand.itemExpand) collapse(depth) else expand(scrollTop, depth)
            } else 0
        }

        /**
         * 查找分组中的父项位置
         * @return -1 表示不存在父项
         */
        fun findParentPosition(): Int {
            this@BindingAdapter.models?.forEachIndexed { index, item ->
                if (item is ItemExpand && item.itemSublist?.contains(_data) == true) {
                    return index
                }
            }
            return -1
        }

        /**
         * 查找分组中的父项ViewHolder
         * @return null表示不存在父项
         */
        fun findParentViewHolder(): BindingViewHolder? {
            this@BindingAdapter.models?.forEachIndexed { index, item ->
                if (item is ItemExpand && item.itemSublist?.contains(_data) == true) {
                    return rv?.findViewHolderForLayoutPosition(index) as? BindingViewHolder
                }
            }
            return null
        }

        //</editor-fold>
    }

    //<editor-fold desc="悬停">

    // 是否启用条目悬停
    var hoverEnabled = true

    var onHoverAttachListener: OnHoverAttachListener? = null

    fun isHover(position: Int): Boolean {
        val model = getModelOrNull<ItemHover>(position)
        return model != null && model.itemHover && hoverEnabled
    }
    //</editor-fold>
}