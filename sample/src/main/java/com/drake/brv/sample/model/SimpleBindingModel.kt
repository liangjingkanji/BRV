package com.drake.brv.sample.model

import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind

class SimpleBindingModel() : ItemBind {

    // 多类型列表请注意区分类型 https://liangjingkanji.github.io/BRV/multi-type.html#_5
    override fun onBind(vh: BindingAdapter.BindingViewHolder) {
        // val binding = holder.getBinding<ItemViewBindingBinding>()
        // binding.tvSimple.text = holder.layoutPosition.toString()
    }
}