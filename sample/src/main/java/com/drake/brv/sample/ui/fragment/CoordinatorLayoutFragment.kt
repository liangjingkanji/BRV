package com.drake.brv.sample.ui.fragment

import android.widget.TextView
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentCoordinatorLayoutBinding
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.tooltip.toast

class CoordinatorLayoutFragment :
    EngineFragment<FragmentCoordinatorLayoutBinding>(R.layout.fragment_coordinator_layout) {
    override fun initView() {
        binding.rv.linear().setup {
            addType<SimpleModel>(R.layout.item_simple)
            onBind {
                findView<TextView>(R.id.tv_simple).text = getModel<SimpleModel>().name
            }
            R.id.tv_simple.onClick {
                toast("点击文本")
            }
        }.models = getData()
    }

    private fun getData(): MutableList<Any> {
        // 在Model中也可以绑定数据
        return mutableListOf<Any>().apply {
            for (i in 0..9) add(SimpleModel())
        }
    }

    override fun initData() {
    }
}