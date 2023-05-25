package com.drake.brv.sample.model

import com.drake.brv.BindingAdapter
import com.drake.brv.item.ItemBind

class SimpleBindingModel : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        // 不推荐这种方式, 因为Model只应该存在数据和逻辑, 如果包含UI绑定会导致视图耦合不例如项目迭代 (例如BRVAH)
        // val appName = holder.context.getString(R.string.app_name)

        // 使用不同的方法来获取视图控件
        // holder.findView<TextView>(R.id.tv_simple).text = appName // 使用findById
        // val dataBinding = holder.getBinding<ItemMultiTypeOneBinding>() // 使用DataBinding或ViewBinding
    }
}