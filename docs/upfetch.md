!!! question "拉取更多"
    拉取更多(又称下拉加载更多页), 例如聊天界面下拉加载更多页

<br>
例如聊天记录界面, 最新的消息在底部, 下拉列表会触发顶部加载历史消息

<br>

<figure markdown>
  ![](https://i.loli.net/2021/08/14/J9ZEOlKGHsQygwV.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef245e7f312a0077194611f1c2aded647a3c/sample/src/main/java/com/drake/brv/sample/ui/fragment/UpFetchFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

!!! note "实现原理"
    将PageRefreshLayout反转, 再将RV反转, RV反转2次等于还原

```kotlin hl_lines="8"
rv.setup {
    addType<Model>(R.layout.item_simple)
}

page.upFetchEnabled = true

page.onRefresh {
    // 模拟网络请求2秒后成功
    postDelayed({
        val data = getData()
        addData(data) { index <= 2 }
    }, 1000)
}.showLoading() //  加载中(缺省页)
```

或使用xml属性`app:page_upFetchEnabled`

```xml
<com.drake.brv.PageRefreshLayout
    android:id="@+id/page"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        app:reverseLayout="true"
        app:stackFromEnd="true" />

    <!--stackFromEnd=true 防止UpFetch时数据不满一屏幕的时候, 对齐底部而不是顶部-->
    <!--reverseLayout=true rv数据排列顺序反转-->

</com.drake.brv.PageRefreshLayout>
```