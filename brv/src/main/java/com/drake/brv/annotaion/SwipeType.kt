/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.annotaion

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