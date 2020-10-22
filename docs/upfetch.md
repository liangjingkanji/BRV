一般情况下分页列表快划动到底部时会开始预加载下一页, 但是存在一些场景需要列表是反序的.

<br>
例如聊天记录界面, 最新的消息在底部, 下拉列表会触发加载下一页. 这种需求在BRV中只需要一行代码(扩展自[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout))

<br>

<img src="https://i.imgur.com/TUfL2Bk.gif" width="250"/>

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