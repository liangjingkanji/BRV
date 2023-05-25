package com.drake.brv.sample.ui.fragment.divider

import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGridDividerBinding
import com.drake.brv.sample.model.DividerModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup

class GridDividerVerticalFragment :
    BaseDividerFragment<FragmentGridDividerBinding>(R.layout.fragment_grid_divider) {

    override fun initView() {
        binding.rv.grid(3).divider {
            setDrawable(R.drawable.divider_horizontal)
            orientation = DividerOrientation.GRID
            includeVisible = true
        }.setup {
            addType<DividerModel>(R.layout.item_divider_vertical)
        }.models = getData()
    }

    fun getData(): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..10) add(DividerModel())
        }
    }

    override fun initData() {
    }
}