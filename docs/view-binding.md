假设你不想使用DataBinding只想用ViewBinding可以阅读以下内容

## 不推荐

1. 首先MVVM是目前最优秀的架构设计, 而DataBinding是实现MVVM的最优解
2. DataBinding本身可以看做ViewBinding, 且不会默认为所有布局自动生成类代码量更少
3. 其次ViewBinding无法被封装, 代码量更多


## 使用

如果你依然想用ViewBinding可以参考以下代码

```kotlin
rv.linear().setup {

    // 在onCreateViewHolder生命周期使用
    onCreate {
        val binding = ItemSimpleBinding.bind(itemView)
        binding.tvName.text = "文本内容"
    }

    // 或者在onBindViewHolder生命周期中使用
    onBind {
        val binding = ItemSimpleBinding.bind(itemView)
    }

    addType<SimpleModel>(R.layout.item_simple)
}.models = getData()
```