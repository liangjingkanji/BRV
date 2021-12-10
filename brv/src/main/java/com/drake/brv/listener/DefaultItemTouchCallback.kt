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

package com.drake.brv.listener

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemDrag
import com.drake.brv.item.ItemSwipe
import com.drake.brv.utils.bindingAdapter
import java.util.*

/**
 * 默认实现拖拽替换和侧滑删除
 */
open class DefaultItemTouchCallback : ItemTouchHelper.Callback() {

    /** 侧滑到底item消失时 */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val adapter = viewHolder.bindingAdapter as? BindingAdapter
        val layoutPosition = viewHolder.layoutPosition
        adapter?.notifyItemRemoved(layoutPosition)
        (adapter?.models as ArrayList).removeAt(layoutPosition)
    }

    /**
     * 返回值表示拖拽/侧滑的方向
     * @param viewHolder 拖拽触发的Item
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
    ): Int {
        var drag = 0
        var swipe = 0
        if (viewHolder is BindingAdapter.BindingViewHolder) {
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
        isCurrentlyActive: Boolean
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

    /**
     * 当拖拽动作完成且松开手指时触发
     * @param source 触发拖拽的Item
     * @param target 拖拽目标的Item
     */
    open fun onDrag(
        source: BindingAdapter.BindingViewHolder, target: BindingAdapter.BindingViewHolder
    ) {

    }

    private var lastActionState: Int = 0
    private var sourceViewHolder: BindingAdapter.BindingViewHolder? = null
    private var targetViewHolder: BindingAdapter.BindingViewHolder? = null

    /**
     * 状态变化
     * @param actionState
     * @see ItemTouchHelper.ACTION_STATE_DRAG 拖拽
     * @see ItemTouchHelper.ACTION_STATE_SWIPE 侧滑
     * @see ItemTouchHelper.ACTION_STATE_IDLE 闲置
     */
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        when (actionState) {
            ItemTouchHelper.ACTION_STATE_IDLE -> {
                if (lastActionState == ItemTouchHelper.ACTION_STATE_DRAG &&
                    sourceViewHolder is BindingAdapter.BindingViewHolder &&
                    targetViewHolder is BindingAdapter.BindingViewHolder) {
                    onDrag(sourceViewHolder!!, targetViewHolder!!)
                }
            }
            else -> {
                this.lastActionState = actionState
            }
        }
    }

    /** 拖拽移动超过其他item时, 其返回值表示是否已经拖拽替换(会触发函数onMoved) */
    override fun onMove(
        recyclerView: RecyclerView, source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
    ): Boolean {
        val adapter = recyclerView.bindingAdapter as? BindingAdapter ?: return false
        val currentPosition = recyclerView.getChildLayoutPosition(source.itemView)
        val targetPosition = recyclerView.getChildLayoutPosition(target.itemView)

        if (source is BindingAdapter.BindingViewHolder && target is BindingAdapter.BindingViewHolder) {
            val model = target.getModel<Any>()
            if (model is ItemDrag && model.itemOrientationDrag != 0) {
                adapter.notifyItemMoved(currentPosition, targetPosition)
                Collections.swap(adapter.mutable, currentPosition - adapter.headerCount, targetPosition - adapter.headerCount)
                sourceViewHolder = source
                targetViewHolder = target
            }
        }
        return false
    }
}