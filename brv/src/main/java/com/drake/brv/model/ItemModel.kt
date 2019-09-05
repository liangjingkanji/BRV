/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv.model

import androidx.recyclerview.widget.ItemTouchHelper

interface ItemModel {

    companion object {
        const val LEFT = ItemTouchHelper.LEFT
        const val RIGHT = ItemTouchHelper.RIGHT
        const val UP = ItemTouchHelper.UP
        const val DOWN = ItemTouchHelper.DOWN
    }

    fun drag(): Int {
        return 0
    }

    fun swipe(): Int {
        return 0
    }
}