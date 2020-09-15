[brv](../../index.md) / [com.drake.brv](../index.md) / [PageRefreshLayout](index.md) / [addData](./add-data.md)

# addData

`fun addData(data: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?>?, adapter: `[`BindingAdapter`](../-binding-adapter/index.md)`? = null, isEmpty: () -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = { data.isNullOrEmpty() }, hasMore: `[`BindingAdapter`](../-binding-adapter/index.md)`.() -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = { true }): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

自动分页自动加载数据, 自动判断当前属于下拉刷新还是上拉加载更多

此函数每次调用会导致[index](--index--.md)递增或者下拉刷新会导致[index](--index--.md)等于[startIndex](start-index.md)

### Parameters

`data` - 数据集

`adapter` - 假设PageRefreshLayout不能直接包裹RecyclerView, 然后也想使用自动分页, 请指定此参数, 因为自动分页需要[BindingAdapter](../-binding-adapter/index.md)实例

`hasMore` - 在函数参数中返回布尔类型来判断是否还存在下一页数据, 默认值true表示始终存在

`isEmpty` - 返回true表示数据为空, 将显示缺省页 -&gt; 空布局, 默认以[data.isNullOrEmpty](#)则为空