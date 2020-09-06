[brv](../../../index.md) / [com.drake.brv](../../index.md) / [DefaultDecoration](../index.md) / [Edge](./index.md)

# Edge

`data class Edge`

列表条目是否靠近边缘的结算结果

### Parameters

`left` - 是否靠左

`right` - 是否靠左

`top` - 是否靠顶

`bottom` - 是否靠底

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | 列表条目是否靠近边缘的结算结果`Edge(left: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, top: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, right: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, bottom: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false)` |

### Properties

| Name | Summary |
|---|---|
| [bottom](bottom.md) | 是否靠底`var bottom: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [left](left.md) | 是否靠左`var left: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [right](right.md) | 是否靠左`var right: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [top](top.md) | 是否靠顶`var top: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [computeEdge](compute-edge.md) | 计算指定条目的边缘位置`fun computeEdge(position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, layoutManager: LayoutManager): Edge` |
