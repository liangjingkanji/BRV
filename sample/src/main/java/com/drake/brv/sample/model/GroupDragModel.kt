package com.drake.brv.sample.model

import com.drake.brv.annotaion.ItemOrientation
import com.drake.brv.item.ItemDrag
import com.drake.brv.item.ItemSwipe

/** 为[GroupModel]添加侧滑拖拽功能 */
class GroupDragModel(
    override var itemOrientationDrag: Int = ItemOrientation.ALL, // 拖拽方向
    override var itemOrientationSwipe: Int = ItemOrientation.HORIZONTAL // 侧滑方向
) : ItemDrag, ItemSwipe, GroupModel() {

    // 这里要求子列表也是可以侧滑的所以重写该字段
    override var itemSublist: List<Any?>? = mutableListOf(GroupDragBasicModel(), GroupDragBasicModel(), GroupDragBasicModel(), GroupDragBasicModel())
}