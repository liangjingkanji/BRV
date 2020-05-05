package com.drake.brv.sample.mod

import com.drake.brv.item.ItemExpand

class NestedGroupModel : ItemExpand {

    override var itemGroupPosition: Int = 0
    override var itemExpand: Boolean = false
    override var itemSublist: List<Any?>? = listOf(Model(), Model(), Model(), Model())
    val title get() = "嵌套分组 [ $itemGroupPosition ]"

}