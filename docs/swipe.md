<img src="https://i.imgur.com/bkqLY6b.gif" width="40%"/>


为数据模型实现接口`ItemSwipe`即可开启拖拽功能

```kotlin
data class SwipeModel(override var itemOrientationSwipe: Int = ItemOrientation.ALL) : ItemSwipe
```

## ItemOrientation

该类包含拖拽可配置的方向

|  字段  |    描述  |
| ---- | ---- |
|   `ALL`   |   全部方向   |
|   `VERTICAL`   |   垂直方向   |
|   `HORIZONTAL`   |   水平方向   |
|   `LEFT`   |   向左   |
|   `RIGHT`   |   向右   |
|   `UP`   |   向上   |
|   `DOWN`   |   向下   |
|   `NONE`   |   禁用   |




## 自定义

如果想要扩展ItemTouchHelper可以给BindingAdapter的变量`itemTouchHelper`赋值

```kotlin
rv.linear().setup {
  addType<Model>(R.layout.item)

  itemTouchHelper = ItemTouchHelper(object : DefaultItemTouchCallback(this) {
	// 这里可以重写函数
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        super.onSwiped(viewHolder, direction)
        // 这是侧滑删除后回调, 这里可以同步服务器
    }
  })

}.models = data
```


通过给view打上tag标签 `swipe` 可以自定义侧滑将会移动的视图.
```xml
<RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="80dp"
        android:orientation="horizontal"
        android:tag="swipe"/>
```

