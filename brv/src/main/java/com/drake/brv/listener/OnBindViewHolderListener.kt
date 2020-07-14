/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.listener

import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.BindingAdapter

interface OnBindViewHolderListener {
    fun onBindViewHolder(rv: RecyclerView,
                         adapter: BindingAdapter,
                         holder: BindingAdapter.BindingViewHolder,
                         position: Int)
}