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
import com.drake.brv.sample.model.FlexTagModel
import com.drake.brv.utils.setup
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_flex_box.*


class FlexBoxFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_flex_box, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /**
         * Google开源项目-flexBox-layout
         *
         * 更多使用方法查看项目地址: https://github.com/google/flexbox-layout
         */
        rv.layoutManager = FlexboxLayoutManager(activity)

        rv.setup {
            addType<FlexTagModel>(R.layout.item_flex_tag)
        }.models = getData()
    }

    private fun getData(): List<FlexTagModel> {
        return listOf(
            FlexTagModel("吴彦祖"),
            FlexTagModel("金城武"),
            FlexTagModel("Amber Gao"),
            FlexTagModel("设计师"),
            FlexTagModel("肥宅"),
            FlexTagModel("不识妻美刘强东")
        )
    }

}
