## 水平分割线

<img src="https://i.imgur.com/tZUyUIZ.png" width="250" />

创建一个`drawable`文件来描述分隔线, 其具备复用的特点

```xml
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/dividerDecoration" />
    <size android:height="5dp" />
</shape>
```

创建列表

```kotlin
rv_grid.linear().divider(R.drawable.divider_horizontal).setup {
    addType<DividerModel>(R.layout.item_divider)
}.models = getData()
```

<br>

## 垂直分割线

<img src="https://i.imgur.com/6AvAiAJ.png" width="250"/>

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

<img src="https://i.imgur.com/Z1vsVjW.png" width="250"/>

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

