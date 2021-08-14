<img src="https://i.loli.net/2021/08/14/4ZgG9BmT8Qbv2jF.gif" width="250"/>


为数据模型实现接口`ItemSwipe`即可开启拖拽功能

```kotlin
data class SwipeModel(override var itemOrientationSwipe: Int = ItemOrientation.ALL) : ItemSwipe
```

> 注意如果你的数据模型被Gson反序列化后, 会删除所有的字段初始化值

这里我们可以重写访问函数来解决问题, 让该值固定返回

```kotlin hl_lines="3"
class SwipeModel() : ItemDrag {
    override var itemOrientationSwipe: Int = 0
        get() = ItemOrientation.ALL // 只会返回该值
}
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




## 自定义侧滑

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


通过给view打上tag标签 `swipe` 可以自定义侧滑将会移动的视图. 这样就可以展示背景后的视图
```xml
<RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="80dp"
        android:orientation="horizontal"
        android:tag="swipe"/>
```

## 侧滑按钮

很多人会问如何实现类似QQ那样的侧滑展示按钮. 这种推荐使用自定义Item的视图对象, 而不是让列表去实现.0

这里推荐第三方库: [SwipeToActionLayout](https://github.com/st235/SwipeToActionLayout)

<img src="https://github.com/st235/SwipeToActionLayout/raw/master/images/showcase.gif" width="50%"/>

> 这种交互效果属于iOS的官方效果, 不推荐Android抄袭
