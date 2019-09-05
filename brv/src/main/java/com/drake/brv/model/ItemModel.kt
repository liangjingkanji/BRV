/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv.model

import androidx.recyclerview.widget.ItemTouchHelper

open class ItemModel {

    val LEFT = ItemTouchHelper.LEFT
    val RIGHT = ItemTouchHelper.RIGHT
    val UP = ItemTouchHelper.UP
    val DOWN = ItemTouchHelper.DOWN

    open var drag = 0
    open var swipe = 0
}