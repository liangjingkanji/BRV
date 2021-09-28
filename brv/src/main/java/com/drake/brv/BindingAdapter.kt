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
import kotlin.math.min

/**
 * < Android上最强大的RecyclerView框架 >
 *
 * 1. 一行代码添加多类型 [addType]
 * 2. 数据模型可以为任何对象 [models]
 * 3. 通过接口实现来扩展功能 [com.drake.brv.item]
 * 4. 快速添加触摸事件(防抖点击/快速点击/长按/选择/侧滑/拖拽)
 * 5. 强大的分组/展开/折叠/粘性头部/递归深度/动画/组position [expandOrCollapse]
 * 6. 自定义列表动画 [setAnimation] 默认动画 [com.drake.brv.animation]
 * 7. 头布局/脚布局 [addHeader] [addFooter]
 * 8. 快速设置分隔物
 * 9. 缺省页 [PageRefreshLayout]
 * 10. 下拉刷新/上拉加载/自动分页加载 [PageRefreshLayout]
 * 11. 强大的选择状态 [setChecked] (切换模式/多选/单选/全选/取消全选/反选/选中数据集/选中数量/单选和多选模式切换)
 * 12. 遵守高内聚低耦合原则, 支持功能配合使用, 代码简洁函数分组
 *
 * @property itemTouchHelper 等效于[RecyclerView.addItemDecoration]设置
 *
 */
@Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
open class BindingAdapter : RecyclerView.Adapter<BindingAdapter.BindingViewHolder>() {

    /** 当前Adapter被setAdapter才不为null */
    var rv: RecyclerView? = null

    /** onBindViewHolder触发监听器集合 */
    var onBindViewHolders = mutableListOf<OnBindViewHolderListener>()

