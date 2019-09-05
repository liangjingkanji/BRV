/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv.sample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.drake.brv.linear
import com.drake.brv.sample.R
import com.drake.brv.sample.model.OneMoreTypeModel
import com.drake.brv.setup
import kotlinx.android.synthetic.main.fragment_one_more_type.*


class OneMoreTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_one_more_type, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_one_more_type.linear().setup {
            addType<OneMoreTypeModel> {
                when (type) {
                    0 -> R.layout.item_multi_type_1
                    else -> R.layout.item_multi_type_2
                }
            }
        }.models = listOf(
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
