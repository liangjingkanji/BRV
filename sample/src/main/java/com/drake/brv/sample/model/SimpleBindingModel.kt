package com.drake.brv.sample.model

import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.ItemSimpleBinding

class SimpleBindingModel : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        // 不推荐这种方式, 因为Model只应该存在数据和逻辑, 如果包含UI绑定会导致视图耦合不例如项目迭代 (例如BRVAH)
        // val appName = holder.context.getString(R.string.app_name)

        // 使用不同的方法来获取视图控件
        // holder.findView<TextView>(R.id.tv_simple).text = appName // 使用findById
        // val dataBinding = holder.getBinding<ItemMultiTypeOneBinding>() // 使用DataBinding或ViewBinding

        // 获取数据对象
        // 如果存在多种数据类型, 请使用holder.getModelOrNull<Data>()或者if来判断itemViewType类型, 避免取值类型转换错误
        // val data = holder.getModel<Data>()

        when (holder.itemViewType) {
            R.layout.item_simple_text -> {
                val binding = holder.getBinding<ItemSimpleBinding>()
                val data = holder.getModel<SimpleModel>()
                binding.tv.text = data.name
            }
        }

    }
}