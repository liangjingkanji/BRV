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
import com.drake.brv.sample.databinding.FragmentRefreshBinding
import com.drake.brv.sample.model.DoubleItemModel
import com.drake.brv.sample.model.Model
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.tooltip.longToast
import com.drake.tooltip.toast
import java.lang.RuntimeException
import java.util.concurrent.atomic.AtomicBoolean


class RefreshFragment : EngineFragment<FragmentRefreshBinding>(R.layout.fragment_refresh) {

    private val testError = AtomicBoolean(true)
    private val total = 5

    override fun initView() {
        setHasOptionsMenu(true)

        binding.rv.linear().setup {
            addType<Model>(R.layout.item_multi_type_simple)
            addType<DoubleItemModel>(R.layout.item_multi_type_two)
        }

        binding.page.onRefresh {
            val runnable = { // 模拟网络请求, 创建假的数据集
                if (index == 3 && testError.getAndSet(false)){
                    longToast("现在为模拟请求失败, 自动化需求可使用`Net`")
                    binding.page.showError(RuntimeException("test exception."))
                }else{
                    val data = getData()
                    addData(data) {
                        index < total // 判断是否有更多页
                    }

                    // addData(data, binding.rv.bindingAdapter, isEmpty = {
                    //     true // 此处判断是否存在下一页
                    // }, hasMore = {
                    //     false // 此处判断是否显示空布局
                    // })
                }
            }
            postDelayed(runnable, 2000)

            toast("右上角菜单可以操作刷新结果, 默认2s结束")
        }.autoRefresh()
    }

    private fun getData(): List<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..9) {
                when (i) {
                    1, 2 -> add(DoubleItemModel())
                    else -> add(Model())
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_loading -> binding.page.showLoading()  // 加载中
            R.id.menu_pull_refresh -> binding.page.autoRefresh() // 下拉刷新
            R.id.menu_refresh -> binding.page.refresh() // 静默刷新
            R.id.menu_content -> binding.page.showContent() // 加载成功
            R.id.menu_error -> binding.page.showError(force = true) // 强制加载错误
            R.id.menu_empty -> binding.page.showEmpty() // 空数据
            R.id.menu_refresh_success -> binding.page.finish() // 刷新成功
            R.id.menu_refresh_fail -> binding.page.finish(false) // 刷新失败
            R.id.menu_no_load_more -> binding.page.finishLoadMoreWithNoMoreData() // 没有更多数据
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
    }
}
