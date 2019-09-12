/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv


import android.annotation.SuppressLint
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
import com.drake.brv.listener.DefaultItemTouchCallback
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.longClicks
import java.util.concurrent.TimeUnit

/**
 * 基于DataBinding实现的通用RecyclerViewAdapter
 *
 * 一行代码创建多类型, 支持一对多类型和普通多类型混合
 * 快速添加点击事件(防暴力点击/快速点击/长按事件)
 * 点击/绑定事件回调
 * item 添加动画
 * HeaderView和FooterView的添加删除
 * item选择状态监听(切换模式/多选/单选/全选/取消全选/反选/选中数据集/选中数量/单选和多选模式切换)
 */

@Suppress("UNCHECKED_CAST")
class BindingAdapter : RecyclerView.Adapter<BindingAdapter.BindingViewHolder>() {

    var models: List<Any?>? = null
        set(value) {

            if (!value.isNullOrEmpty()) {
                field = ArrayList(value)
            }

            notifyDataSetChanged()

            if (checkedPositions.isNotEmpty()) {
                checkedPositions.clear()
            }

            if (isFirst) {
                lastPosition = -1
                isFirst = false
            } else {
                lastPosition = itemCount - 1
            }
        }


    val headers: ArrayList<View> = arrayListOf()
    val footers: ArrayList<View> = arrayListOf()
    val checkedPositions = arrayListOf<Int>()


    val modelCount: Int
        get() {
            return if (models == null) {
                0
            } else {
                models!!.size
            }
        }


    val headerCount: Int
        get() {
            return headers.size
        }

    val footerCount: Int
        get() {
            return footers.size
        }


    val checkedCount: Int
        get() = checkedPositions.size

