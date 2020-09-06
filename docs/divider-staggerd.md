瀑布流分割线

<img src="https://i.imgur.com/Xa5QKbA.png" width="40%"/>


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
| [startVisible](api/brv/com.drake.brv/-default-decoration/start-visible.md) | 是否显示上下边缘分割线 |
| [endVisible](api/brv/com.drake.brv/-default-decoration/end-visible.md) | 是否显示左右边缘分割线 |
| [includeVisible](api/brv/com.drake.brv/-default-decoration/include-visible.md) | 是否显示周围分割线 |