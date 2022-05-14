假设你不想使用DataBinding只想用ViewBinding可以阅读以下内容

## 前言

我推荐使用DataBinding而非ViewBinding, 理由如下

1. 首先MVVM是目前最优秀的架构设计, 而DataBinding是实现MVVM的最优解
2. DataBinding本身包含ViewBinding, 且不会默认为所有布局自动生成类代码量更少(仅layout标签包裹的布局)
3. 其次ViewBinding无法被封装, 需要额外的代码处理


## 使用

如果你依然想用ViewBinding可以参考以下代码

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    // 在onCreateViewHolder生命周期使用
    onCreate {
        val binding = ItemSimpleBinding.bind(itemView)
        binding.tvName.text = "文本内容"
    }

    // 或者在onBindViewHolder生命周期中使用
    onBind {
        val binding = ItemSimpleBinding.bind(itemView)
    }
}.models = getData()
```

或者在实现[ItemBind](/#2)的数据模型中使用

```kotlin
class SimpleModel(var name: String = "BRV") : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val binding = ItemSimpleBinding.bind(holder.itemView)
        binding.tvName.text = "文本内容"
    }
}
```

如果是多类型注意先判断类型, 避免为绑定错误的ViewBinding

```kotlin
class SimpleModel(var name: String = "BRV") : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        when(holder.itemViewType) {
            R.layout.item_simple -> {
                val binding = ItemSimpleBinding.bind(holder.itemView)
                binding.tvName.text = "文本内容"
            }
            R.layout.item_simple_2 -> {
                val binding = ItemSimpleBinding2.bind(holder.itemView)
                binding.tvName.text = "类型2-文本内容"
            }
        }
    }
}
```