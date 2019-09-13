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
import com.drake.brv.sample.R
import com.drake.brv.sample.model.Model
import com.drake.brv.sample.model.Model2
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.statelayout.StateLayout
import com.drake.statelayout.state
import kotlinx.android.synthetic.main.fragment_state.*


class StateFragment : Fragment() {

    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_state, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /**
         * 请查看Application的初始化
         */


        rv.linear().setup {
            addType<Model>(R.layout.item_multi_type_1)
            addType<Model2>(R.layout.item_multi_type_2)
        }.models = listOf(
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


        val state = state()

        initToolbar(state)
    }


    private fun initToolbar(state: StateLayout) {
        toolbar = activity!!.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_state)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_loading -> state.showLoading()  // 加载中
                R.id.menu_content -> state.showContent() // 加载成功
                R.id.menu_error -> state.showError() // 加载错误
                R.id.menu_empty -> state.showEmpty() // 加载失败
            }
            true
        }
    }

}
