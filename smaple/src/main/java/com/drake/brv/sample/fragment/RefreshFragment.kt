/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：9/11/19 6:49 PM
 */

package com.drake.brv.sample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.drake.brv.PageRefreshLayout
import com.drake.brv.sample.R
import com.drake.brv.sample.model.Model
import com.drake.brv.sample.model.Model2
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_refresh.*


class RefreshFragment : Fragment() {

    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_refresh, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /**
         * 请查看Application的初始化
         *
         */


//        val page = rv.page(loadMoreEnabled = true, stateEnabled = true) 通过代码设置PageRefreshLayout
        val page = content // 在XML布局中声明PageRefreshLayout 两者选其一即可


        page.autoRefresh() // page.refresh() 属于没有动画效果的刷新

        rv.linear().setup {
            addType<Model>(R.layout.item_multi_type_1)
            addType<Model2>(R.layout.item_multi_type_2)
        }

        val total = 3

        page.onRefresh {

            // 模拟网络请求
            postDelayed({

                // 创建假的数据集
                val data = listOf(
                    Model(),
                    Model2(),
                    Model2(),
                    Model(),
                    Model(),
                    Model(),
                    Model(),
                    Model2(),
                    Model2(),
                    Model2(),
                    Model(),
                    Model(),
                    Model()
                )

                addData(data) {
                    index < total // 判断是否有更多页
                }
            }, 1000)

            Toast.makeText(activity, "右上角菜单可以操作刷新结果, 默认2s结束", Toast.LENGTH_SHORT).show()
        }

        /**
         * 关于自动化分页加载请查看我的网络请求库 Net : https://github.com/liangjingkanji/Net
         */


        initToolbar(page)
    }


    private fun initToolbar(page: PageRefreshLayout) {
        toolbar = activity!!.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_refresh)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_loading -> page.showLoading()  // 加载中
                R.id.menu_pull_refresh -> page.autoRefresh() // 下拉刷新
                R.id.menu_refresh -> page.refresh() // 静默刷新
                R.id.menu_content -> page.showContent() // 加载成功
                R.id.menu_error -> page.showError(force = true) // 加载错误
                R.id.menu_empty -> page.showEmpty() // 空数据
                R.id.menu_refresh_success -> page.finish() // 刷新成功
                R.id.menu_refresh_fail -> page.finish(false) // 刷新失败
                R.id.menu_no_load_more -> page.finishLoadMoreWithNoMoreData() // 没有更多数据
            }
            true
        }
    }


}
