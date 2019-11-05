/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：9/15/19 7:41 PM
 */

package com.drake.brv.listener

import android.view.View


internal fun View.throttleClick(interval: Long = 500, block: View.() -> Unit) {
    setOnClickListener(ThrottleClickListener(interval, block))
}

internal class ThrottleClickListener(val interval: Long = 500, var block: View.() -> Unit) :
        View.OnClickListener {

    var lastTime: Long = 0

    override fun onClick(v: View) {

        val currentTime = System.currentTimeMillis()

        if (currentTime - lastTime > interval) {
            lastTime = currentTime
            block(v)
        }

    }


}