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
import com.drake.brv.sample.model.OneMoreTypeModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_one_more_type.*


class OneMoreTypeFragment : Fragment(R.layout.fragment_one_more_type) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_one_more_type.linear().setup {
            addType<OneMoreTypeModel> {
                when (type) {
                    0 -> R.layout.item_multi_type_one
                    else -> R.layout.item_multi_type_two
                }
            }
        }.models = getData()
    }

    private fun getData(): List<OneMoreTypeModel> {
        return listOf(
            OneMoreTypeModel(0),
            OneMoreTypeModel(0),
            OneMoreTypeModel(1),
            OneMoreTypeModel(1),
            OneMoreTypeModel(0),
            OneMoreTypeModel(0),
            OneMoreTypeModel(1),
            OneMoreTypeModel(1),
            OneMoreTypeModel(0),
            OneMoreTypeModel(0),
            OneMoreTypeModel(0),
            OneMoreTypeModel(1),
            OneMoreTypeModel(0),
            OneMoreTypeModel(0),
            OneMoreTypeModel(0),
            OneMoreTypeModel(1),
            OneMoreTypeModel(0),
            OneMoreTypeModel(0)
        )
    }

}
