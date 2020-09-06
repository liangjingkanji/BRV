[brv](../../index.md) / [com.drake.brv.utils](../index.md) / [androidx.recyclerview.widget.RecyclerView](index.md) / [divider](./divider.md)

# divider

`fun RecyclerView.divider(block: `[`DefaultDecoration`](../../com.drake.brv/-default-decoration/index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): RecyclerView`

函数配置分割线
具体配置参数查看[DefaultDecoration](../../com.drake.brv/-default-decoration/index.md)

`fun RecyclerView.divider(@DrawableRes drawable: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, orientation: `[`DividerOrientation`](../../com.drake.brv.annotaion/-divider-orientation/index.md)` = DividerOrientation.HORIZONTAL): RecyclerView`

指定Drawable资源为分割线, 分割线的间距和宽度应在资源文件中配置

### Parameters

`drawable` - 描述分割线的drawable

`orientation` - 分割线方向, 仅[androidx.recyclerview.widget.GridLayoutManager](#)需要使用此参数, 其他LayoutManager都是根据其方向自动推断