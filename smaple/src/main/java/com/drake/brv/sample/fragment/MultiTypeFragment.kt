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
import androidx.fragment.app.Fragment
import com.drake.brv.sample.R
import com.drake.brv.sample.model.Model
import com.drake.brv.sample.model.Model2
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast
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


        /**
         * 请查看Application的初始化
         */

        rv_multi_type.linear().setup {
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

        // 点击事件
        rv_multi_type.bindingAdapter.onClick(R.id.item) {

            when (itemViewType) {
                R.layout.item_multi_type_1 -> toast("类型1")
                else -> toast("类型2")
            }
        }
    }


}
