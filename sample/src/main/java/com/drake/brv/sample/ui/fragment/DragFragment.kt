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

package com.drake.brv.sample.ui.fragment

import com.drake.brv.annotaion.ItemOrientation
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentDragBinding
import com.drake.brv.sample.model.DragModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment

class DragFragment : EngineFragment<FragmentDragBinding>(R.layout.fragment_drag) {

    override fun initView() {

        // 侧滑删除会改变数据的内容
        binding.rv.linear().setup {
            addType<DragModel>(R.layout.item_drag)
        }.models = getData()
    }

    private fun getData(): List<Any> {
        return listOf(
            DragModel(),
            DragModel(),
            DragModel(ItemOrientation.NONE), // 不支持侧滑
            DragModel(),
            DragModel(),
            DragModel(ItemOrientation.NONE), // 不支持侧滑
            DragModel(),
            DragModel()
        )
    }

    override fun initData() {
    }
}