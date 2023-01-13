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

import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentMultiTypeBinding
import com.drake.brv.sample.model.FullSpanModel
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.tooltip.toast


class MultiTypeFragment : EngineFragment<FragmentMultiTypeBinding>(R.layout.fragment_multi_type) {

    override fun initView() {
        binding.rv.linear().setup {
            addType<SimpleModel>(R.layout.item_simple)
            addType<FullSpanModel>(R.layout.item_multi_type_two_span)
        }.models = getData()

        // 点击事件
        binding.rv.bindingAdapter.onClick(R.id.item) {
            when (itemViewType) {
                R.layout.item_simple -> toast("类型1")
                else -> toast("类型2")
            }
        }
    }

    private fun getData(): MutableList<Any> {
        return mutableListOf(
            SimpleModel(),
            FullSpanModel(),
            FullSpanModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            FullSpanModel(),
            FullSpanModel(),
            FullSpanModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel()
        )
    }

    override fun initData() {
    }
}
