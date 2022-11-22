假设不想使用DataBinding只想用ViewBinding可以阅读以下内容

> 其实使用ViewBinding和DataBinding的查找视图对象用法一致, 都是调用`getBinding()`

## 前言

更推荐使用DataBinding而非ViewBinding, 理由如下

1. 首先MVVM双向数据绑定是目前最优秀的架构设计, 而DataBinding是实现MVVM的最优解
2. ViewBinding只是DataBinding中的一个小功能, 且DataBinding不会默认为所有布局自动生成类故代码量更少


## 使用

如果你依然想用ViewBinding可以参考以下代码

### 1) 在onBind中使用

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    // 在onCreateViewHolder生命周期使用
    onCreate {
        getBinding<ItemSimpleBinding>().tvName.text = "文本内容"
    }

    // 或者在onBindViewHolder生命周期中使用
    onBind {
        val binding = getBinding<ItemSimpleBinding>()
    }
}.models = getData()
```

### 2) 在ItemBind中使用

如果使用实现接口[ItemBind](/#2)的数据模型

```kotlin
class SimpleModel(var name: String = "BRV") : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding = getBinding<ItemSimpleBinding>()
        binding.tvName.text = "文本内容"
    }
}
```

都是调用方法`getBinding()`

## 多类型

如果是多类型注意先判断类型, 避免为绑定错误的ViewBinding

```kotlin
class SimpleModel(var name: String = "BRV") : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {

        // 方式1 判断itemViewType
        when(holder.itemViewType) {
            R.layout.item_simple -> {
                getBinding<ItemSimpleBinding>().tvName.text = "文本内容"
            }
            R.layout.item_simple_2 -> {
                getBinding<ItemSimpleBinding2>().tvName.text = "类型2-文本内容"
            }
        }


        // 方式2 判断ViewBinding
        when (val viewBinding = getBinding<ViewBinding>()) {
            is ItemSimpleBinding -> {
                viewBinding.tvName.text = "文本内容"
            }
            is ItemComplexBinding -> {
                viewBinding.tvName.text = "类型2-文本内容"
            }
        }
    }
}
```