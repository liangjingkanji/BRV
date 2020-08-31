BRV提供一个切换事件的触发和监听, 相当于会提供一个回调函数遍历所有的列表条目, 你可以在这个回调里面依次更新数据或者视图.

<br>
这个`切换`可以理解为`遍历`列表条目

<br>
一般用于切换列表的编辑模式

<img src="https://i.imgur.com/0uYZkXB.gif" width="50%"/>

<br>

## 示例
```kotlin
override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    rv_check_mode.linear().setup {
        addType<CheckModel>(R.layout.item_check_mode)

        // 监听切换事件
        onToggle { position, toggleMode, end ->
            if (end) {
                // 显示和隐藏编辑菜单
                ll_menu.visibility = if (toggleMode) View.VISIBLE else View.GONE
            }
        }
    }.models = getData()
}

fun onClick(v:View){
    rv_check_mode.bindingAdapter.toggle() // 点击事件触发切换事件
}

```

## 函数

```kotlin
fun toggle()
// 触发切换模式(根据当前状态取反)

fun getToggleMode(): Boolean
// 范围当前出何种切换模式

fun setToggleMode(toggleMode: Boolean)
// 设置切换模式, 如果设置的是当前正处于的模式不会触发回调

fun onToggle(block: (position: Int, toggleModel: Boolean, end: Boolean) -> Unit)
// 监听切换事件, 在事件中你可以处理任何视图的数据或者视图修改
// position: 遍历过程中的列表条目索引
// toggleModel: 切换模式(布尔值)
// end: 是否全部遍历完成
```

