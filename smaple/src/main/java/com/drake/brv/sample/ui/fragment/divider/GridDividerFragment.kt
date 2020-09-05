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

package com.drake.brv.sample.ui.fragment.divider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.sample.R
import com.drake.brv.sample.model.DividerModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_grid_horizontal_divider.*

class GridDividerFragment : BaseDividerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grid_divider, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv.grid(3).divider {
            setDrawable(R.drawable.divider_horizontal)
            orientation = DividerOrientation.GRID
            includeVisible = true
        }.setup {
            addType<DividerModel>(R.layout.item_divider_vertical)
        }.models = getData()
    }

    fun getData(): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..10) add(DividerModel())
        }
    }
}