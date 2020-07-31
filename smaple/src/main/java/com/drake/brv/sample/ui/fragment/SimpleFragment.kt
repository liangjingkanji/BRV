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
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.drake.brv.sample.R
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_simple.*

class SimpleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simple, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_simple.linear().setup {
            addType<SimpleModel>(R.layout.item_simple)
            onBind {
                findView<TextView>(R.id.tv_simple).text = getModel<SimpleModel>().name
            }
        }.models = getData()
    }

    private fun getData(): MutableList<Any> {
        // 在Model中也可以绑定数据
        return mutableListOf<Any>().apply {
            for (i in 0..9) add(SimpleModel())
        }
    }
}