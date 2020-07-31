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
