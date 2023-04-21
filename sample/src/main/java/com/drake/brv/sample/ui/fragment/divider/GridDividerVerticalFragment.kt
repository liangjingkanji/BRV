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

package com.drake.brv.sample.ui.fragment.divider

import com.drake.brv.annotaion.AnimationType
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGridDividerBinding
import com.drake.brv.sample.model.DividerModel
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup

class GridDividerVerticalFragment :
    BaseDividerFragment<FragmentGridDividerBinding>(R.layout.fragment_grid_divider) {

    private val dataList = mutableListOf<Any>()

    override fun initView() {
        binding.btMainAdd.setOnClickListener {
            dataList.add(DividerModel())
            binding.rv.bindingAdapter.notifyItemInserted(dataList.size)
            // 注释前的现象为问题1 注释后的现象为问题2
            binding.rv.invalidateItemDecorations()
        }
        binding.btMainRemove.setOnClickListener {
            dataList.removeAt(dataList.size - 1)
            binding.rv.bindingAdapter.notifyItemRemoved(dataList.size)
        }
        binding.rv.grid(3).divider {
            setDrawable(R.drawable.divider_horizontal)
            orientation = DividerOrientation.GRID
            includeVisible = true
        }.setup {
            addType<DividerModel>(R.layout.item_divider_vertical)
            setAnimation(AnimationType.SLIDE_RIGHT)
            animationRepeat = true
        }.models = getData()
    }

    fun getData(): MutableList<Any> {
        for (i in 0..10) dataList.add(DividerModel())
        return dataList
    }

    override fun initData() {
    }
}