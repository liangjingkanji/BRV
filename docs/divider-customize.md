1. 扩展函数`divider`为简化创建`DefaultDecoration`
2. 如`DefaultDecoration`不满足需求可以继承`RecyclerView.ItemDecoration`实现

## 组合间距

可重复调用`.divider`来叠加分隔线/间距

```kotlin
binding.rv.grid(3).divider { // 水平间距
    orientation = DividerOrientation.HORIZONTAL
    setDivider(10, true)
}.divider { // 垂直间距
    orientation = DividerOrientation.VERTICAL
    setDivider(20, true)
    onEnabled { // 是否为当前Item启用分隔线
        itemViewType == R.layout.item_divider_vertical
    }
}.setup {
    addType<DividerModel>(R.layout.item_divider_vertical)
}.models = getData()
```

## 方法

| 函数 | 描述 |
|-|-|
| onEnabled | 根据回调返回值是否绘制分隔线 |
| addType | 添加绘制分隔线的条目类型, 原理为使用onEnabled判断 |
| setDivider | 条目间隔 |
| setColor | 分隔线颜色 |
| setDrawable | 分隔线图片, 和`setDivider`选一 |
| setMargin | 分隔物间距 |

## 计算边缘

`Edge.computeEdge()`可以计算Item是否位于列表的边缘

```kotlin
data class Edge(
    var left: Boolean = false,
    var top: Boolean = false,
    var right: Boolean = false,
    var bottom: Boolean = false
)
```

`left`为true表示指定position位于列表左侧, `top`为true表示位于列表顶部, 类推