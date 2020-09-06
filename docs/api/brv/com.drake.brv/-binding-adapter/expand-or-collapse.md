[brv](../../index.md) / [com.drake.brv](../index.md) / [BindingAdapter](index.md) / [expandOrCollapse](./expand-or-collapse.md)

# expandOrCollapse

`fun expandOrCollapse(position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, scrollTop: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, depth: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)

展开或折叠

### Parameters

`scrollTop` - 展开同时当前条目滑动到顶部

`depth` - 递归展开子项的深度, 如等于-1则代表展开所有子项, 0表示仅展开当前

**Return**
展开或折叠后变动的条目数量

