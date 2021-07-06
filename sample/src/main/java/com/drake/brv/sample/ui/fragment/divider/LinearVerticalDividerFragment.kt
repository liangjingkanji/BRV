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

import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentLinearVerticalDividerBinding
import com.drake.brv.sample.model.DividerModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

class LinearVerticalDividerFragment :
    BaseDividerFragment<FragmentLinearVerticalDividerBinding>(R.layout.fragment_linear_vertical_divider) {

    override fun initView() {
        binding.rv.linear(RecyclerView.HORIZONTAL).divider(R.drawable.divider_vertical).setup {
            addType<DividerModel>(R.layout.item_divider_horizontal)
        }.models = getData()
    }

    fun getData(): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..10) {
                add(DividerModel())
            }
        }
    }

    override fun initData() {
    }
}