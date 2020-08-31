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
import com.drake.brv.sample.R
import com.drake.brv.sample.model.Model
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_up_fetch.*

/**
 * 聊天室列表下拉加载历史记录示例
 */
class UpFetchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_up_fetch, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_up_fetch.setup {
            addType<Model>(R.layout.item_multi_type_simple)
        }

        page.upFetchEnabled = true

        page.onRefresh {
            // 模拟网络请求2秒后成功
            postDelayed({
                val data = getData()
                addData(data) { index <= 2 }
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