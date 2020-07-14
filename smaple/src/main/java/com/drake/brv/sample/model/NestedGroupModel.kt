/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.sample.model

import androidx.databinding.BaseObservable
import com.drake.brv.item.ItemExpand
import com.drake.brv.sample.R

class NestedGroupModel : ItemExpand, BaseObservable() {

    override var itemGroupPosition: Int = 0
    override var itemExpand: Boolean = false
        set(value) {
            field = value
            notifyChange()
        }
    override var itemSublist: List<Any?>? = listOf(Model(), Model(), Model(), Model())
    val title get() = "嵌套分组 [ $itemGroupPosition ]"
    val expandIcon get() = if (itemExpand) R.drawable.ic_arrow_nested_expand else R.drawable.ic_arrow_nested_collapse
}