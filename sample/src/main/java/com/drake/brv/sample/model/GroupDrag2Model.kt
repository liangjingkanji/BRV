package com.drake.brv.sample.model

import com.drake.brv.annotaion.ItemOrientation
import com.drake.brv.item.ItemSwipe

/** 为[Group3Model]添加侧滑功能 */
class GroupDrag2Model(
    override var itemOrientationSwipe: Int = ItemOrientation.HORIZONTAL, // 侧滑方向
) : Group3Model(), ItemSwipe