/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：5/9/20 1:07 AM
 */

package com.drake.brv.item

import com.drake.brv.BindingAdapter

/**
 * 推荐使用DataBinding来进行数据绑定[com.drake.brv.BindingAdapter.modelId], 或者函数[com.drake.brv.BindingAdapter.onBind]
 * 该接口进行UI操作不符合MVVM架构, 因为Model中不允许出现View引用
 */
interface ItemBind {
    fun onBind(vh: BindingAdapter.BindingViewHolder)
}