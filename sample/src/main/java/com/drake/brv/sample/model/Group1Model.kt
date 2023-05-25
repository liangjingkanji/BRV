package com.drake.brv.sample.model

import androidx.databinding.BaseObservable
import com.drake.brv.item.ItemExpand
import com.drake.brv.item.ItemHover
import com.drake.brv.item.ItemPosition
import com.drake.brv.sample.R

open class Group1Model : ItemExpand, ItemHover, ItemPosition,
    BaseObservable() {

    override var itemGroupPosition: Int = 0
    override var itemExpand: Boolean = false
        set(value) {
            field = value
            notifyChange()
        }

    /** 由于类型是List<Any>所以本字段不能用于json解析, 所以使用真实字段jsonSublist代理 */
    override var itemSublist: List<Any?>?
        get() = jsonSublist
        set(value) {
            jsonSublist = value as List<Group3Model> // 注意类型转换异常
        }

    /** 接口数据里面的子列表使用此字段接收(请注意避免gson等框架解析kotlin会覆盖字段默认值问题) */
    var jsonSublist: List<Group3Model> = mutableListOf(Group3Model(), Group3Model(), Group3Model(), Group3Model())

    override var itemHover: Boolean = true
    override var itemPosition: Int = 0

    val title get() = "分组 [ $itemGroupPosition ]"

    val expandIcon get() = if (itemExpand) R.drawable.ic_arrow_expand else R.drawable.ic_arrow_collapse

}