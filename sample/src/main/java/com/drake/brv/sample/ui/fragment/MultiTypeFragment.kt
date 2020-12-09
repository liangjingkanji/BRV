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
import com.drake.brv.sample.R
import com.drake.brv.sample.model.DoubleItemModel
import com.drake.brv.sample.model.Model
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast
import kotlinx.android.synthetic.main.fragment_multi_type.*


class MultiTypeFragment : Fragment(R.layout.fragment_multi_type) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_multi_type.linear().setup {
            addType<Model>(R.layout.item_multi_type_simple)
            addType<DoubleItemModel>(R.layout.item_multi_type_two)
        }.models = getData()

        // 点击事件
        rv_multi_type.bindingAdapter.onClick(R.id.item) {
            when (itemViewType) {
                R.layout.item_multi_type_simple -> toast("类型1")
                else -> toast("类型2")
            }
        }
    }


    private fun getData(): MutableList<Any> {
        return mutableListOf(
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
}
