<figure markdown>
  ![](https://i.loli.net/2021/08/14/9EUvCSnONYixWDT.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef2/sample/src/main/java/com/drake/brv/sample/ui/fragment/DragFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

Model实现接口`ItemDrag`即可开启拖拽功能

```kotlin
data class DragModel(override var itemOrientationDrag: Int = ItemOrientation.ALL) : ItemDrag
```

## 拖拽方向

该类包含拖拽可配置的方向

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

## 自定义

`DefaultItemTouchCallback`是BRV内部的触摸事件处理, 可以复写或直接实现`ItemTouchHelper.Callback`

```kotlin
rv.linear().setup {
  addType<Model>(R.layout.item)

  itemTouchHelper = ItemTouchHelper(object : DefaultItemTouchCallback(this) {

    /**
     * 当拖拽动作完成且松开手指时触发
     */
    open fun onDrag(
        source: BindingAdapter.BindingViewHolder,
        target: BindingAdapter.BindingViewHolder
    ) {
        // 这是拖拽交换后回调, 这里可以同步服务器
    }

  })

}.models = data
```

## 点击拖拽

因为滑动列表和拖拽排序都是移动手势可能存在手势冲突, 所以建议点击列表某个区域或按钮开始拖拽

```kotlin
rv.linear().setup {
    addType<DragModel>(R.layout.item_drag)
    onCreate {
        findView<View>(R.id.btnDrag).setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) { // 如果手指按下则开始拖拽
                itemTouchHelper?.startDrag(this)
            }
            true
        }
    }
}.models = getData()
```
