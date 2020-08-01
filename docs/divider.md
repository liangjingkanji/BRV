## 分割线

<img src="https://i.imgur.com/sJbcnnf.png" alt="image-20200801180841347" width="30%" />

垂直列表的分割线

```kotlin
rv_grid.linear().divider(R.drawable.divider_horizontal).setup {
	addType<StaggeredModel>(R.layout.item_staggered)
}.models = getData()
```



[R.drawable.divider_horizontal]

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/colorAccent" />
    <size
        android:width="30px"
        android:height="10px" />
</shape>
```





### 配置间距



```kotlin
rv_grid.divider {
    orientation = Orientation.VERTICAL // 分割线方向
    setColorRes(R.color.dividerDecoration) // 分割线颜色
    setDivider(30, true) // 分隔线为30dp
    startVisible = true // 列表头部是否显示分割线
    endVisible = true // 列表尾部是否显示分割线
}.setup {
    addType<StaggeredModel>(R.layout.item_staggered)
}.models = getData()
```





### 函数



[RecyclerUtils](https://github.com/liangjingkanji/BRV/blob/master/brv/src/main/java/com/drake/brv/utils/RecyclerUtils.kt)工具提供扩展函数快速创建常用的分割线

```kotlin
fun RecyclerView.dividerColorRes(
    @ColorRes color: Int, // 分割线颜色
    marginStart: Int = 0, // 左边间距
    marginEnd: Int = 0, // 右边间距
    width: Int = 1 // 分割线的宽度
): RecyclerView
// 通过颜色资源设置水平分割线

fun RecyclerView.dividerColor(
    color: String, // 16进制颜色字符串
    marginStart: Int = 0,
    marginEnd: Int = 0,
    width: Int = 1
): RecyclerView
// 同上

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



扩展函数都是创建[DefaultDecoration](https://github.com/liangjingkanji/BRV/blob/master/brv/src/main/java/com/drake/brv/DefaultDecoration.kt)对象来进行配置

查看源码可见

```kotlin
fun RecyclerView.divider(
    @DrawableRes drawable: Int
): RecyclerView {
    return divider {
        setDrawable(drawable)
    }
}
```

