/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.animation

import android.view.View


interface ItemAnimation {

    /**
     * 处理item被添加的时候的进入动画
     */
    fun onItemEnterAnimation(view: View)
}
