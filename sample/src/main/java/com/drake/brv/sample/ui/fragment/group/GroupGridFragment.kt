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

import androidx.recyclerview.widget.GridLayoutManager
import com.drake.brv.item.ItemExpand
import com.drake.brv.layoutmanager.HoverGridLayoutManager
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGroupBinding
import com.drake.brv.sample.model.GroupBasicModel
import com.drake.brv.sample.model.GroupModel
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
                    R.layout.item_group_basic -> 1 // 设置指定类型的跨度为1, 假设spanCount为2则代表此类型占据宽度为二分之一
                    else -> 2
                }
            }
        }
        binding.rv.layoutManager = layoutManager
        binding.rv.setup {
            addType<GroupModel>(R.layout.item_group_title)
            addType<GroupBasicModel>(R.layout.item_group_basic)
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

    private fun getData(): MutableList<GroupModel> {
        return mutableListOf<GroupModel>().apply {
            repeat(4) {
                add(GroupModel())
            }
        }
    }

    override fun initData() {
    }

}
