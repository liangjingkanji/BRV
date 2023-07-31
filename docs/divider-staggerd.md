## 网格分隔线

<img src="https://i.loli.net/2021/08/14/gx8mLuCNOFzWfIj.png" width="250"/>


```kotlin
rv.staggered(3).divider(R.drawable.divider_horizontal).setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)

    onBind {
        // 模拟动态高度
        val layoutParams = itemView.layoutParams
        layoutParams.height = getModel<DividerModel>().height
        itemView.layoutParams = layoutParams
    }
}.models = getData()
```


## 边缘分隔线

通过两个字段可以控制边缘分隔线是否显示

| 字段 | 描述 |
|-|-|
| [startVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#-2091559976%2FProperties%2F-900954490) | 是否显示上下边缘分隔线 |
| [endVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#-377591023%2FProperties%2F-900954490) | 是否显示左右边缘分隔线 |
| [includeVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#1716094302%2FProperties%2F-900954490) | 是否显示周围分隔线 |