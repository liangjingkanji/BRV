package com.drake.brv.sample.ui.fragment.group

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemExpand
import com.drake.brv.listener.DefaultItemTouchCallback
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGroupDragBinding
import com.drake.brv.sample.model.GroupDrag1Model
import com.drake.brv.sample.model.GroupDrag2Model
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast


class GroupDragFragment : BaseGroupFragment<FragmentGroupDragBinding>(R.layout.fragment_group_drag) {

    override fun initView() {
        binding.rv.linear().setup {
            addType<GroupDrag1Model>(R.layout.item_group_1)
            addType<GroupDrag2Model>(R.layout.item_group_3)

            // 自定义部分实现
            itemTouchHelper = ItemTouchHelper(object : DefaultItemTouchCallback() {

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val vh = viewHolder as BindingAdapter.BindingViewHolder
                    vh.collapse() // 侧滑删除分组前先折叠子列表
                    super.onSwiped(viewHolder, direction)

                    // 如果侧滑删除的是子列表, 要删除对应分组的getItemSublist, 避免刷新再次被加载出来
                    // getItemSublist必须发挥可变集合, 否则无法删除
                    (vh.findParentViewHolder()?.getModelOrNull<ItemExpand>()?.getItemSublist() as? MutableList)?.remove(vh.getModelOrNull())
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    source: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    // 拖拽分组前先折叠子列表
                    (source as BindingAdapter.BindingViewHolder).collapse()
                    (target as BindingAdapter.BindingViewHolder).collapse()
                    return super.onMove(recyclerView, source, target)
                }
            })

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

    private fun getData(): MutableList<GroupDrag1Model> {
        return MutableList(4) { GroupDrag1Model() }
    }

    override fun initData() {
    }

}
