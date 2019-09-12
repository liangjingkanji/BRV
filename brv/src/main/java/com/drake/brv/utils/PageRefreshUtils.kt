/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：9/12/19 3:04 PM
 */

package com.drake.brv.utils

import android.view.View
import android.view.ViewGroup
import com.drake.brv.PageRefreshLayout


/**
 * PageRefreshLayout 包裹当前 view
 *
 * @receiver View
 * @param loadMoreEnabled Boolean 启用上拉加载
 * @param stateEnabled Boolean 启用缺省页
 */
fun View.page(
    loadMoreEnabled: Boolean = true,
    stateEnabled: Boolean = true
): PageRefreshLayout {

    val pageRefreshLayout = PageRefreshLayout(context)

    val parent = parent as ViewGroup
    pageRefreshLayout.id = id
    val index = parent.indexOfChild(this)
    val layoutParams = layoutParams

    parent.removeView(this)
    pageRefreshLayout.setRefreshContent(this@page)
    parent.addView(pageRefreshLayout, index, layoutParams)


    pageRefreshLayout.apply {
        setEnableLoadMore(loadMoreEnabled)
        this.stateEnabled = stateEnabled
        init()
    }

    return pageRefreshLayout
}