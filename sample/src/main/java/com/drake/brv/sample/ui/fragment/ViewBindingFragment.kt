package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentViewBindingBinding
import com.drake.brv.sample.databinding.ItemSimpleBinding
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast

/** 更推荐使用DataBinding做MVVM架构, ViewBinding只是取代findById的简单框架而已 */
class ViewBindingFragment : Fragment(R.layout.fragment_view_binding) {

    private val binding by lazy {
        FragmentViewBindingBinding.bind(requireView())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rv.linear().setup {
            addType<SimpleModel>(R.layout.item_simple)
            onBind {
                val binding = ItemSimpleBinding.bind(itemView) // 使用ViewBinding
                binding.tvSimple.text = layoutPosition.toString()
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
}