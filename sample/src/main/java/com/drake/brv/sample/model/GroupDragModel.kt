package com.drake.brv.sample.model

import com.drake.brv.annotaion.ItemOrientation
import com.drake.brv.item.ItemDrag
import com.drake.brv.item.ItemSwipe

class GroupDragModel(
    override var itemOrientationDrag: Int = ItemOrientation.ALL, // 拖拽方向
    override var itemOrientationSwipe: Int = ItemOrientation.HORIZONTAL // 侧滑方向
) : GroupModel(), ItemDrag, ItemSwipe