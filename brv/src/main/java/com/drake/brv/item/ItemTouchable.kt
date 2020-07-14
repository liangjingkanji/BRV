/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.item

import com.drake.brv.annotaion.DragType
import com.drake.brv.annotaion.SwipeType

/**
 * 可触控的条目
 */
interface ItemTouchable {

    // 侧滑方向
    @SwipeType
    var itemSwipe: Int

    // 拖拽方向
    @DragType
    var itemDrag: Int
}