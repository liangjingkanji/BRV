package com.drake.brv.sample.model

import com.drake.brv.annotaion.ItemOrientation
import com.drake.brv.item.ItemDrag
import com.drake.brv.item.ItemSwipe

/** 为[Group1Model]添加侧滑拖拽功能 */
class GroupDrag1Model(
    override var itemOrientationDrag: Int = ItemOrientation.ALL, // 拖拽方向
    override var itemOrientationSwipe: Int = ItemOrientation.HORIZONTAL // 侧滑方向
) : ItemDrag, ItemSwipe, Group1Model() {

    private val draggableSublist = MutableList(4) { GroupDrag2Model() }

    // 这里要求子列表也是可以侧滑的所以重写该字段, 并且得是可变集合, 否则无法子列表被删除
    override fun getItemSublist(): List<Any?> {
        // 如果每次返回不同的对象, 会导致分组删除子列表后折叠/展开抛出异常
        return draggableSublist
    }

}