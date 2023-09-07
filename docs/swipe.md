<figure markdown>
  ![](https://i.loli.net/2021/08/14/4ZgG9BmT8Qbv2jF.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef245e7f312a0077194611f1c2aded647a3c/sample/src/main/java/com/drake/brv/sample/ui/fragment/SwipeFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>


Model实现接口`ItemSwipe`即可开启拖拽功能

```kotlin
data class SwipeModel(override var itemOrientationSwipe: Int = ItemOrientation.ALL) : ItemSwipe
```

## 侧滑方向

该类包含侧滑可配置的方向

|  ItemOrientation  |    描述  |
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

通过赋值`itemTouchHelper`实现自己的手势

```kotlin
rv.linear().setup {
  addType<SwipeModel>(R.layout.item)

  itemTouchHelper = ItemTouchHelper(object : DefaultItemTouchCallback(this) {
	// 这里可以重写函数
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        super.onSwiped(viewHolder, direction)
        // 这是侧滑删除后回调, 这里可以同步服务器

        Log.d("位置", "layoutPosition = ${viewHolder.layoutPosition}")
        Log.d("数据", "SwipeModel = ${(viewHolder as BindingAdapter.BindingViewHolder).getModel<SwipeModel>()}")
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

请自定义View实现, 或使用以下三方库

<figure markdown>
  ![](https://raw.githubusercontent.com/liangjingkanji/BRV/master/docs/img/md/swipe-menu.gif){ width="250" }
  <a href="https://github.com/mcxtzhang/SwipeDelMenuLayout/blob/master/README-cn.md" target="_blank"><figcaption>SwipeDelMenuLayout</figcaption></a>
</figure>

侧滑按钮交互属于iOS风格, Android存在全屏手势冲突, 并不推荐



