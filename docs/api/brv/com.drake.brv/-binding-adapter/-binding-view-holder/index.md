[brv](../../../index.md) / [com.drake.brv](../../index.md) / [BindingAdapter](../index.md) / [BindingViewHolder](./index.md)

# BindingViewHolder

`inner class BindingViewHolder : ViewHolder`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BindingViewHolder(itemView: `[`View`](https://developer.android.com/reference/android/view/View.html)`)`<br>`BindingViewHolder(viewDataBinding: ViewDataBinding)` |

### Properties

| Name | Summary |
|---|---|
| [_data](_data.md) | `lateinit var _data: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html) |
| [adapter](adapter.md) | `val adapter: `[`BindingAdapter`](../index.md) |
| [context](context.md) | `var context: `[`Context`](https://developer.android.com/reference/android/content/Context.html) |
| [modelPosition](model-position.md) | `val modelPosition: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Functions

| Name | Summary |
|---|---|
| [collapse](collapse.md) | 折叠子项`fun collapse(depth: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [expand](expand.md) | 展开子项`fun expand(scrollTop: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true, depth: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [expandOrCollapse](expand-or-collapse.md) | 展开或折叠子项`fun expandOrCollapse(scrollTop: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false, depth: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [findParentPosition](find-parent-position.md) | 查找分组中的父项位置`fun findParentPosition(): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [findParentViewHolder](find-parent-view-holder.md) | 查找分组中的父项ViewHolder`fun findParentViewHolder(): BindingViewHolder?` |
| [findView](find-view.md) | 查找ItemView上的视图`fun <V : `[`View`](https://developer.android.com/reference/android/view/View.html)`> findView(id: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): V` |
| [getBinding](get-binding.md) | 返回匹配泛型的数据绑定对象ViewDataBinding`fun <B : ViewDataBinding> getBinding(): B` |
| [getModel](get-model.md) | 返回数据模型`fun <M> getModel(): M` |
| [getModelOrNull](get-model-or-null.md) | 返回数据模型, 如果不匹配泛型则返回Null`fun <M> getModelOrNull(): M?` |