    val checkableCount: Int
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
            val size = checkedPositions.size
            if (field && size > 1) {
                for (i in 0 until size - 1) {
                    setChecked(checkedPositions[0], false)
                }
            }
        }


    var recyclerView: RecyclerView? = null

    var touchEnable = false
        set(value) {
            field = value

            if (value) {
                recyclerView?.let {
                    itemTouchHelper.attachToRecyclerView(recyclerView)
                }
            } else {
                itemTouchHelper.attachToRecyclerView(null)
            }
        }

    private var itemAnimation: BaseItemAnimation = AlphaItemAnimation()
    private var lastPosition = -1
    private var isFirst = true
    private var animationEnable = false
    private val clickableIds = SparseBooleanArray()
    private val longClickableIds = ArrayList<Int>()
    private var checkableItemTypeList: List<Int>? = null
    val typePool = mutableMapOf<Class<*>, Any.(Int) -> Int>()

    private var onBind: (BindingViewHolder.() -> Boolean)? = null
    private var onPayload: (BindingViewHolder.(Any) -> Unit)? = null
    private var onClick: (BindingViewHolder.(viewId: Int) -> Unit)? = null
    private var onLongClick: (BindingViewHolder.(viewId: Int) -> Unit)? = null
    private var onCheckedChange: ((itemType: Int, position: Int, checked: Boolean, allChecked: Boolean) -> Unit)? =
        null
    private var onToggle: ((itemType: Int, position: Int, toggleModel: Boolean) -> Unit)? = null
    private var onToggleEnd: ((toggleModel: Boolean) -> Unit)? = null

    var itemTouchHelper = ItemTouchHelper(DefaultItemTouchCallback(this))

    /**
     * function params of return value , true brv not handler onBindViewHolder
     * @param block [@kotlin.ExtensionFunctionType] Function1<BindingViewHolder, Boolean>
     */
    fun onBind(block: BindingViewHolder.() -> Boolean) {
        onBind = block
    }

    /**
     * increment data update
     * @param block [@kotlin.ExtensionFunctionType] Function2<BindingViewHolder, Any, Unit>
     */
    fun onPayload(block: BindingViewHolder.(Any) -> Unit) {
        onPayload = block
    }

    fun onClick(@IdRes vararg id: Int, block: BindingViewHolder.(Int) -> Unit) {
        for (i in id) {
            clickableIds.put(i, false)
        }
        onClick = block
    }

    fun onLongClick(@IdRes vararg id: Int, block: BindingViewHolder.(Int) -> Unit) {
        for (i in id) {
            longClickableIds.add(i)
        }
        onLongClick = block
    }

    fun onCheckedChange(block: (itemType: Int, position: Int, checked: Boolean, allChecked: Boolean) -> Unit) {
        onCheckedChange = block
    }

    fun onToggle(block: (itemType: Int, position: Int, toggleMode: Boolean) -> Unit) {
        onToggle = block
    }

    fun onToggleEnd(block: (Boolean) -> Unit) {
        onToggleEnd = block
    }


    fun addHeader(view: View) {
        if (headers.contains(view)) {
            return
        }
        headers.apply {
            add(view)
            notifyItemInserted(headerCount - 1)
        }
    }

    fun addFooter(view: View) {
        if (footers.contains(view)) {
            return
        }
        footers.apply {
            add(view)
            notifyItemInserted(headerCount + modelCount + footerCount - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        return when {
            isHeader(viewType) -> BindingViewHolder(headers[viewType])
            isFooter(viewType) -> BindingViewHolder(footers[viewType - headerCount - modelCount])
            else -> {
                val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context),
                    viewType,
                    parent,
                    false
                ) ?: throw NoSuchPropertyException("Item of layout must is data binding layout")
                BindingViewHolder(viewDataBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        if (isModel(position)) {
            holder.bind(getModel<Any>(position)!!)
        }
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

        return when {
            isHeader(position) -> position
            isFooter(position) -> position
            else -> {
                val model = getModel<Any>(position)
                val modelClass: Class<*> = model!!.javaClass
                (typePool[modelClass]?.invoke(model, position)
                    ?: throw NoSuchPropertyException("Please add item model type, model = $model"))
            }
        }
    }

    override fun getItemCount(): Int {
        return headerCount + modelCount + footerCount
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        this.recyclerView = recyclerView

        if (touchEnable) {
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }
    }


    override fun onViewAttachedToWindow(holder: BindingViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutPosition = holder.layoutPosition
        if (animationEnable && lastPosition < layoutPosition) {
            itemAnimation.onItemEnterAnimation(holder.itemView)
            lastPosition = layoutPosition
        }
    }

    inline fun <reified M> addType(@LayoutRes layout: Int) {
        typePool[M::class.java] = { layout }
    }

    inline fun <reified M> addType(noinline block: M.(Int) -> Int) {
        typePool[M::class.java] = block as Any.(Int) -> Int
    }

    /**
     * add click event
     * in 500 milliSecond be invalid
     */
    fun addClickable(@IdRes vararg id: Int) {
        for (i in id) {
            clickableIds.put(i, false)
        }
    }

    fun addFastClickable(@IdRes vararg id: Int) {
        for (i in id) {
            clickableIds.put(i, true)
        }
    }

    fun addLongClickable(@IdRes vararg id: Int) {
        for (i in id) {
            longClickableIds.add(i)
        }
    }

    fun setAnimation(animationEnable: Boolean) {
        this.animationEnable = animationEnable
    }

    fun setAnimation(itemAnimation: BaseItemAnimation) {
        this.animationEnable = true
        this.itemAnimation = itemAnimation
    }

    fun setAnimation(@AnimationType animationType: Int) {
        this.animationEnable = true
        when (animationType) {
            AnimationType.ALPHA -> this.itemAnimation = AlphaItemAnimation()
            AnimationType.SCALE -> this.itemAnimation = ScaleItemAnimation()
            AnimationType.SLIDE_BOTTOM -> this.itemAnimation = SlideBottomItemAnimation()
            AnimationType.SLIDE_LEFT -> this.itemAnimation = SlideLeftItemAnimation()
            AnimationType.SLIDE_RIGHT -> this.itemAnimation = SlideRightItemAnimation()
        }
    }

    fun removeHeader(view: View) {
        if (headers.contains(view)) {
            val temp = headers.indexOf(view)
            headers.remove(view)
            notifyItemRemoved(temp)
        }
    }

    fun removeFooter(view: View) {
        if (footers.contains(view)) {
            val temp = footers.indexOf(view)
            footers.remove(view)
            notifyItemRemoved(temp)
        }
    }

    fun clearHeader() {
        if (headers.isNotEmpty()) {
            val temp = headerCount
            headers.clear()
            notifyItemRangeRemoved(0, temp)
        }
    }

    fun clearFooter() {
        if (footers.isNotEmpty()) {
            val temp = footerCount
            footers.clear()
            notifyItemRangeRemoved(headerCount + modelCount, temp)
        }
    }

    fun isHeader(@IntRange(from = 0) position: Int): Boolean {
        return (headerCount > 0 && position < headerCount)
    }

    fun isModel(position: Int): Boolean {
        return !(isHeader(position) || isFooter(position))
    }

    fun isFooter(@IntRange(from = 0) position: Int): Boolean {
        return (footerCount > 0 && position >= headerCount + modelCount && position < headerCount + modelCount + footerCount)
    }

    fun <M> getModel(position: Int): M? {
        return when {
            isHeader(position) -> headers[position] as M
            isFooter(position) -> footers[position - headerCount - modelCount] as M
            else -> models?.let { it[position - headerCount] as M }
        }
    }

    /**
     * adapter position  convert to  model position
     * @receiver Int model of position
     * @return Int
     */
    fun Int.getModelPosition(): Int {
        return this - headerCount
    }

    /**
     * get checked status of list model
     */
    fun <M> getCheckedModels(): List<M> {
        val checkedModels = ArrayList<M>()
        for (position in this.checkedPositions) {
            checkedModels.add(getModel(position)!!)
        }
        return checkedModels
    }

    var toggleMode = false

    /**
     * switch list mode, can iterate each item of list
     *
     * @see .setOnItemToggleListener
     */
    fun toggle() {
        onToggle.let {
            toggleMode = !toggleMode
            for (i in 0 until itemCount) {
                onToggle?.invoke(getItemViewType(i), i, toggleMode)
            }
            onToggleEnd?.invoke(toggleMode)
        }
    }


    /**
     * set list mode, can iterate each item of list
     * @param toggleModel Boolean
     */
    fun setToggle(toggleModel: Boolean) {
        if (toggleModel != this.toggleMode) {
            toggle()
        }
    }


    /**
     * set checked status of multi type list
     * @see setChecked
     * @param checkableItemType IntArray
     */
    fun setCheckableType(@LayoutRes vararg checkableItemType: Int) {
        checkableItemTypeList = checkableItemType.toMutableList()
    }


    /**
     * single mode be not support checked of all, but support cancel checked of all
     * @param checked Boolean true is checked of all, false is cancel checked of all
     */
    fun checkedAll(checked: Boolean = true) {
        if (checked) {
            if (singleMode) {
                return
            }
            for (i in 0 until itemCount) {
                if (!checkedPositions.contains(i)) {
                    setChecked(i, true)
                }
            }
        } else {
            for (i in 0 until itemCount) {
                if (checkedPositions.contains(i)) {
                    setChecked(i, false)
                }
            }
        }
    }

    /**
     * whether checked of all or not
     * @return Boolean
     */
    fun isCheckedAll(): Boolean {
        return checkedCount == checkableCount
    }


    fun checkedReverse() {
        if (singleMode) {
            return
        }
        for (i in 0 until itemCount) {
            if (checkedPositions.contains(i)) {
                setChecked(i, false)
            } else {
                setChecked(i, true)
            }
        }
    }


    /**
     * set checked status of item
     *
     * @see .setOnItemCheckedChangeListener
     */
    fun setChecked(@IntRange(from = 0) position: Int, checked: Boolean) {

        if ((checkedPositions.contains(position) && checked) || (!checked && !checkedPositions.contains(
                position
            ))
        ) {
            return
        }

        val itemViewType = getItemViewType(position)
        if (checkableItemTypeList != null && checkableItemTypeList!!.contains(itemViewType)) {
            return
        }
        if (onCheckedChange == null || !isModel(position)) {
            return
        }
        if (checked) {
            checkedPositions.add(position)
        } else {
            checkedPositions.remove(Integer.valueOf(position))
        }

        onCheckedChange?.invoke(
            itemViewType,
            position,
            checked,
            isCheckedAll()
        )
        if (singleMode && checked && checkedPositions.size > 1) {
            setChecked(checkedPositions[0], false)
        }
    }

    fun toggleChecked(@IntRange(from = 0) position: Int) {
        if (checkedPositions.contains(position)) {
            setChecked(position, false)
        } else {
            setChecked(position, true)
        }
    }

    /**
     * add new data
     */
    fun addModels(models: List<Any?>?) {
        if (models.isNullOrEmpty()) {
            return
        }
        if (this.models.isNullOrEmpty()) {
            this.models = models
        } else {
            (this.models!! as ArrayList).addAll(models)
            notifyItemRangeInserted(headerCount + modelCount, models.size)
        }
    }

    inner class BindingViewHolder : RecyclerView.ViewHolder {

        private lateinit var viewDataBinding: ViewDataBinding
        private lateinit var model: Any
        val bindingAdapter: BindingAdapter = this@BindingAdapter
        val modelPosition
            get() = adapterPosition - headerCount

        constructor(itemView: View) : super(itemView)

        @SuppressLint("CheckResult")
        constructor(viewDataBinding: ViewDataBinding) : super(viewDataBinding.root) {
            this.viewDataBinding = viewDataBinding

            for (i in 0 until clickableIds.size()) {
                val view = itemView.findViewById<View>(clickableIds.keyAt(i)) ?: continue
                if (clickableIds.valueAt(i)) {
                    view.clicks().subscribe { onClick?.invoke(this, view.id) }
                } else {
                    view.clicks()
                        .throttleFirst(500, TimeUnit.MILLISECONDS)
                        .subscribe { onClick?.invoke(this, view.id) }
                }
            }

            for (longClickableId in longClickableIds) {
                val view = itemView.findViewById<View>(longClickableId) ?: continue
                view.longClicks().subscribe { onLongClick?.invoke(this, view.id) }
            }
        }

        fun bind(model: Any) {
            this.model = model

            onBind?.apply {
                val isReturn = onBind!!.invoke(this@BindingViewHolder)
                if (isReturn) return
            }

            viewDataBinding.setVariable(modelId, model)
            viewDataBinding.executePendingBindings()
        }

        /**
         * get viewDataBinding of list
         */
        fun <B : ViewDataBinding> getViewDataBinding(): B {
            return viewDataBinding as B
        }

        /**
         * get model of list
         */
        fun <M> getModel(): M {

            return model as M
        }
    }

    companion object {
        var modelId: Int = -1
    }
}