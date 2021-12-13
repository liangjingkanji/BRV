/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.brv.sample.ui.fragment.group

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemExpand
import com.drake.brv.listener.DefaultItemTouchCallback
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGroupDragBinding
import com.drake.brv.sample.model.GroupBasicModel
import com.drake.brv.sample.model.GroupDragModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast


class GroupDragFragment : BaseGroupFragment<FragmentGroupDragBinding>(R.layout.fragment_group_drag) {

    override fun initView() {
        binding.rv.linear().setup {
            addType<GroupDragModel>(R.layout.item_group_title)
            addType<GroupBasicModel>(R.layout.item_group_basic)

            // 自定义部分实现
            itemTouchHelper = ItemTouchHelper(object : DefaultItemTouchCallback() {
                override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                    if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) { // 拖拽移动分组前先折叠子列表
                        (viewHolder as BindingAdapter.BindingViewHolder).collapse()
                    }
                    super.onSelectedChanged(viewHolder, actionState)
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    (viewHolder as BindingAdapter.BindingViewHolder).collapse() // 侧滑删除分组前先折叠子列表
                    super.onSwiped(viewHolder, direction)
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

    override fun initData() {
    }

}
