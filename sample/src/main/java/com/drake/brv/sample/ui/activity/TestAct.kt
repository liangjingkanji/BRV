package com.drake.brv.sample.ui.activity

import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.ActTestBinding
import com.drake.brv.sample.model.FullSpanModel
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineActivity

class TestAct : EngineActivity<ActTestBinding>(R.layout.act_test) {
    override fun initData() {
    }

    private fun loadData() {
        binding.refresh.setEnableRefresh(false)
        val data = mutableListOf<Any>().apply {
            for (i in 0..9) {
                when (i) {
                    1, 2 -> add(FullSpanModel())
                    else -> add(SimpleModel())
                }
            }
        }
        binding.refresh.addData(data) { false }
    }

    override fun initView() {
        binding.recyclerview.linear().setup {
            addType<SimpleModel>(R.layout.item_simple)
            addType<FullSpanModel>(R.layout.item_full_span)
        }
        binding.refresh.setEnableLoadMore(false)
        binding.refresh.onRefresh { loadData() }.refreshing()
    }

}