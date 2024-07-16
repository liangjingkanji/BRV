/*
 * MIT License
 *
 * Copyright (c) 2023 劉強東 https://github.com/liangjingkanji
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.drake.brv.listener

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.BindingAdapter
import com.drake.brv.BindingAdapter.BindingViewHolder
import com.drake.brv.item.ItemDrag
import com.drake.brv.item.ItemSwipe
import com.drake.brv.utils.bindingAdapter

/**
 * 默认实现拖拽替换和侧滑删除
 */
open class DefaultItemTouchCallback : ItemTouchHelper.Callback() {

    /** 侧滑到底item消失时 */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val adapter = viewHolder.bindingAdapter as? BindingAdapter ?: return
        val layoutPosition = viewHolder.layoutPosition
        val headerCount = adapter.headerCount
        if (layoutPosition < headerCount) {
            adapter.removeHeader(layoutPosition, true)
        } else {
            val models = adapter.models as? MutableList
            if (models != null) {
                models.removeAt(layoutPosition - headerCount)
                adapter.notifyItemRemoved(layoutPosition)
            }
        }
    }

    /**
     * 返回值表示拖拽/侧滑的方向
     * @param viewHolder 拖拽触发的Item
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
    ): Int {
        var drag = 0
        var swipe = 0
        if (viewHolder is BindingViewHolder) {
            val model = viewHolder.getModel<Any>()
            if (model is ItemDrag) drag = model.itemOrientationDrag
            if (model is ItemSwipe) swipe = model.itemOrientationSwipe
        }
        return makeMovementFlags(drag, swipe)
    }

    /** 绘制拖拽或者侧滑动画 */
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val swipeView = viewHolder.itemView.findViewWithTag<View>("swipe")
            if (swipeView != null) {
                swipeView.translationX = dX
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    /**
     * 滑动距离速率来判断当前是否执行滑动删除事件(可以理解为移出itemView)
     * @param viewHolder 拖拽触发的Item
     */
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 1f
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val view = viewHolder.itemView.findViewWithTag<View>("swipe")
        if (view != null) {
            view.translationX = 0F
        }
    }

    /**
     * 当拖拽动作完成且松开手指时触发, 如果拖拽起始位置等于目标位置则属于无效移动, 不回调当前方法
     * @param source 触发拖拽的Item
     * @param target 拖拽目标的Item
     */
    open fun onDrag(source: BindingViewHolder, target: BindingViewHolder) {

    }

    private var lastActionState: Int = 0
    private var sourceViewHolder: BindingViewHolder? = null
    private var targetViewHolder: BindingViewHolder? = null

    /**
     * 拖拽或者侧滑导致的状态变化
     * @param vh 当前触发的Item
     * @param actionState 触发的状态
     * @see ItemTouchHelper.ACTION_STATE_DRAG 拖拽
     * @see ItemTouchHelper.ACTION_STATE_SWIPE 侧滑
     * @see ItemTouchHelper.ACTION_STATE_IDLE 闲置
     */
    override fun onSelectedChanged(vh: RecyclerView.ViewHolder?, actionState: Int) {
        when (actionState) {
            ItemTouchHelper.ACTION_STATE_IDLE -> {
                val source = sourceViewHolder
                val target = targetViewHolder
                if (lastActionState == ItemTouchHelper.ACTION_STATE_DRAG &&
                    source is BindingViewHolder && target is BindingViewHolder &&
                    startMovingPosition != target.bindingAdapterPosition
                ) {
                    onDrag(source, target)
                }
                // 手指放下, 重置起始移动位置
                startMovingPosition = null
            }

            else -> {
                this.lastActionState = actionState
            }
        }
    }

    /**记录开始移动位置, 如果拖拽起始位置等于目标位置则属于无效移动*/
    private var startMovingPosition: Int? = null

    /**
     * 拖拽移动超过其他item时, 其返回值表示是否已经拖拽替换(会触发函数onMoved)
     * @return 返回false 禁止被拖拽交换移动
     */
    override fun onMove(
        recyclerView: RecyclerView,
        source: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder,
    ): Boolean {
        val adapter = recyclerView.bindingAdapter as? BindingAdapter ?: return false
        val currentPosition = recyclerView.getChildLayoutPosition(source.itemView)
        val targetPosition = recyclerView.getChildLayoutPosition(target.itemView)

        val models = adapter.models as? MutableList
        if (models != null && source is BindingViewHolder && target is BindingViewHolder && adapter.isModel(targetPosition)) {
            val fromPosition = currentPosition - adapter.headerCount
            val toPosition = targetPosition - adapter.headerCount
            val fromItem = models[fromPosition]
            models.removeAt(fromPosition)
            models.add(toPosition, fromItem)
            adapter.notifyItemMoved(currentPosition, targetPosition)
            // 记录起始移动位置
            if (startMovingPosition == null) {
                startMovingPosition = source.bindingAdapterPosition
            }
            sourceViewHolder = source
            targetViewHolder = target
            return true
        }
        return false
    }
}