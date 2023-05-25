package com.drake.brv.sample.model

import com.drake.brv.annotaion.ItemOrientation
import com.drake.brv.annotaion.ItemOrientation.ALL
import com.drake.brv.item.ItemDrag

data class DragModel(override var itemOrientationDrag: Int = ALL) : ItemDrag {
    fun getText(): String? = if (itemOrientationDrag == ItemOrientation.NONE) "禁用拖拽" else null
}