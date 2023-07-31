package com.drake.brv.sample.ui.fragment

import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentInterfaceTypeBinding
import com.drake.brv.sample.model.BaseInterfaceModel
import com.drake.brv.sample.model.InterfaceModel1
import com.drake.brv.sample.model.InterfaceModel2
import com.drake.brv.sample.model.InterfaceModel3
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment

class InterfaceTypeFragment : EngineFragment<FragmentInterfaceTypeBinding>(R.layout.fragment_interface_type) {

    override fun initView() {
        binding.rv.linear().setup {
            addType<BaseInterfaceModel>(R.layout.item_interface_type)
        }.models = getData()
    }

    private fun getData(): List<Any> {
        // 在Model中也可以绑定数据
        return List(3) { InterfaceModel1("item $it") } +
                List(3) { InterfaceModel2(it, "item ${3 + it}") } +
                List(3) { InterfaceModel3("item ${6 + it}") }
    }

    override fun initData() {
    }
}