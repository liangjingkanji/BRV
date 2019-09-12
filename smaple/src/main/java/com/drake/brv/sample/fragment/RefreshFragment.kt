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
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.drake.brv.PageRefreshLayout
import com.drake.brv.sample.R
import com.drake.brv.sample.model.MultiType1Model
import com.drake.brv.sample.model.MultiType2Model
import com.drake.brv.utils.linear
import com.drake.brv.utils.page
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

    override fun onStart() {
        super.onStart()

        /**
         * 请查看Application的初始化
         */

        val page = rv.page(loadMoreEnabled = true, stateEnabled = true)


        rv.linear().setup {
            addType<MultiType1Model>(R.layout.item_multi_type_1)
            addType<MultiType2Model>(R.layout.item_multi_type_2)
        }.models = listOf(
            MultiType1Model(),
            MultiType2Model(),
            MultiType2Model(),
            MultiType1Model(),
            MultiType1Model(),
            MultiType1Model(),
            MultiType1Model(),
            MultiType2Model(),
            MultiType2Model(),
            MultiType2Model(),
            MultiType1Model(),
            MultiType1Model(),
            MultiType1Model()
        )


        page.onRefresh {
            // 模拟网络请求
            postDelayed({ showContent() }, 2000)
        }


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
                R.id.menu_error -> page.showError() // 加载错误
                R.id.menu_empty -> page.showEmpty() // 空数据
                R.id.menu_refresh_success -> page.finish() // 刷新成功
                R.id.menu_refresh_fail -> page.finish(false) // 刷新失败
                R.id.menu_no_load_more -> page.finishLoadMoreWithNoMoreData() // 没有更多数据
            }
            true
        }
    }


}
