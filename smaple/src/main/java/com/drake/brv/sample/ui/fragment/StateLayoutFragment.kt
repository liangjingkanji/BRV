/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.drake.brv.sample.R
import com.drake.brv.sample.model.DoubleItemModel
import com.drake.brv.sample.model.Model
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.statelayout.StateLayout
import com.drake.statelayout.state
import kotlinx.android.synthetic.main.fragment_state_layout.*


class StateLayoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_state_layout, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv.linear().setup {
            addType<Model>(R.layout.item_multi_type_simple)
            addType<DoubleItemModel>(R.layout.item_multi_type_two)
        }.models = getData()

        val state = state()

        initToolbar(state)
    }

    private fun getData(): List<Any> {
        return listOf(
            Model(),
            DoubleItemModel(),
            DoubleItemModel(),
            Model(),
            Model(),
            Model(),
            Model(),
            DoubleItemModel(),
            DoubleItemModel(),
            DoubleItemModel(),
            Model(),
            Model(),
            Model()
        )
    }


    private fun initToolbar(state: StateLayout) {
        val toolbar: Toolbar = requireActivity().findViewById(R.id.toolbar)
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