    companion object {
        /**
         * 即item的layout布局中的<variable>标签内定义变量名称
         * 示例:
         * ```
         * <variable
         *      name="m"
         *      type="com.drake.brv.sample.mod.CheckModel" />
         * ```
         * 则应在Application中的onCreate函数内设置:
         * `BindingAdapter.modelId = BR.m`
         */
        @Deprecated("函数优化", ReplaceWith("BR.modelId", "com.drake.brv.utils.BRV"))
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

    // </editor-fold>


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

    /** 类型池 */
    val typePool = mutableMapOf<Class<*>, Any.(Int) -> Int>()

    /** 添加多类型 */
    inline fun <reified M> addType(@LayoutRes layout: Int) {
        typePool[M::class.java] = { layout }
    }

    /** 通过回调函数添加多类型, 一对多多类型 */
    inline fun <reified M> addType(noinline block: M.(Int) -> Int) {
        typePool[M::class.java] = block as Any.(Int) -> Int
    }
    // </editor-fold>


    // <editor-fold desc="触摸事件">
    private val clickListeners = HashMap<Int, Pair<(BindingViewHolder.(Int) -> Unit)?, Boolean>>()
    private val longClickListeners = HashMap<Int, (BindingViewHolder.(Int) -> Unit)?>()

    /** 自定义ItemTouchHelper即可设置该属性 */
    var itemTouchHelper: ItemTouchHelper? = ItemTouchHelper(DefaultItemTouchCallback())
        set(value) {
            if (value == null) field?.attachToRecyclerView(null) else value.attachToRecyclerView(rv)
            field = value
        }

    /** 防抖动点击事件的间隔时间, 单位毫秒 */
    var clickThrottle: Long = BRV.clickThrottle

    /** 防抖动点击事件的间隔时间, 单位毫秒. 本函数已废弃 */
    @Deprecated("Rename to clickThrottle", ReplaceWith("clickThrottle"))
    var clickPeriod: Long
        get() = clickThrottle
        set(value) {
            clickThrottle = value
        }

    /**
     * 添加点击事件
     * 默认500ms防抖, 修改[clickThrottle]属性可以全局设置间隔时间, 单位毫秒
     * 默认会回调最后一个onClick监听函数
     */
    @Deprecated(
        "点击事件现在是指定Id对应一个回调函数, 相同Id覆盖",
        ReplaceWith("onClick(*id){  }"),
        DeprecationLevel.WARNING
    )
    fun addClickable(@IdRes vararg id: Int) {
        for (i in id) {
            clickListeners[i] = Pair(null, false)
        }
    }

    /**
     * 指定Id的视图将被监听点击事件(未使用防抖)
     * 默认会回调最后一个onClick监听函数
     */
    @Deprecated(
        "点击事件现在是指定Id对应一个回调函数, 相同Id覆盖",
        ReplaceWith("onFastClick(*id){  }"),
        DeprecationLevel.WARNING
    )
    fun addFastClickable(@IdRes vararg id: Int) {
        for (i in id) {
            clickListeners[i] = Pair(null, true)
        }
    }

    /**
     * 指定Id的视图将被监听长按事件
     * 默认会回调最后一个onLongClick监听函数
     */
    @Deprecated(
        "点击事件现在是指定Id对应一个回调函数, 相同Id覆盖",
        ReplaceWith("onLongClick(*id){  }"),
        DeprecationLevel.WARNING
    )
    fun addLongClickable(@IdRes vararg id: Int) {
        for (i in id) {
            longClickListeners[i] = null
        }
    }

    /**
     * 监听指定Id控件的点击事件, 包含防抖动
     */
    fun onClick(@IdRes vararg id: Int, block: BindingViewHolder.(id: Int) -> Unit) {
        for (i in id) {
            clickListeners[i] = Pair(block, false)
        }
        onClick = block
    }

    /**
     * 监听指定Id控件的点击事件
     */
    fun onFastClick(@IdRes vararg id: Int, block: BindingViewHolder.(id: Int) -> Unit) {
        for (i in id) {
            clickListeners[i] = Pair(block, true)
        }
        onClick = block
    }

    /**
     * 长按点击事件回调
     */
    fun onLongClick(@IdRes vararg id: Int, block: BindingViewHolder.(id: Int) -> Unit) {
        for (i in id) {
            longClickListeners[i] = block
        }
        onLongClick = block
    }

    /**
     * 添加点击事件, 点击启用防抖动
     */
    fun @receiver:IdRes Int.onClick(listener: BindingViewHolder.(Int) -> Unit) {
        clickListeners[this] = Pair(listener, false)
    }

    /**
     * 添加点击事件
     */
    fun @receiver:IdRes Int.onFastClick(listener: BindingViewHolder.(Int) -> Unit) {
        clickListeners[this] = Pair(listener, true)
    }

    /**
     * 添加长按事件
     */
    fun @receiver:IdRes Int.onLongClick(listener: BindingViewHolder.(Int) -> Unit) {
        longClickListeners[this] = listener
    }

    // </editor-fold>


    // <editor-fold desc="列表动画">

    private var itemAnimation: ItemAnimation = AlphaItemAnimation()
    private var lastPosition = -1
    private var isFirst = true

    /** 是否启用条目动画 */
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

    /** 头布局的数据模型 */
    var headers: List<Any?> = mutableListOf()
        set(value) {
            field = value.toMutableList()
            notifyDataSetChanged()
        }

    /** 头布局数量 */
    val headerCount: Int get() = headers.size

    /**
     * 添加头布局
     * @param model 数据
     * @param index 插入头布局中的索引
     * @param animation 是否显示动画
     */
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

    /**
     * 通过数据删除头布局
     */
    fun removeHeader(model: Any?, animation: Boolean = false) {
        if (headerCount != 0 && headers.contains(model)) {
            val headerIndex = headers.indexOf(model)

            (headers as MutableList).remove(model)
            if (animation) {
                notifyItemRemoved(headerIndex)
            } else notifyDataSetChanged()
        }
    }

    /**
     * 通过索引删除头布局
     */
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

    /**
     * 指定position是否为头布局
     */
    fun isHeader(@IntRange(from = 0) position: Int): Boolean =
        (headerCount > 0 && position < headerCount)

    // </editor-fold>


    // <editor-fold desc="脚布局">

    /**
     * 全部脚布局数据集合
     */
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

    /**
     * 脚布局数量
     */
    val footerCount: Int get() = footers.size

    /**
     * 添加脚布局
     * @param model 数据模型
     * @param index 指定插入的脚布局索引
     * @param animation 是否显示动画
     */
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

    /**
     * 通过数据模型删除脚布局
     */
    fun removeFooter(model: Any?, animation: Boolean = false) {
        if (footerCount != 0 && footers.contains(model)) {
            val footerIndex = headerCount + modelCount + footers.indexOf(model)
            (footers as MutableList).remove(model)
            if (animation) {
                notifyItemRemoved(footerIndex)
            } else notifyDataSetChanged()
        }
    }

    /**
     * 通过索引删除脚布局
     */
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

    /**
     * 清除全部脚布局
     */
    fun clearFooter(animation: Boolean = false) {
        if (footers.isNotEmpty()) {
            val footerCount = this.footerCount
            (footers as MutableList).clear()
            if (animation) {
                notifyItemRangeRemoved(headerCount + modelCount, itemCount + footerCount)
            } else notifyDataSetChanged()
        }
    }

    /**
     * 指定position是否为脚布局
     */
    fun isFooter(@IntRange(from = 0) position: Int): Boolean =
        (footerCount > 0 && position >= headerCount + modelCount && position < itemCount)

    // </editor-fold>


    // <editor-fold desc="数据">

    /** 数据模型数量(不包含头布局和脚布局) */
    val modelCount: Int
        get() {
            return if (models == null) 0 else models!!.size
        }

    /** 数据模型集合 */
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

    /** 可增删的数据模型集合 */
    var mutable
        get() = models as ArrayList
        set(value) {
            models = value
        }

    /**
     * 扁平化数据. 将折叠分组铺平展开创建列表
     */
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
                if (!itemSublist.isNullOrEmpty() && (item.itemExpand || (depth != 0 && expand != null))) {
                    val nestedList = flat(ArrayList(itemSublist), expand, nextDepth)
                    list.addAll(nestedList)
                }
            }
        }
        return list
    }

    /**
     * 指定position是否为models
     */
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
                rv?.post {
                    rv?.invalidateItemDecorations()
                }
            } else {
                notifyDataSetChanged()
            }
        }
    }


    /**
     * 对应models中的index
     */
    fun Int.toModelPosition(): Int = this - headerCount

    // </editor-fold>

    //<editor-fold desc="切换模式">

    /** 是否开启切换模式 */
    var toggleMode = false
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

    /** 已选择条目的position */
    val checkedPosition = mutableListOf<Int>()

    /** 已选择条目数量 */
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

    /** 全局单选模式 */
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

        if ((checkedPosition.contains(position) && checked) ||
            (!checked && !checkedPosition.contains(position))
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

    /** 分组展开和折叠是否启用动画 */
    var expandAnimationEnabled = true

    /** 只允许一个条目展开(展开当前条目就会折叠上个条目) */
    var singleExpandMode = false

    /** 监听展开分组 */
    fun onExpand(block: BindingViewHolder.(Boolean) -> Unit) {
        this.onExpand = block
    }

    /**
     * 判断两个位置的item是否属于同一分组下, 要求这两个位置的item都展开才有效
     * 如果其中一个item都属于根节点则返回-1, 这种情况不算属于同一分组下
     */
    fun isSameGroup(
        @IntRange(from = 0) position: Int,
        @IntRange(from = 0) otherPosition: Int
    ): Boolean {
        val aModel = models?.getOrNull(otherPosition) ?: return false
        val bModel = models?.getOrNull(otherPosition) ?: return false
        for (index in min(position, otherPosition) - 1 downTo 0) {
            val item = models?.getOrNull(index) ?: break
            if (item is ItemExpand && item.itemSublist?.contains(aModel) == true
                && item.itemSublist?.contains(bModel) == true
            ) {
                return true
            }
        }
        return false
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
            for (clickListener in clickListeners) {
                val view = itemView.findViewById<View>(clickListener.key) ?: continue
                if (clickListener.value.second) {
                    view.setOnClickListener {
                        (clickListener.value.first ?: onClick)?.invoke(this, it.id)
                    }
                } else {
                    view.throttleClick(clickThrottle) {
                        (clickListener.value.first ?: onClick)?.invoke(this@BindingViewHolder, id)
                    }
                }
            }
            for (longClickListener in longClickListeners) {
                val view = itemView.findViewById<View>(longClickListener.key) ?: continue
                view.setOnLongClickListener {
                    (longClickListener.value ?: onLongClick)?.invoke(this, it.id)
                    true
                }
            }
        }

        internal fun bind(model: Any) {
            this._data = model

            onBindViewHolders.forEach {
                it.onBindViewHolder(rv!!, adapter, this, adapterPosition)
            }

            if (model is ItemPosition) {
                model.itemPosition = modelPosition
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
                    val sublistFlat = flat(ArrayList(itemSublist), true, depth)

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
                    val sublistFlat = flat(ArrayList(itemSublist), false, depth)
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
            for (index in layoutPosition - 1 downTo 0) {
                val item = models?.getOrNull(index) ?: break
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
            return rv?.findViewHolderForLayoutPosition(findParentPosition()) as? BindingViewHolder
        }

        //</editor-fold>
    }

    //<editor-fold desc="悬停">

    /**
     * 是否启用条目悬停
     */
    var hoverEnabled = true

    /**
     * 监听开始悬停
     */
    var onHoverAttachListener: OnHoverAttachListener? = null

    /**
     * 通过position判断是否启用悬停
     */
    fun isHover(position: Int): Boolean {
        val model = getModelOrNull<ItemHover>(position)
        return model != null && model.itemHover && hoverEnabled
    }
    //</editor-fold>
}