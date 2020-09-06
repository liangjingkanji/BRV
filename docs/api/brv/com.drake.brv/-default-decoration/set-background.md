[brv](../../index.md) / [com.drake.brv](../index.md) / [DefaultDecoration](index.md) / [setBackground](./set-background.md)

# setBackground

`fun setBackground(color: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

分割线背景色
分割线有时候会存在间距(例如配置[setMargin](set-margin.md))或属于虚线, 这个时候暴露出来的是RecyclerView的背景色, 所以我们可以设置一个背景色来调整
可以设置背景色解决不统一的问题, 默认为透明[Color.TRANSPARENT](https://developer.android.com/reference/android/graphics/Color.html#TRANSPARENT)

`fun setBackground(colorString: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

分割线背景色
分割线有时候会存在间距(例如配置[setMargin](set-margin.md))或属于虚线, 这个时候暴露出来的是RecyclerView的背景色, 所以我们可以设置一个背景色来调整
可以设置背景色解决不统一的问题, 默认为透明[Color.TRANSPARENT](https://developer.android.com/reference/android/graphics/Color.html#TRANSPARENT)

### Parameters

`colorString` - 颜色的16进制字符串