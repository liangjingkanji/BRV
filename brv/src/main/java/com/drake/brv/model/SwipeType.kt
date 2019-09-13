package com.drake.brv.model

import androidx.annotation.IntDef
import androidx.recyclerview.widget.ItemTouchHelper

@IntDef(SwipeType.LEFT, SwipeType.RIGHT, SwipeType.NONE)
annotation class SwipeType {

    companion object {
        const val LEFT = ItemTouchHelper.LEFT
        const val RIGHT = ItemTouchHelper.RIGHT
        const val NONE = 0
    }

}