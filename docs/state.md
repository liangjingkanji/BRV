缺省页对于一个用户体验良好的应用非常重要

BRV采用集成一个非常优秀缺省页库 [StateLayout](https://github.com/liangjingkanji/StateLayout)来实现列表缺省页

> 已经内嵌在BRV库中, 无需再次依赖StateLayout. 如果你的列表同时包含下拉刷新/上拉加载我建议使用[PageRefreshLayout](refresh.md)替代StateLayout

<br>

主要特点

- [x] 优雅的函数设计
- [x] 局部缺省页
- [x] 布局或代码声明皆可
- [x] 全局/单例配置
- [x] 监听缺省页显示
- [x] 自定义动画
- [x] 多种状态缺省页
- [x] 网络请求回调
- [x] 传递任意对象作为标签
- [x] 快速配置点击重试
- [x] 异步线程
- [x] 无网络立即显示错误缺省页
- [x] 配合列表使用自动化显示列表缺省页
- [x] 配合网络请求自动化显示缺省页

<br>

<img src="https://i.loli.net/2021/08/14/cliN9VtnAfjb1Z4.gif" width="250"/>


## 使用

第一步, 在Application中初始化缺省页

```kotlin
StateConfig.apply {
    emptyLayout = R.layout.layout_empty
    errorLayout = R.layout.layout_error
    loadingLayout = R.layout.layout_loading
}
```

第二步, 创建缺省页

=== "布局创建"
    ```xml
    <com.drake.statelayout.StateLayout
        android:id="@+id/state"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rv"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </com.drake.statelayout.StateLayout>
    ```
=== "代码创建"
    建议在XML布局中创建, 可保持代码可读性且避免不必要的问题发生, 性能也更优
    ```kotlin
    val state = state() // 在Activity/Fragment直接函数创建缺省页, `rv.state()`也可以, 但是还是推荐在布局中创建
    ```

> CoordinatorLayout+ViewPager`要求使用的状态缺省页的XML根布局为`NestedScrollView`, 否则显示缺省页后无法正常滑动

第三步, 创建列表

```kotlin
rv.linear().setup {
    addType<Model>(R.layout.item_multi_type_simple)
    addType<DoubleItemModel>(R.layout.item_multi_type_two)
}.models = getData()
```

第四步, 显示缺省页
```kotlin
state.showLoading()  // 加载中
state.showContent() // 加载成功
state.showError() // 加载错误
state.showEmpty() // 加载失败
```

## StateLayout

StateLayout缺省页库非常推荐使用, BRV内部集成实现列表缺省页给开发者使用

如果你想要自定义缺省页动画以及缺省页的生命周期监听建议你阅读以下文档

- [GitHub](https://github.com/liangjingkanji/StateLayout/)
- [使用文档](https://liangjingkanji.github.io/StateLayout)

## 骨骼动画

骨骼动画实际上就是对应布局的动画或者图片

brv的骨骼动画同样由StateLayout实现: [骨骼动画](https://liangjingkanji.github.io/StateLayout/skeleton/)

具体实现代码示例可以阅读[sample](https://github.com/liangjingkanji/BRV/blob/857e33e2bbc80bbdb9b5dec364012ff353b74d5a/sample/src/main/java/com/drake/brv/sample/ui/fragment/SkeletonFragment.kt)
