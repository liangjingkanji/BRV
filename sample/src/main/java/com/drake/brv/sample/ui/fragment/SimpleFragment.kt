package com.drake.brv.sample.ui.fragment

import android.widget.TextView
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentSimpleBinding
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.tooltip.toast

class SimpleFragment : EngineFragment<FragmentSimpleBinding>(R.layout.fragment_simple) {

    override fun initView() {

        // LayoutManager可以通过 linear/grid/staggered 等函数或者在layout布局中通过属性设置都行, 简单的建议在布局设置
        binding.rv.linear().setup {
            addType<SimpleModel>(R.layout.item_simple_text)
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