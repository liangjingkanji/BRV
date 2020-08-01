## 拖拽/侧滑

![swipe_drag](https://i.imgur.com/uxOV3PE.gif)

只支持拖拽移动和侧滑删除



步骤:

1. 开启`ItemTouchHelper`支持
2. 数据模型继承`ItemModel`
3. 自定义扩展



BindingAdapter提供一个字段来开启ItemTouchHelper支持

```kotlin
var touchEnable = false // 默认关闭
```



然后数据模型要求继承`ItemModel`, 根据需求重写函数.

示例:

```kotlin
data class Model(val name: String) : ItemModel() {

    override fun getDrag(): Int {
        return UP or DOWN
    }

    override fun getSwipe(): Int {
        return RIGHT or LEFT
    }
    
}
```

`RIGHT or LEFT`是控制拖拽和侧滑的方向(侧滑不支持UP/DOWN)的常量.

拖拽移动item会自动改变数据模型在数据集合中的位置.



**扩展功能**

如果想要扩展ItemTouchHelper可以给BindingAdapter的变量`itemTouchHelper`赋值

```kotlin
rv.linear().setup {

  addType<Model>(R.layout.item)
  touchEnable = true
  
  itemTouchHelper = ItemTouchHelper(object : DefaultItemTouchCallback(this) {
			// 这里可以重写函数
  })

}.models = data
```



通过给view打上tag标签 `swipe` 可以控制侧滑将会移动的视图.

```xml
<RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="80dp"
        android:orientation="horizontal"
        android:tag="swipe"/>
```

