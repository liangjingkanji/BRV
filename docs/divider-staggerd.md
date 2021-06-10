瀑布流分割线

> 由于网格分割线是动态计算的, 所以在RecyclerView中假设你添加新的数据使用局部刷新函数(notifyItem*等函数)以后可能需要使用`rv.invalidateItemDecorations()`刷新分隔物, 否则可能会分割线宽高错乱

<img src="https://i.imgur.com/Xa5QKbA.png" width="250"/>


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


## 边缘分割线

通过两个字段可以控制边缘分割线是否显示

| 字段 | 描述 |
|-|-|
| [startVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#-2091559976%2FProperties%2F-900954490) | 是否显示上下边缘分割线 |
| [endVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#-377591023%2FProperties%2F-900954490) | 是否显示左右边缘分割线 |
| [includeVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#1716094302%2FProperties%2F-900954490) | 是否显示周围分割线 |