package com.drake.brv.item

/**
 * 可展开/折叠的条目组内item必须要继承此接口，标记它是组的末尾成员。
 */
interface ItemGroup {

    /** 同级别的分组的索引位置, 实现但不需要赋值，其值自动设置*/
    var itemGroupPosition: Int

    /**父组,依赖的父group对象,它的父组一定是个[ItemExpand]类型的，实现但不需要复制，其值自动设置**/
    var itemParent: ItemExpand?
}
