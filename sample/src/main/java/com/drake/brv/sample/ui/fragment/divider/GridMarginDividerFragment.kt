package com.drake.brv.sample.ui.fragment.divider

import android.graphics.Color
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGridMarginDividerBinding
import com.drake.brv.sample.model.DividerModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup

class GridMarginDividerFragment : BaseDividerFragment<FragmentGridMarginDividerBinding>(R.layout.fragment_grid_margin_divider) {

    override fun initView() {
        binding.rv.grid(3).divider {
            orientation = DividerOrientation.GRID
            setDivider(1, true)
            setMargin(16, 0, dp = true, baseItemStart = true)
            setColor(Color.WHITE)
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