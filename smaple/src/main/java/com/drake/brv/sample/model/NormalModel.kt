/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.sample.model

import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.drake.brv.item.ItemPosition

class NormalModel(override var itemPosition: Int = 0) : ItemPosition, ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        // 不推荐这种方式, 造成Model包含视图引用, 耦合(例如BRVAH)
        // val appName = holder.context.getString(R.string.app_name)
        // holder.findView<TextView>(R.id.tv_normal).text = appName + itemPosition
    }
}