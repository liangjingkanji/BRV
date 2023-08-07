一个遍历所有列表条目的监听事件, 开发者可以在回调内更新视图

!!! question "切换模式"
    `切换`可以理解为`遍历`列表条目, 常用于切换列表为编辑模式

<figure markdown>
  ![](https://i.loli.net/2021/08/14/BVjGH7CT9lZ8KXa.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef245e7f312a0077194611f1c2aded647a3c/sample/src/main/java/com/drake/brv/sample/ui/fragment/CheckModeFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

```kotlin
rv.linear().setup {
    addType<CheckModel>(R.layout.item_check_mode)

    // 监听切换事件
    onToggle { position, toggleMode, end ->
        if (end) {
            // 显示和隐藏编辑菜单
            ll_menu.visibility = if (toggleMode) View.VISIBLE else View.GONE
        }
    }
}.models = getData()

rv.bindingAdapter.toggle() // 点击事件触发切换事件
```

| 函数 | 描述 |
|-|-|
| toggle | 触发切换 |
| toggleMode | 当前切换模式 |
| onToggle | 切换事件回调 |

