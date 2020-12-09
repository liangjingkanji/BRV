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
import androidx.fragment.app.Fragment
import com.drake.brv.PageRefreshLayout
import com.drake.brv.sample.R
import com.drake.brv.sample.model.Model
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_preload.*

/**
 * 指定预加载, 默认反序3 开始预加载, 可设置全局变量 [PageRefreshLayout.preloadIndex]
 */
class PreloadFragment : Fragment(R.layout.fragment_preload) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_pre_load.setup {
            addType<Model>(R.layout.item_multi_type_simple)
        }

        // page.preloadIndex = 4 // 自定义列表倒数第4个开始预加载, 默认为3

        page.onRefresh {
            // 模拟网络请求2秒后成功
            postDelayed({
                val data = getData()
                addData(data) {
                    index <= 3
                }
            }, 1000)
        }.showLoading() //  加载中(缺省页)
    }

    /**
     * 模拟数据
     */
    private fun getData(): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..9) {
                add(Model())
            }
        }
    }
}