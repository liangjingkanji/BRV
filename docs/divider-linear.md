> 如果直接绘制item的xml可以实现分割线, 比如你在xml中使用简单的`layout_margin`(设置间距)也能完成你想要的分割线效果, 那么我更建议使用layout_margin

## 水平分割线

<img src="https://i.loli.net/2021/08/14/IoBfnz6ERXVHlq3.png" width="250" />

创建一个`drawable`文件来描述分隔线, 其具备复用的特点

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/dividerDecoration" />
    <size android:height="5dp" />
</shape>
```

创建列表

```kotlin
rv.linear().divider(R.drawable.divider_horizontal).setup {
    addType<DividerModel>(R.layout.item_divider)
}.models = getData()
```

<br>

## 垂直分割线

<img src="https://i.loli.net/2021/08/14/rAeDXkfV6HxJUym.png" width="250"/>

创建Drawable作为分隔线
```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/dividerDecoration" />
    <size android:width="5dp" />
</shape>
```

创建列表
```kotlin
rv.linear(RecyclerView.HORIZONTAL).divider(R.drawable.divider_vertical).setup {
    addType<DividerModel>(R.layout.item_divider_vertical)
}.models = getData()
```


- 这里使用`Drawable`资源来快速设置分割线, Drawable的宽高就是分割线的宽高
- 如果水平分割线, 则Drawable的宽度值无效(实际宽度值为RecyclerView的宽)
- 如果垂直分割线, 则Drawable的高度值无效(实际分割线高度为RecyclerView高度)


## 边缘分割线

| 字段 | 描述 |
|-|-|
| [startVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#-2091559976%2FProperties%2F-900954490) | 是否显示首部分割线 |
| [endVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#-377591023%2FProperties%2F-900954490) | 是否显示尾部分割线 |
| [includeVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#1716094302%2FProperties%2F-900954490) | 是否显示首尾分割线 |

<img src="https://i.loli.net/2021/08/14/iL5epWdOQKnwZAc.png" width="250"/>

通过两个字段可以控制首尾是否显示分割线

```kotlin hl_lines="3 4"
rv.linear().divider {
    setDrawable(R.drawable.divider_horizontal)
    startVisible = true
    endVisible = true
}.setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```

## 四周全包裹

<img src="https://i.loli.net/2021/08/14/lGSOPdg5A8WInoL.png" width="250"/>

这种分割线属于网格分割线, 要求使用`DividerOrientation.GRID`, 但LinearLayoutManager并不支持

这里有两种解决办法

1. 使用spanCount为1的GridLayoutManager等效
1. 在rv两侧单独使用`View`绘制两条分割线

推荐第一种办法, 示例代码如下:

```kotlin
rv.grid().divider{
    setDrawable(R.drawable.divider_horizontal)
    orientation = DividerOrientation.GRID
    includeVisible = true
}.setup {
    addType<DividerModel>(R.layout.item_divider_vertical)
}.models = getData()
```
