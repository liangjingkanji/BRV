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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.annotaion.Orientation
import com.drake.brv.layoutmanager.HoverGridLayoutManager
import com.drake.brv.sample.R
import com.drake.brv.sample.model.StaggeredModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_divider.*


class DividerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_divider, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 动态跨度同样支持均分分割线
        rv_grid.layoutManager = HoverGridLayoutManager(context, 3, RecyclerView.VERTICAL, false).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == 3) 2 else 1
                }
            }
        }

        rv_grid.divider {
            orientation = Orientation.GRID
            setColorRes(R.color.dividerDecoration)
            setDivider(30, true)
            startVisible = true // 网格布局中水平方向首尾显示分割线
            endVisible = true // 网格布局中垂直方向首尾显示分割线
        }.setup {
            addType<StaggeredModel>(R.layout.item_staggered)

            // 模拟瀑布流动态宽高, 根据数据动态改变宽高, 瀑布流不建议宽高同时都可变
            // onBind {
            //     val model = getModel<StaggeredModel>()
            //     itemView.layoutParams.apply {
            //         // width = model.width
            //         height = model.height
            //         itemView.layoutParams = this
            //     }
            //     false
            // }

        }.models = getData()
    }


    private fun getData(): List<Any> {
        val data = mutableListOf<Any>()
        // 模拟瀑布流动态宽高
        for (i in 0..9) {
            when (i) {
                2 -> data.add(StaggeredModel(height = 400, width = 200))
                3 -> data.add(StaggeredModel(height = 400, width = 150))
                5 -> data.add(StaggeredModel(height = 800))
                4 -> data.add(StaggeredModel(height = 1000))
                8 -> data.add(StaggeredModel(height = 500))
                11 -> data.add(StaggeredModel(height = 700))
                else -> data.add(StaggeredModel())
            }
        }
        return data
    }

}
