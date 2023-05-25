package com.drake.brv.sample.ui.fragment.divider

import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentLinearVerticalDividerBinding
import com.drake.brv.sample.model.DividerModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

class LinearVerticalDividerFragment :
    BaseDividerFragment<FragmentLinearVerticalDividerBinding>(R.layout.fragment_linear_vertical_divider) {

    override fun initView() {
        binding.rv.linear(RecyclerView.HORIZONTAL).divider(R.drawable.divider_vertical).setup {
            addType<DividerModel>(R.layout.item_divider_horizontal)
        }.models = getData()
    }

    fun getData(): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..10) {
                add(DividerModel())
            }
        }
    }

    override fun initData() {
    }
}