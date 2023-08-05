package com.drake.brv.sample.ui.fragment.group

import androidx.recyclerview.widget.GridLayoutManager
import com.drake.brv.item.ItemExpand
import com.drake.brv.layoutmanager.HoverGridLayoutManager
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGroupBinding
import com.drake.brv.sample.model.Group1Model
import com.drake.brv.sample.model.Group3Model
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.setup
import com.drake.tooltip.toast


class GroupGridFragment : BaseGroupFragment<FragmentGroupBinding>(R.layout.fragment_group) {

    override fun initView() {
        val layoutManager = HoverGridLayoutManager(requireContext(), 2) // 2 则代表列表一行铺满要求跨度为2
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position < 0) return 1 // 如果添加分割线可能导致position为负数
                // 根据类型设置列表item跨度
                return when (binding.rv.bindingAdapter.getItemViewType(position)) {
                    R.layout.item_group_3 -> 1 // 设置指定类型的跨度为1, 假设spanCount为2则代表此类型占据宽度为二分之一
                    else -> 2
                }
            }
        }
        binding.rv.layoutManager = layoutManager
        binding.rv.setup {
            addType<Group1Model>(R.layout.item_group_1)
            addType<Group3Model>(R.layout.item_group_3)
            R.id.item.onFastClick {
                when (itemViewType) {
                    R.layout.item_group_2, R.layout.item_group_1 -> {

                        val changeCount =
                            if (getModel<ItemExpand>().itemExpand) "折叠 ${expandOrCollapse()} 条" else "展开 ${expandOrCollapse()} 条"

                        toast(changeCount)
                    }
                }
            }

        }.models = getData()
    }

    private fun getData(): MutableList<Group1Model> {
        return MutableList(4) { Group1Model() }
    }

    override fun initData() {
    }

}
