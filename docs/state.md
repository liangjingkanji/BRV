
缺省页对于一个用户体验良好的应用非常重要

BRV采用集成一个非常优秀缺省页库 [StateLayout](https://github.com/liangjingkanji/StateLayout)来实现列表缺省页

<br>

主要特点

- [x] 全局配置/单例配置
- [x] 生命周期
- [x] 代码/布局创建缺省页
- [x] 点击重试

<br>

<img src="https://i.imgur.com/ndxXOkl.gif" width="50%"/>


## 使用

第一步, 创建缺省页

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
    ```kotlin
    val state = state() // 在Activity/Fragment直接函数创建缺省页, `rv.state()`也可以, 推荐使用第一种方式
    ```

第二步, 创建列表

```kotlin
rv.linear().setup {
    addType<Model>(R.layout.item_multi_type_simple)
    addType<DoubleItemModel>(R.layout.item_multi_type_two)
}.models = getData()
```

第三步, 显示缺省页
```kotlin
state.showLoading()  // 加载中
state.showContent() // 加载成功
state.showError() // 加载错误
state.showEmpty() // 加载失败
```
<br>

!!! Tip
    StateLayout缺省页库非常推荐使用, BRV也暴露集成的缺省页库给开发者使用, [使用文档](https://liangjingkanji.github.io/StateLayout)
