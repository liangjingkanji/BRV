/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.annotaion

import androidx.annotation.IntDef
import androidx.recyclerview.widget.ItemTouchHelper

@IntDef(DragType.LEFT, DragType.RIGHT, DragType.UP, DragType.DOWN, DragType.NONE, DragType.ALL)
annotation class DragType {

    companion object {
        const val LEFT = ItemTouchHelper.LEFT
        const val ALL = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        const val RIGHT = ItemTouchHelper.RIGHT
        const val LEFT_RIGHT = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        const val UP = ItemTouchHelper.UP
        const val DOWN = ItemTouchHelper.DOWN
        const val UP_DOWN = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        const val NONE = 0
    }

}