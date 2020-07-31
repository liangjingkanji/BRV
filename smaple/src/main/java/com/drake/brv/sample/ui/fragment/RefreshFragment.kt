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
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.drake.brv.PageRefreshLayout
import com.drake.brv.sample.R
import com.drake.brv.sample.model.DoubleItemModel
import com.drake.brv.sample.model.Model
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast
import kotlinx.android.synthetic.main.fragment_refresh.*


class RefreshFragment : Fragment() {

    private val total = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_refresh, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv.linear().setup {
            addType<Model>(R.layout.item_multi_type_simple)
            addType<DoubleItemModel>(R.layout.item_multi_type_two)
        }

        /*
         * 关于自动化分页加载请查看我的网络请求库 Net : https://github.com/liangjingkanji/Net
         */
        page.onRefresh {
            postDelayed({ // 模拟网络请求, 创建假的数据集
                val data = getData()
                addData(data) {
                    index < total // 判断是否有更多页
                }
                // addData(data, {
                //     true // 此处判断是否存在下一页
                // }, {
                //     false // 此处判断是否显示空布局
                // })
            }, 2000)

            toast("右上角菜单可以操作刷新结果, 默认2s结束")

        }.autoRefresh()

        initToolbar(page)
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


    private fun initToolbar(page: PageRefreshLayout) {
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_refresh)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_loading -> page.showLoading()  // 加载中
                R.id.menu_pull_refresh -> page.autoRefresh() // 下拉刷新
                R.id.menu_refresh -> page.refresh() // 静默刷新
                R.id.menu_content -> page.showContent() // 加载成功
                R.id.menu_error -> page.showError(force = true) // 强制加载错误
                R.id.menu_empty -> page.showEmpty() // 空数据
                R.id.menu_refresh_success -> page.finish() // 刷新成功
                R.id.menu_refresh_fail -> page.finish(false) // 刷新失败
                R.id.menu_no_load_more -> page.finishLoadMoreWithNoMoreData() // 没有更多数据
            }
            true
        }
    }


}
