package com.drake.brv.sample.model

import com.drake.brv.annotaion.ItemOrientation
import com.drake.brv.item.ItemSwipe

data class SwipeModel(override var itemOrientationSwipe: Int = ItemOrientation.ALL) : ItemSwipe {

    fun getText(): String? = when (itemOrientationSwipe) {
        ItemOrientation.ALL -> "水平滑动删除"
        ItemOrientation.LEFT -> "左滑删除"
        ItemOrientation.RIGHT -> "右滑删除"
        else -> "禁用侧滑"
    }
}