/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.sample.model

import androidx.databinding.BaseObservable
import com.drake.brv.item.ItemExpand
import com.drake.brv.item.ItemHover
import com.drake.brv.item.ItemPosition
import com.drake.brv.sample.R

class GroupModel : ItemExpand, ItemHover, ItemPosition, BaseObservable() {

    override var itemGroupPosition: Int = 0
    override var itemExpand: Boolean = false
        set(value) {
            field = value
            notifyChange()
        }
    override var itemSublist: List<Any?>? = listOf(Model(), Model(), Model(), Model())
    override var itemHover: Boolean = true
    override var itemPosition: Int = 0

    val title get() = "分组 [ $itemGroupPosition ]"

    val expandIcon get() = if (itemExpand) R.drawable.ic_arrow_expand else R.drawable.ic_arrow_collapse

}