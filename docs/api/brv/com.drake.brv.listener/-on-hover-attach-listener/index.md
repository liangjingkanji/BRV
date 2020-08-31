[brv](../../index.md) / [com.drake.brv.listener](../index.md) / [OnHoverAttachListener](./index.md)

# OnHoverAttachListener

`interface OnHoverAttachListener`

由RecyclerView.Adapter实现该接口

### Functions

| Name | Summary |
|---|---|
| [attachHover](attach-hover.md) | 当条目附着时 [detachHover](detach-hover.md) 该函数可以进行还原`abstract fun attachHover(v: `[`View`](https://developer.android.com/reference/android/view/View.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [detachHover](detach-hover.md) | 条目分离时 一般用于还原[attachHover](attach-hover.md)函数`abstract fun detachHover(v: `[`View`](https://developer.android.com/reference/android/view/View.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
