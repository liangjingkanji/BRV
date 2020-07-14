/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.listener

import android.view.View

/**
 * 由RecyclerView.Adapter实现该接口
 */
interface OnHoverAttachListener {
    /**
     * 当条目附着时
     * [detachHover] 该函数可以进行还原
     */
    fun attachHover(v: View)

    /**
     * 条目分离时
     * 一般用于还原[attachHover]函数
     */
    fun detachHover(v: View)
}