/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.util.Log
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
                Log.d("日志", "网络请求")
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