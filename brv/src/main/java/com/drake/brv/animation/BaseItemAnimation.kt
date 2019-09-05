/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv.animation

import android.view.View


interface BaseItemAnimation {

    /**
     * 处理item被添加的时候的进入动画
     *
     * @param view item view
     */
    fun onItemEnterAnimation(view: View)
}
