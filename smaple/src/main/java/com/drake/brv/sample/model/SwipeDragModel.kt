package com.drake.brv.sample.model

import com.drake.brv.model.DragType
import com.drake.brv.model.Item
import com.drake.brv.model.SwipeType

data class SwipeDragModel(var swipe: Int, var drag: Int) : Item {

    override fun drag(): Int {
        return drag
    }


    override fun swipe(): Int {
        return swipe
    }

    val txt: String
        get() {

            var temp = ""

            if (drag == DragType.NONE) {
                temp += "不支持拖拽"
            }

            if (drag == DragType.NONE && swipe == SwipeType.NONE)
                temp += " | "

            if (swipe == SwipeType.NONE)
                temp += "不支持侧滑"

            return temp
        }
}