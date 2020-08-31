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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drake.brv.sample.R
import com.drake.brv.sample.model.DividerModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.setup
import com.drake.brv.utils.staggered
import kotlinx.android.synthetic.main.fragment_staggered_divider.*

class StaggeredDividerFragment : BaseDividerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_staggered_divider, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv.staggered(3).divider(R.drawable.divider_horizontal).setup {
            addType<DividerModel>(R.layout.item_divider_horizontal)
            onBind {
                // 设置动态高度
                val layoutParams = itemView.layoutParams
                layoutParams.height = getModel<DividerModel>().height
                itemView.layoutParams = layoutParams
            }
        }.models = getData()
    }

    private fun getData(): List<Any> {
        val data = mutableListOf<Any>()
        // 生成动态宽高
        for (i in 0..9) {
            when (i) {
                2 -> data.add(DividerModel(400))
                3 -> data.add(DividerModel(500))
                4 -> data.add(DividerModel(700))
                5 -> data.add(DividerModel(1000))
                8 -> data.add(DividerModel(200))
                11 -> data.add(DividerModel(1200))
                else -> data.add(DividerModel())
            }
        }
        return data
    }
}