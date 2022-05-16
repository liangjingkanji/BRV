rv的数据是集合`List<Any?>?`, 其中的集合元素就是数据模型

通过让数据模型实现不同的接口可以获取不同的功能或者回调

| 接口 | 描述 |
|-|-|
| ItemBind | 在数据模型中回调onBindViewHolder生命周期, 用于[绑定数据](index.md) |
| ItemAttached | 监听视图附着到窗口(Window) |
| ItemExpand | Item可[分组](group.md) |
| ItemDrag | Item可[拖拽](drag.md) |
| ItemSwipe | Item可[侧滑](swipe.md) |
| ItemHover | Item可[悬停](hover.md) |
| ItemPosition | Item索引位置 |

具体如何使用请查看对应功能章节或者注释

```kotlin
data class SimpleModel(var name: String = "BRV") : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        // 使用不同的方法来获取视图控件
        // holder.findView<TextView>(R.id.tv_simple).text = appName // 使用findById
        // val viewBinding = ItemSimpleBinding.bind(holder.itemView) // 使用ViewBinding
        // val dataBinding = holder.getBinding<ItemMultiTypeOneBinding>() // 使用DataBinding
    }
}
```