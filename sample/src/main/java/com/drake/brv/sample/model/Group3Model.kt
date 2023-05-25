package com.drake.brv.sample.model

import com.drake.brv.item.ItemExpand

open class Group3Model(
    override var itemGroupPosition: Int = 0,
    override var itemExpand: Boolean = false,
    override var itemSublist: List<Any?>? = null,
) : ItemExpand