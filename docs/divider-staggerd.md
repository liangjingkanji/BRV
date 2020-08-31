## 垂直列表分割线
<p align="center"><img src="https://i.imgur.com/tZUyUIZ.png" width="50%" /></p>

=== "分割线"
    ```kotlin
    rv_grid.linear().divider(R.drawable.divider_horizontal).setup {
        addType<DividerModel>(R.layout.item_divider)
    }.models = getData()
    ```

=== "divider_horizontal"
    ```xml
    <shape xmlns:android="http://schemas.android.com/apk/res/android">
        <solid android:color="@color/dividerDecoration" />
        <size
            android:width="100px"
            android:height="10px" />
    </shape>
    ```

1. 这里使用`Drawable`资源来快速设置分割线, Drawable的宽高就是分割线的宽高
1. 如果水平分割线, 则Drawable的宽度值无效(实际宽度值为RecyclerView的宽)
1. 如果垂直分割线, 则Drawable的高度值无效(实际分割线高度为RecyclerView高度)


## 水平列表分割线
<p align="center"><img src="https://i.imgur.com/6AvAiAJ.png" width="50%"/></p>

=== "分割线"
    ```kotlin
    rv.linear(orientation = RecyclerView.HORIZONTAL).divider(R.drawable.divider_vertical).setup {
        addType<DividerModel>(R.layout.item_divider)
    }.models = getData()
    ```

=== "divider_vertical"
    ```xml
    <shape xmlns:android="http://schemas.android.com/apk/res/android">
        <solid android:color="@color/dividerDecoration" />
        <size android:width="5px" />
    </shape>
    ```


## 网格列表分割线

这里演示复杂的自定义分割线

```kotlin
rv_grid.divider {
    orientation = Orientation.GRID // 分割线方向
    setColorRes(R.color.dividerDecoration) // 分割线颜色
    setDivider(30, true) // 分隔线为30dp
    startVisible = true // 列表头部是否显示分割线
    endVisible = true // 列表尾部是否显示分割线

    onEnabled { // 根据布尔类型返回值决定是否显示分割线
        when (itemViewType) {
            R.layout.item_divider -> true
            else -> false
        }
    }
}.setup {
    addType<DividerModel>(R.layout.item_staggered)
}.models = getData()
```



## 瀑布流列表分割线


[RecyclerUtils](https://github.com/liangjingkanji/BRV/blob/master/brv/src/main/java/com/drake/brv/utils/RecyclerUtils.kt)工具提供扩展函数快速创建常用的分割线

```kotlin
fun RecyclerView.dividerColorRes(
    @ColorRes color: Int, // 分割线颜色
    marginStart: Int = 0, // 左边间距, 如果是垂直分割线则为上间距
    marginEnd: Int = 0, // 右边间距, 如果是垂直分割线则为下间距
    width: Int = 1 // 分割线的宽度
): RecyclerView
// 通过颜色资源设置水平分割线

fun RecyclerView.dividerColor(
    @ColorInt color: Int, // 颜色常量值, 例如Color.RED
    marginStart: Int = 0,
    marginEnd: Int = 0,
    width: Int = 1
): RecyclerView
// 同上

fun RecyclerView.divider(
    @DrawableRes drawable: Int // Drawable资源
): RecyclerView
// 通过Drawable资源来设置分割线, Drawable的宽高决定分割线的宽高

fun RecyclerView.divider(
    block: DefaultDecoration.() -> Unit
): RecyclerView
// 在回调函数中配置分割线
```


## DefaultDecoration

扩展函数都是创建[DefaultDecoration](https://github.com/liangjingkanji/BRV/blob/master/brv/src/main/java/com/drake/brv/DefaultDecoration.kt)对象来进行配置

查看`divider`函数的源码可见很简单

```kotlin
fun RecyclerView.divider(
    @DrawableRes drawable: Int
): RecyclerView {
    return divider {
        setDrawable(drawable)
    }
}
```

```kotlin
fun onEnabled(enabled: BindingAdapter.BindingViewHolder.() -> Boolean)

fun addType(@LayoutRes vararg typeArray: Int)
```

