<p align="center"><img src="https://i.imgur.com/Mt0m2Sy.gif" width="40%"/></p>

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

    rv_hover.linear().setup {
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

