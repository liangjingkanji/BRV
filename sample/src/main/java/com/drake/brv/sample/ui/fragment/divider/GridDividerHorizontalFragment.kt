package com.drake.brv.sample.ui.fragment.divider

import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentGridDividerBinding
import com.drake.brv.sample.model.DividerModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.setup

class GridDividerHorizontalFragment :
    BaseDividerFragment<FragmentGridDividerBinding>(R.layout.fragment_grid_divider) {

    override fun initView() {
        binding.rv.grid(3, orientation = RecyclerView.HORIZONTAL).divider {
            setDrawable(R.drawable.divider_horizontal)
            orientation = DividerOrientation.GRID
        }.setup {
            addType<DividerModel>(R.layout.item_divider_horizontal)
        }.models = getData()
    }

    fun getData(): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..16) add(DividerModel())
        }
    }

    override fun initData() {
    }
}