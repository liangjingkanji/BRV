<p align="center"><img src="https://i.loli.net/2021/08/14/4wUngbV2qZFAf5H.gif" width="250"/></p>

通过实现接口`ItemHover`

```kotlin
class HoverHeaderModel : ItemHover {
    // 返回值决定是否悬停
    override var itemHover: Boolean = true
}
```

然后这个Item就会悬停在顶部了



完整示例

```kotlin
override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    rv.linear().setup {
        addType<Model>(R.layout.item_multi_type_simple)
        addType<HoverHeaderModel>(R.layout.item_hover_header)
        models = getData()

        // 点击事件
        onClick(R.id.item) {
            when (itemViewType) {
                R.layout.item_hover_header -> toast("悬停条目")
                else -> toast("普通条目")
            }
        }

        // 可选项, 粘性监听器
        onHoverAttachListener = object : OnHoverAttachListener {
            // 黏住顶部的时候, v表示指定悬停的itemView对象
            override fun attachHover(v: View) {
                ViewCompat.setElevation(v, 10F)
            }

            // 从顶部分离的时候
            override fun detachHover(v: View) {
                ViewCompat.setElevation(v, 0F)
            }
        }

    }
}
```

> 不同于大部分悬停框架, BRV无需特殊处理支持全部的点击事件

[BindingAdapter] 判断当前位置是否属于悬停

```kotlin
fun isHover(position: Int): Boolean
```

## 网格悬停

Demo截图

<img src="https://i.loli.net/2021/08/14/4CfDnegM2kOi8WK.png" width="250"/>

可以看到图片中悬停的item比普通的item要宽两倍, 这里需要确定悬停的Item的动态`SpanSize`, 所以不能直接使用`grid(3)`而是需要手动创建`HoverGridLayoutManager`

```kotlin
val layoutManager = HoverGridLayoutManager(requireContext(), 2)
layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return if(rv.bindingAdapter.isHover(position)) 2 else 1 // 具体的业务逻辑由你确定
    }
}
rv.layoutManager = layoutManager
```

