package com.drake.brv.sample.ui.fragment.group

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemExpand
import com.drake.brv.listener.DefaultItemTouchCallback
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGroupSpanDragBinding
import com.drake.brv.sample.model.GroupDragBasicModel
import com.drake.brv.sample.model.GroupDragModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast

/**
 * @author Dboy
 * @since 2022/6/30 12:39
 */
class GroupSpanDragFragment :
    BaseGroupFragment<FragmentGroupSpanDragBinding>(R.layout.fragment_group_span_drag) {

    var isCanSpanGroup = false

    override fun initData() {

    }

    override fun initView() {
        binding.spanSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            isCanSpanGroup = isChecked
        }

        binding.rv.linear().setup {
            addType<GroupDragModel>(R.layout.item_group_title)
            addType<GroupDragBasicModel>(R.layout.item_group_basic)

            // 自定义部分实现
            itemTouchHelper = ItemTouchHelper(object : DefaultItemTouchCallback() {
                override fun canSpanGroups() = isCanSpanGroup

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val vh = viewHolder as BindingAdapter.BindingViewHolder
                    vh.collapse() // 侧滑删除分组前先折叠子列表
                    super.onSwiped(viewHolder, direction)

                    // 如果侧滑删除的是分组里面的子列表, 要删除对应父分组的itemSublist数据, 否则会导致数据异常
                    // itemSublist必须为可变集合, 否则无法被删除
                    (vh.findParentViewHolder()
                        ?.getModelOrNull<ItemExpand>()?.itemSublist as? ArrayList)?.remove(vh.getModelOrNull())
                }
            })

            R.id.item.onFastClick {
                when (itemViewType) {
                    R.layout.item_group_title_second, R.layout.item_group_title -> {

                        val changeCount =
                            if (getModel<ItemExpand>().itemExpand) "折叠 ${expandOrCollapse()} 条" else "展开 ${expandOrCollapse()} 条"

                        toast(changeCount)
                    }
                }
            }

        }.models = getData()
    }

    private fun getData(): MutableList<GroupDragModel> {
        return mutableListOf<GroupDragModel>().apply {
            repeat(4) {
                add(GroupDragModel())
            }
        }
    }

}