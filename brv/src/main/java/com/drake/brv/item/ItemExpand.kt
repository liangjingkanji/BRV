/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.item

/**
 * 可展开/折叠的条目
 */
interface ItemExpand {
    // 同级别的分组的索引位置(非layoutPosition/adapterPosition)
    var itemGroupPosition: Int

    // 是否已展开
    var itemExpand: Boolean

    // 子列表
    var itemSublist: List<Any?>?
}