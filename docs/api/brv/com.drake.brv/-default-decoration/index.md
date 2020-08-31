[brv](../../index.md) / [com.drake.brv](../index.md) / [DefaultDecoration](./index.md)

# DefaultDecoration

`class DefaultDecoration : ItemDecoration`

最强大的分割线工具

1. 分隔图片
2. 分隔颜色
3. 分隔间距
4. 回调函数判断间隔
5. 首尾是否显示分隔线, 可以展 示表格效果
6. 类型池来指定是否显示分割线
7. 支持全部的LayoutManager, 竖向/横向/网格分割线
8. 优于其他框架, 完美支持均布网格分隔物
9. 支持分组条目的分割线

### Types

| Name | Summary |
|---|---|
| [Edge](-edge/index.md) | 列表条目是否靠近边缘的结算结果`data class Edge` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | 最强大的分割线工具`DefaultDecoration(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [endVisible](end-visible.md) | 最后一个条目是否显示分割线, 当处于[DividerOrientation.GRID](../../com.drake.brv.annotaion/-divider-orientation/-g-r-i-d.md) 时垂直方向顶端和末端是否显示分割线`var endVisible: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [expandVisible](expand-visible.md) | 展开分组条目后该条目是否显示分割线`var expandVisible: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [orientation](orientation.md) | 分割线的方向, 仅支持[GridLayoutManager](#), 其他LayoutManager都是根据其方向自动适应`var orientation: `[`DividerOrientation`](../../com.drake.brv.annotaion/-divider-orientation/index.md) |
| [startVisible](start-visible.md) | 第一个条目之前是否显示分割线, 当处于[DividerOrientation.GRID](../../com.drake.brv.annotaion/-divider-orientation/-g-r-i-d.md) 时水平方向顶端和末端是否显示分割线`var startVisible: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [typePool](type-pool.md) | 集合内包含的类型才显示分割线`var typePool: `[`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)`<`[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`>?` |

### Functions

| Name | Summary |
|---|---|
| [addType](add-type.md) | 添加类型后只允许该类型的条目显示分割线 从未添加类型则默认为允许全部条目显示分割线`fun addType(vararg typeArray: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getItemOffsets](get-item-offsets.md) | `fun getItemOffsets(outRect: `[`Rect`](https://developer.android.com/reference/android/graphics/Rect.html)`, view: `[`View`](https://developer.android.com/reference/android/view/View.html)`, parent: RecyclerView, state: State): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onDraw](on-draw.md) | `fun onDraw(canvas: `[`Canvas`](https://developer.android.com/reference/android/graphics/Canvas.html)`, parent: RecyclerView, state: State): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onEnabled](on-enabled.md) | 根据回调函数来决定是否启用分割线`fun onEnabled(block: BindingViewHolder.() -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setBackground](set-background.md) | 分割线背景色 分割线有时候会存在间距(例如配置[setMargin](set-margin.md))或属于虚线, 这个时候暴露出来的是RecyclerView的背景色, 所以我们可以设置一个背景色来调整 可以设置背景色解决不统一的问题, 默认为透明[Color.TRANSPARENT](https://developer.android.com/reference/android/graphics/Color.html#TRANSPARENT)`fun setBackground(color: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun setBackground(colorString: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setBackgroundRes](set-background-res.md) | 分割线背景色 分割线有时候会存在间距(例如配置[setMargin](set-margin.md))或属于虚线, 这个时候暴露出来的是RecyclerView的背景色, 所以我们可以设置一个背景色来调整 可以设置背景色解决不统一的问题, 默认为透明[Color.TRANSPARENT](https://developer.android.com/reference/android/graphics/Color.html#TRANSPARENT)`fun setBackgroundRes(color: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setColor](set-color.md) | 设置分割线颜色, 如果不设置分割线宽度[setDivider](set-divider.md)则分割线宽度默认为1px 所谓分割线宽度指的是分割线的粗细, 而非水平宽度`fun setColor(color: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun setColor(color: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setColorRes](set-color-res.md) | 设置分割线颜色, 如果不设置分割线宽度[setDivider](set-divider.md)则分割线宽度默认为1px 所谓分割线宽度指的是分割线的粗细, 而非水平宽度`fun setColorRes(color: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setDivider](set-divider.md) | 设置分割线宽度 如果使用[setDrawable](set-drawable.md)则无效`fun setDivider(width: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 1, dp: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setDrawable](set-drawable.md) | 将图片作为分割线, 图片宽高即分割线宽高`fun setDrawable(drawable: `[`Drawable`](https://developer.android.com/reference/android/graphics/drawable/Drawable.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>`fun setDrawable(drawableRes: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setMargin](set-margin.md) | 设置分隔左右或上下间距, 依据分割线为垂直或者水平决定具体方向间距`fun setMargin(start: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, end: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)` = 0, dp: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [computeEdge](compute-edge.md) | 计算指定条目的边缘位置`fun computeEdge(position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, layoutManager: LayoutManager): Edge` |
