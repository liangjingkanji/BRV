package com.drake.brv.sample.ui.fragment

import com.drake.brv.annotaion.ItemOrientation
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentDragBinding
import com.drake.brv.sample.model.DragModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment

class DragFragment : EngineFragment<FragmentDragBinding>(R.layout.fragment_drag) {

    override fun initView() {

        // 侧滑删除会改变数据的内容
        binding.rv.linear().setup {
            addType<DragModel>(R.layout.item_drag)
        }.models = getData()
    }

    private fun getData(): List<Any> {
        return listOf(
            DragModel(),
            DragModel(),
            DragModel(ItemOrientation.NONE), // 不支持侧滑
            DragModel(),
            DragModel(),
            DragModel(ItemOrientation.NONE), // 不支持侧滑
            DragModel(),
            DragModel()
        )
    }

    override fun initData() {
    }
}