<figure markdown>
  ![](https://s2.loli.net/2022/04/24/JgSrqjWAP26b8x5.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef2/sample/src/main/java/com/drake/brv/sample/ui/fragment/HeaderFooterFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

!!! Warning "多类型列表"
    1. 头/脚布局在RV中只是多类型item, 所以在计算`position`时应当考虑其中
    2. 头/脚布局也需要使用`addType`函数添加类型

    ```kotlin
    BRV数据集 = Header + Models + Footer
    ```

```kotlin
binding.rv.linear().setup {
    addType<Model>(R.layout.item_simple)
    addType<Header>(R.layout.item_header)
    addType<Footer>(R.layout.item_footer)
}.models = getData()

binding.rv.bindingAdapter.run {
    addHeader(Header(), animation = true)
    addFooter(Footer(), animation = true)
}
```

其他头/脚布局实现方式

1. 可使用[ConcatAdapter](https://developer.android.com/reference/androidx/recyclerview/widget/ConcatAdapter)连接多个`BindingAdapter`
1. `NestedScrollView`嵌套RV会导致RV一次性加载全部列表, 大量列表数据时会卡顿

<br>


## 下拉刷新位置

如果使用`CoordinatorLayout`方案来解决列表缺省页覆盖头/脚布局问题, 但是期望从页面顶部开始下拉刷新动画

<figure markdown>
  ![](https://raw.githubusercontent.com/liangjingkanji/BRV/master/docs/img/md/partial-state.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef245e7f312a0077194611f1c2aded647a3c/sample/src/main/java/com/drake/brv/sample/ui/fragment/PagePartStateFragment.kt#L27" target="_blank"><figcaption>PagePartStateFragment</figcaption></a>
</figure>

```kotlin title="fragment_page_partial_state.xml"
<com.drake.brv.PageRefreshLayout
    android:id="@+id/page"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:page_rv="@id/rv"
    app:page_state="@id/state">

    <androidx.coordinatorlayout.widget.CoordinatorLayout>

        <com.google.android.material.appbar.AppBarLayout>

            // ... HEADER

        </com.google.android.material.appbar.AppBarLayout>

        <com.drake.statelayout.StateLayout
            android:id="@+id/state"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior">

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/rv"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />

        </com.drake.statelayout.StateLayout>

    </androidx.coordinatorlayout.widget.CoordinatorLayout>

</com.drake.brv.PageRefreshLayout>

```

1. `app:page_rv` 指定嵌套的rv
2. `app:page_state` 指定嵌套的缺省页

## 函数

| 函数 | 描述 |
|-|-|
| addHeader/addFooter | 添加头布局/脚布局 |
| removeHeader/removeFooter | 删除头布局/脚布局 |
| removeHeaderAt/removeFooterAt | 删除指定索引的头布局/脚布局 |
| clearHeader/clearFooter | 清除全部头布局/脚布局 |
| isHeader/isFooter | 指定索引是否是头布局/脚布局 |
| headerCount/footerCount | 头布局/脚布局数量 |





