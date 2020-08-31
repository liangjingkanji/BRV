[brv](../../index.md) / [com.drake.brv.listener](../index.md) / [DefaultItemTouchCallback](./index.md)

# DefaultItemTouchCallback

`open class DefaultItemTouchCallback : Callback`

默认实现拖拽替换和侧滑删除

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | 默认实现拖拽替换和侧滑删除`DefaultItemTouchCallback(adapter: `[`BindingAdapter`](../../com.drake.brv/-binding-adapter/index.md)`)` |

### Properties

| Name | Summary |
|---|---|
| [adapter](adapter.md) | `var adapter: `[`BindingAdapter`](../../com.drake.brv/-binding-adapter/index.md) |

### Functions

| Name | Summary |
|---|---|
| [getMovementFlags](get-movement-flags.md) | `open fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getSwipeThreshold](get-swipe-threshold.md) | `open fun getSwipeThreshold(viewHolder: ViewHolder): `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) |
| [onChildDraw](on-child-draw.md) | `open fun onChildDraw(c: `[`Canvas`](https://developer.android.com/reference/android/graphics/Canvas.html)`, recyclerView: RecyclerView, viewHolder: ViewHolder, dX: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`, dY: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`, actionState: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, isCurrentlyActive: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDrag](on-drag.md) | 拖拽替换成功`open fun onDrag(source: BindingViewHolder, target: BindingViewHolder): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onMove](on-move.md) | `open fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onSwiped](on-swiped.md) | `open fun onSwiped(viewHolder: ViewHolder, direction: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
