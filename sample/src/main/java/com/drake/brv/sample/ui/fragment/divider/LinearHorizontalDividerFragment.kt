package com.drake.brv.sample.ui.fragment.divider

import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentLinearHorizontalDividerBinding
import com.drake.brv.sample.model.DividerModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

class LinearHorizontalDividerFragment :
    BaseDividerFragment<FragmentLinearHorizontalDividerBinding>(R.layout.fragment_linear_horizontal_divider) {

    override fun initView() {
        binding.rv.linear().divider(R.drawable.divider_horizontal).setup {
            addType<DividerModel>(R.layout.item_divider_vertical)
        }.models = getData()
    }

    fun getData(): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..3) {
                add(DividerModel())
            }
        }
    }

    override fun initData() {
    }
}