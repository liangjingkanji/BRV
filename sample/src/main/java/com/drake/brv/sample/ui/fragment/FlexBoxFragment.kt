package com.drake.brv.sample.ui.fragment

import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentFlexBoxBinding
import com.drake.brv.sample.model.LableModel
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.google.android.flexbox.FlexboxLayoutManager


class FlexBoxFragment : EngineFragment<FragmentFlexBoxBinding>(R.layout.fragment_flex_box) {

    override fun initView() {
        /**
         * Google开源项目-flexBox-layout
         * 更多使用方法查看项目地址: https://github.com/google/flexbox-layout
         */
        binding.rv.layoutManager = FlexboxLayoutManager(activity)

        binding.rv.setup {
            addType<LableModel>(R.layout.item_label)
        }.models = getData()
    }

    private fun getData(): List<LableModel> {
        return listOf(
            LableModel("淘宝"),
            LableModel("微信"),
            LableModel("QQ"),
            LableModel("UC浏览器"),
            LableModel("京东"),
            LableModel("滴滴"),
            LableModel("抖音"),
            LableModel("今日头条")
        )
    }

    override fun initData() {
    }
}
