## 水平分割线

<p align="center"><img src="https://i.imgur.com/tZUyUIZ.png" width="40%" /></p>

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

<p align="center"><img src="https://i.imgur.com/6AvAiAJ.png" width="40%"/></p>

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

