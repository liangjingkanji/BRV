package com.drake.brv.sample.mod

import com.drake.brv.model.DragType
import com.drake.brv.model.Item
import com.drake.brv.model.SwipeType

data class SwipeDragModel(var swipe: Int, var drag: Int) : Item {

    override fun isDrag(): Int {
        return drag
    }


    override fun isSwipe(): Int {
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

            if (temp.isNotEmpty()) {
                temp = "当前条目$temp"
            }

            return temp
        }
}