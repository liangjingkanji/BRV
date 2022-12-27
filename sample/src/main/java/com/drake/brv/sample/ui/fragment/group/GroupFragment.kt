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

import com.drake.brv.item.ItemExpand
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGroupBinding
import com.drake.brv.sample.model.GroupBasicModel
import com.drake.brv.sample.model.GroupModel
import com.drake.brv.sample.model.GroupSecondModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast


class GroupFragment : BaseGroupFragment<FragmentGroupBinding>(R.layout.fragment_group) {

    override fun initView() {
        binding.rv.linear().setup {

            // 任何条目都需要添加类型到BindingAdapter中
            addType<GroupModel>(R.layout.item_group_title)
            addType<GroupSecondModel>(R.layout.item_group_title_second)
            addType<GroupBasicModel>(R.layout.item_group_basic)
            R.id.item.onFastClick {
                when (itemViewType) {
                    // 点击展开或折叠
                    R.layout.item_group_title_second, R.layout.item_group_title -> {

                        val changeCount =
                            if (getModel<ItemExpand>().itemExpand) "折叠 ${expandOrCollapse()} 条" else "展开 ${expandOrCollapse()} 条"

                        toast(changeCount)
                    }
                    // 点击删除嵌套分组
                    R.layout.item_group_basic -> {
                        val itemGroupPosition = getModel<GroupBasicModel>().itemGroupPosition
                        val parentPosition = findParentPosition()
                        if (parentPosition != -1) {
                            (getModel<ItemExpand>(parentPosition).itemSublist as MutableList).removeAt(itemGroupPosition)
                            mutable.removeAt(layoutPosition)
                            notifyItemRemoved(layoutPosition)
                        }
                    }
                }
            }

        }.models = getData()
    }

    private fun getData(): MutableList<GroupModel> {
        return mutableListOf<GroupModel>().apply {
            for (i in 0..4) {

                // 第二个分组存在嵌套分组
                if (i == 0) {
                    val nestedGroupModel = GroupModel().apply {
                        itemSublist =
                            listOf(GroupSecondModel(), GroupSecondModel(), GroupSecondModel())
                    }
                    add(nestedGroupModel)
                    continue
                }

                add(GroupModel())
            }
        }
    }

    override fun initData() {
    }

}
