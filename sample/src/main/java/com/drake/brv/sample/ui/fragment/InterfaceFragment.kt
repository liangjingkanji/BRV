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
import com.drake.brv.sample.databinding.FragmentInterfaceBinding
import com.drake.brv.sample.databinding.FragmentSimpleBinding
import com.drake.brv.sample.model.*
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.tooltip.toast

class InterfaceFragment : EngineFragment<FragmentInterfaceBinding>(R.layout.fragment_interface) {

    override fun initView() {
        binding.rv.linear().setup {
            addType<BaseInterfaceModel>(R.layout.item_interface)
            R.id.item.onClick {
                toast("点击文本")
            }
        }.models = getData()
    }

    private fun getData(): List<Any> {
        // 在Model中也可以绑定数据
        return List(3) { InterfaceModel1("item $it") } +
                List(3) { InterfaceModel2(it, "item ${3 + it}") } +
                List(3) { InterfaceModel3("item ${6 + it}") }
    }

    override fun initData() {
    }
}