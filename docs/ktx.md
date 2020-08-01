### LayoutManager

框架还提供快速创建布局管理器的扩展函数, 上面使用示例

```kotlin
rv.linear().setup {
	
}
```

函数

```kotlin
fun RecyclerView.linear(
    @RecyclerView.Orientation orientation: Int = VERTICAL,
    reverseLayout: Boolean = false
)


fun RecyclerView.grid(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = VERTICAL,
    reverseLayout: Boolean = false
)

fun RecyclerView.staggered(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = VERTICAL
)
```



### 分隔物

框架提供快速设置分隔物扩展函数

```kotlin
fun RecyclerView.divider( 
    @DrawableRes drawable: Int,  // 分隔物Drawable
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL, // LayoutManager的方向
    block: ((Rect, View, RecyclerView, RecyclerView.State) -> Boolean)? = null // getItemOffset回调用于设置间隔
)
```



示例

```kotlin
rv.linear().divider(R.drawable.divider_horizontal_padding_15dp).setup {
	
}
```



### 对话框

通过扩展函数快速给对话框创建列表

```
Dialog(context).setAdapter(bindingAdapter)
```

