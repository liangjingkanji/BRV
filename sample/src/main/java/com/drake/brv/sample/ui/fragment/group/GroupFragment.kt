package com.drake.brv.sample.ui.fragment.group

import com.drake.brv.item.ItemExpand
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGroupBinding
import com.drake.brv.sample.model.Group1Model
import com.drake.brv.sample.model.Group2Model
import com.drake.brv.sample.model.Group3Model
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast


class GroupFragment : BaseGroupFragment<FragmentGroupBinding>(R.layout.fragment_group) {

    override fun initView() {
        binding.rv.linear().setup {

            // 任何条目都需要添加类型到BindingAdapter中
            addType<Group1Model>(R.layout.item_group_1)
            addType<Group2Model>(R.layout.item_group_2)
            addType<Group3Model>(R.layout.item_group_3)
            R.id.item.onFastClick {
                when (itemViewType) {
                    // 点击展开或折叠
                    R.layout.item_group_2, R.layout.item_group_1 -> {

                        val changeCount =
                            if (getModel<ItemExpand>().itemExpand) "折叠 ${expandOrCollapse()} 条" else "展开 ${expandOrCollapse()} 条"

                        toast(changeCount)
                    }
                    // 点击删除嵌套分组
                    R.layout.item_group_3 -> {
                        val model = getModel<Group3Model>()
                        val parentPosition = findParentPosition()
                        if (parentPosition != -1) {
                            (getModel<ItemExpand>(parentPosition).getItemSublist() as MutableList).remove(model)
                            mutable.removeAt(layoutPosition)
                            notifyItemRemoved(layoutPosition)
                        }
                    }
                }
            }

        }.models = getData()
    }

    private fun getData(): MutableList<Group1Model> {
        return mutableListOf<Group1Model>().apply {
            for (i in 0..4) {

                // 第二个分组存在嵌套分组
                if (i == 0) {
                    val nestedGroupModel = Group1Model().apply {
                        sublist = MutableList(3) { Group2Model() }
                    }
                    add(nestedGroupModel)
                    continue
                }

                add(Group1Model())
            }
        }
    }

    override fun initData() {
    }

}
