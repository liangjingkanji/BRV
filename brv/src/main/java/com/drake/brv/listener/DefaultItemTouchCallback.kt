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
import com.drake.brv.item.ItemGroup
import com.drake.brv.item.ItemDrag
import com.drake.brv.item.ItemExpand
import com.drake.brv.item.ItemSwipe
import com.drake.brv.utils.bindingAdapter

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
        // 拖拽移动分组前先折叠子列表
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            (viewHolder as BindingAdapter.BindingViewHolder).collapse()
        }
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
            val targetModel = target.getModel<Any>()
            val sourceModel = source.getModel<Any>()
            //拖拽跨组处理
            if (targetModel is ItemGroup && sourceModel is ItemGroup) {
                if (canSpanGroups()) {
                    //如果目标item是可折叠的，并且目标组已经展开了，并且目标组中不包含这个拖拽item---这里分两种情况解释:同组，不同组
                    if (targetModel is ItemExpand && targetModel.itemExpand && targetModel.itemSublist?.contains(sourceModel) == false) {
                        //同组：
                        //同组情况下，是将拖拽item加入到目标item组,当前目标item就是一个组头。所以将item放置到组的第0位置。
                        //最后的（targetModel.itemSublist?.contains(sourceModel) == false）并不影响同组操作。
                        //==============================================================================
                        //不同组：
                        //"加入目标组",这种情况一般是，拖拽item向下移动，而下一个item就是一个组的头。而且这个组头还是展开的。
                        //其实是从一个组中拖拽到另一个组中，从上往下拖拽，
                        //这个时候就要将当前拖拽的item加入到它的组下。如果组不是展开的，那么就将拖拽item加入到目标item同级组内，其实就是else的操作。
                        //"脱离目标组" 上面的（targetModel.itemSublist?.contains(sourceModel) == false）的判断，
                        //解释过来就是，如果是true，说明这个item在这个组里面，也就是说，此时是item向上拖拽，拖到自己的父组位置，
                        //与父组进行了位置交换，所以需要走else将item脱离当前组,加入到自己父级的父级组里，和当前自己父级同级别。
                        changeGroupInternalPosition(sourceModel, targetModel, 0)
                    } else {
                        //同组：由于是同组，拖拽item和目标item的itemParent是同一个。所以还是加入到了当前自己组内，要做的只是修改组内位置。
                        //不同组：拖拽item加入到目标item的组内，然后位置是当前目标item的位置。
                        changeGroupInternalPosition(
                            sourceModel,
                            targetModel.itemParent,
                            targetModel.itemGroupPosition
                        )
                    }
                } else {
                    if (sourceModel.itemParent != targetModel.itemParent) {
                        //其父组不同，说明不在一个组里边。 从目标item向上查找，找到与source同级别的item。并对其进行折叠。
                        //其实际情况是拖拽item向上移动，而上面的item是个展开的item，所以要去折叠它。方便同组内拖拽移动。
                        //先折叠然后再进行else操作和组内排序。
                        findSameLevelParentAndCanExpandModel(recyclerView, source, target)
                        return false
                    } else {
                        //在同组，如果这个item是可折叠的，如果它目前是展开状态，就折叠
                        if (targetModel is ItemExpand && targetModel.itemExpand) {
                            target.collapse()
                            return false
                        }
                        //交换组内位置,sourceModel和targetModel是同一个父级，所以joinGroup传谁的都行。
                        changeGroupInternalPosition(
                            sourceModel,
                            sourceModel.itemParent,
                            targetModel.itemGroupPosition
                        )
                    }
                }
            }
            //改变item在列表中的位置
            if (targetModel is ItemDrag && targetModel.itemOrientationDrag != 0) {
                val fromPosition = currentPosition - adapter.headerCount
                val toPosition = targetPosition - adapter.headerCount
                val fromItem = adapter.mutable[fromPosition]
                adapter.mutable.apply {
                    removeAt(fromPosition)
                    add(toPosition, fromItem)
                }
                adapter.notifyItemMoved(currentPosition, targetPosition)
                sourceViewHolder = source
                targetViewHolder = target
            }
        }
        return false
    }


    /**
     * 改变[sourceModel]的组关系为[joinGroup]，和其在[joinGroup]中的[joinPosition]位置
     * @param sourceModel 源数据模型，拖拽的
     * @param joinGroup 要加入的组
     * @param joinPosition 要加入的组的哪个位置
     */
    private fun changeGroupInternalPosition(
        sourceModel: ItemGroup,
        joinGroup: ItemExpand? = sourceModel.itemParent,
        joinPosition: Int
    ) {
        //如果两个不在同一组中 源Model要从自己的组中移除，并加入目标组中。
        //如果是同一个组，只是交换组内位置。
        sourceModel.itemParent?.itemSublist =
            sourceModel.itemParent?.itemSublist?.toMutableList()?.apply {
                remove(sourceModel)
                //重新调整组内位置标记
                forEachIndexed { index, item ->
                    if (item is ItemGroup) {
                        item.itemGroupPosition = index
                    }
                }
            }

        //如果不是同一组，重新对targetModel的父级的子项进行设置
        //如果是同一组，将源item加入target的位置
        joinGroup?.itemSublist =
            joinGroup?.itemSublist?.toMutableList()?.apply {
                //添加到与当前targetModel相同组下。
                add(joinPosition, sourceModel)
                forEachIndexed { index, item ->
                    //重新调整组内位置标记
                    if (item is ItemGroup) {
                        item.itemGroupPosition = index
                    }
                }
            }
        //改变其父级
        sourceModel.itemParent = joinGroup
    }

    /**
     * 找到同级别拥有同一个父级别的可展开的Model
     * 从[target]item向上查找其父组，直到找到一个与[source]同组的model,然后折叠
     */
    private fun findSameLevelParentAndCanExpandModel(
        recyclerView: RecyclerView, source: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
    ) {
        if (source is BindingAdapter.BindingViewHolder && target is BindingAdapter.BindingViewHolder) {
            val targetPosition = recyclerView.getChildLayoutPosition(target.itemView)
            val currentModel = source.getModelOrNull<ItemExpand>()
            val targetModel = target.getModelOrNull<ItemExpand>()
            if (targetModel?.itemParent != currentModel?.itemParent) {
                if (targetPosition != 0) {
                    val previousViewHolder =
                        recyclerView.findViewHolderForAdapterPosition(targetPosition - 1) ?: return
                    findSameLevelParentAndCanExpandModel(recyclerView, source, previousViewHolder)
                } else {
                    //没有找到
                }
            } else {
                if (targetModel?.itemExpand == true) {
                    target.collapse()
                    return
                }
            }
        }

    }

    /**
     * 是否可以跨越组拖拽
     */
    open fun canSpanGroups(): Boolean {
        return false
    }
}