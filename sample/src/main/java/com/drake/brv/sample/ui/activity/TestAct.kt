package com.drake.brv.sample.ui.activity

import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.ActTestBinding
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineActivity

class TestAct : EngineActivity<ActTestBinding>(R.layout.act_test) {
    override fun initData() {
    }

    private fun loadData() {
        val data = (0..9).map { SimpleModel() }
        binding.refresh.addData(data) {
            data.size == 20
        }
    }

    override fun initView() {
        binding.recyclerview.linear().setup {
            addType<SimpleModel>(R.layout.item_simple)
        }
        binding.refresh.onRefresh { loadData() }.refreshing()
    }
}