悬停条目又常被称为`粘性头部`

<figure markdown>
  ![](https://i.loli.net/2021/08/14/4wUngbV2qZFAf5H.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef2/sample/src/main/java/com/drake/brv/sample/ui/fragment/hover/HoverLinearFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

Model实现接口`ItemHover`

```kotlin
class HoverHeaderModel : ItemHover {
    // 返回值决定是否悬停
    override var itemHover: Boolean = true
}
```


## 监听悬停事件

```kotlin
rv.linear().setup {
    addType<Model>(R.layout.item_simple)
    addType<HoverHeaderModel>(R.layout.item_hover_header)

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
}.models = getData()
```

!!! success "支持点击事件"
    不同于大部分框架实现的悬停, BRV支持全部的点击/长按事件

## 网格悬停

<img src="https://i.loli.net/2021/08/14/4CfDnegM2kOi8WK.png" width="250"/>

可以看到图中悬停的item比普通item要宽两倍, 所以需要返回动态`SpanSize`

```kotlin
val layoutManager = HoverGridLayoutManager(requireContext(), 2)
layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return if(rv.bindingAdapter.isHover(position)) 2 else 1 // 具体处理由开发者确定
    }
}
rv.layoutManager = layoutManager
```

