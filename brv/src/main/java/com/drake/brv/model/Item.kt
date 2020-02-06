/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv.model

interface Item {

    /**
     * 拖拽方向
     */
    @DragType
    fun drag(): Int {
        return DragType.NONE
    }


    /**
     * 侧滑方向
     */
    @SwipeType
    fun swipe(): Int {
        return SwipeType.NONE
    }

    /**
     * 子列表
     */
    fun sublist(): List<Any?>? {
        return null
    }

}