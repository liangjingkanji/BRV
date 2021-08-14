本章节说的拉取更多不是上拉加载更多, 而是下拉加载更多. 常见的应用场景就是聊天界面下拉取加载历史记录

<br>
例如聊天记录界面, 最新的消息在底部, 下拉列表会触发加载下一页. 这种需求在BRV中只需要一行代码(扩展自[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout))

<br>

<img src="https://i.loli.net/2021/08/14/J9ZEOlKGHsQygwV.gif" width="250"/>

```kotlin hl_lines="8"
override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    rv_up_fetch.setup {
        addType<Model>(R.layout.item_multi_type_simple)
    }

    page.upFetchEnabled = true

    page.onRefresh {
        // 模拟网络请求2秒后成功
        postDelayed({
            val data = getData()
            addData(data) { index <= 2 }
        }, 1000)
    }.showLoading() //  加载中(缺省页)
}
```
除了高亮的一个属性设置, 其他代码和正常一样