package com.drake.brv.sample.ui.fragment.divider

import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGridVerticalDividerBinding
import com.drake.brv.sample.model.DividerModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup

class GridVerticalDividerFragment :
    BaseDividerFragment<FragmentGridVerticalDividerBinding>(R.layout.fragment_grid_vertical_divider) {

    override fun initView() {
        binding.rv.grid(3, RecyclerView.HORIZONTAL)
            .divider(R.drawable.divider_vertical, DividerOrientation.VERTICAL).setup {
                addType<DividerModel>(R.layout.item_divider_horizontal)
            }.models = getData()
    }

    fun getData(): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..12) {
                add(DividerModel())
            }
        }
    }

    override fun initData() {
    }
}