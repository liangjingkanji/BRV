/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：5/5/20 9:12 PM
 */

package com.drake.brv.item

/**
 * 可粘性头部的条目
 */
interface ItemHover {
    /**
     * 是否启用粘性头部
     * @see com.drake.brv.layoutmanager.HoverLinearLayoutManager
     * @see com.drake.brv.layoutmanager.HoverGridLayoutManager
     * @see com.drake.brv.layoutmanager.HoverStaggeredGridLayoutManager
     */
    var itemHover: Boolean
}