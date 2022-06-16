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

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentPagePartStateHeaderBinding
import com.drake.brv.sample.model.Model
import com.drake.brv.sample.model.TwoSpanModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment


class PagePartStateFragment : EngineFragment<FragmentPagePartStateHeaderBinding>(R.layout.fragment_page_part_state_header) {

    private val total = 6

    override fun initView() {
        setHasOptionsMenu(true)

        binding.rv.linear().setup {
            addType<Model>(R.layout.item_multi_type_simple)
            addType<TwoSpanModel>(R.layout.item_multi_type_two_span)
        }

        binding.page.onRefresh {
            val runnable = { // 模拟网络请求, 创建假的数据集
                val data = getData()
                addData(data) {
                    index < total // 判断是否有更多页
                }
            }
            postDelayed(runnable, 2000)
        }.autoRefresh()
    }

    private fun getData(): List<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..9) {
                when (i) {
                    1, 2 -> add(TwoSpanModel())
                    else -> add(Model())
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_state, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_loading -> binding.page.showLoading()  // 加载中
            R.id.menu_content -> binding.page.showContent() // 加载成功
            R.id.menu_error -> binding.page.showError(force = true) // 强制加载错误
            R.id.menu_empty -> binding.page.showEmpty() // 空数据
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
    }
}
