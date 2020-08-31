[brv](../../index.md) / [com.drake.brv](../index.md) / [BindingAdapter](index.md) / [addType](./add-type.md)

# addType

`fun <reified M> addType(@LayoutRes layout: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

添加多类型

`inline fun <reified M> addType(noinline block: M.(`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`) -> `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

通过回调函数添加多类型, 一对多多类型

