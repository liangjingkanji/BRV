/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv.model

interface ItemModel {


    @DragType
    fun drag(): Int {
        return DragType.NONE
    }


    @SwipeType
    fun swipe(): Int {
        return SwipeType.NONE
    }

}