package com.drake.brv.sample.model

import com.drake.brv.item.ItemExpand

open class Group3Model(
    override var itemGroupPosition: Int = 0,
    override var itemExpand: Boolean = false,
) : ItemExpand {
    override fun getItemSublist(): List<Any>? {
        return null
    }
}