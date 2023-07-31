RV的数据是集合`List<Any?>?`, 其中的集合元素就是Model(数据模型)

通过让Model实现不同接口可以获取不同的功能

| 接口 | 描述 |
|-|-|
| ItemBind | 监听`onBindViewHolder`来[绑定数据](index.md#_3) |
| ItemAttached | 监听视图附着到窗口(Window) |
| ItemExpand | Item可[分组](group.md) |
| ItemDrag | Item可[拖拽](drag.md) |
| ItemSwipe | Item可[侧滑](swipe.md) |
| ItemHover | Item可[悬停](hover.md) |
| ItemPosition | Item索引位置 |

如何使用请阅读对应功能章节或者注释

```kotlin
data class SimpleModel(var name: String = "BRV") : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        // 在Model内可以为视图绑定数据
    }
}
```