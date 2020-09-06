[brv](../../index.md) / [com.drake.brv](../index.md) / [PageRefreshLayout](index.md) / [addData](./add-data.md)

# addData

`fun addData(data: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?>?, isEmpty: () -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = { data.isNullOrEmpty() }, hasMore: `[`BindingAdapter`](../-binding-adapter/index.md)`.() -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = { true }): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

直接接受数据, 自动判断当前属于下拉刷新还是上拉加载更多

### Parameters

`data` - 数据集

`hasMore` - 在函数参数中返回布尔类型来判断是否还存在下一页数据, 默认值true表示始终存在

`isEmpty` - 返回true表示数据为空, 将显示缺省页 -&gt; 空布局, 默认以[data.isNullOrEmpty](#)则为空