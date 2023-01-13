package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentViewBindingBinding
import com.drake.brv.sample.databinding.ItemCommentBinding
import com.drake.brv.sample.databinding.ItemSimpleTextBinding
import com.drake.brv.sample.model.SimpleBindingModel
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
            addType<SimpleBindingModel>(R.layout.item_simple_text)
            onBind {

                // 单一类型不用判断
                val binding = getBinding<ItemSimpleTextBinding>() // 使用ViewBinding/DataBinding都可以使用本方法
                binding.tvSimple.text = layoutPosition.toString()

                // 如果是多类型可以通过判断ViewBinding类型分开处理
                when (val viewBinding = getBinding<ViewBinding>()) {
                    is ItemSimpleTextBinding -> {
                        viewBinding.tvSimple.text = layoutPosition.toString()
                    }
                    is ItemCommentBinding -> {
                        viewBinding.tvContent.text = layoutPosition.toString()
                    }
                }
            }
            R.id.tv_simple.onClick {
                toast("点击文本")
            }
        }.models = getData()
    }


    private fun getData(): MutableList<Any> {
        // 在Model中也可以绑定数据
        return mutableListOf<Any>().apply {
            for (i in 0..9) add(SimpleBindingModel())
        }
    }
}