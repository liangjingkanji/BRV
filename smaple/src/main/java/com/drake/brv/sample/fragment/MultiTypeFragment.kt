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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.drake.brv.linear
import com.drake.brv.sample.R
import com.drake.brv.sample.model.MultiType1Model
import com.drake.brv.sample.model.MultiType2Model
import com.drake.brv.setup
import kotlinx.android.synthetic.main.fragment_multi_type.*


class MultiTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_multi_type, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_multi_type.linear().setup {
            addType<MultiType1Model>(R.layout.item_multi_type_1)
            addType<MultiType2Model>(R.layout.item_multi_type_2)

            onClick(R.id.item) {
                when (itemViewType) {
                    R.layout.item_multi_type_1 -> {
                        Toast.makeText(activity, "类型1", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(activity, "类型2", Toast.LENGTH_SHORT).show()
                    }
                }
            }

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
    }


}
