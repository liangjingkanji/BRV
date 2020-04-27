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
    fun isDrag(): Int {
        return DragType.NONE
    }

    /**
     * 悬停
     * @return true 表示启用悬停, 要求使用Hover前缀的LayoutManager才生效
     * @see com.drake.brv.layoutmanager.StickyLinearLayoutManager
     * @see com.drake.brv.layoutmanager.StickyGridLayoutManager
     * @see com.drake.brv.layoutmanager.StickyStaggeredGridLayoutManager
     */
    fun isSticky(): Boolean {
        return false
    }


    /**
     * 侧滑方向
     */
    @SwipeType
    fun isSwipe(): Int {
        return SwipeType.NONE
    }

    /**
     * 子列表
     */
    fun sublist(): List<Any?>? {
        return null
    }

}