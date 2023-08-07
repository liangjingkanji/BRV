缺省页对于提升应用用户体验非常重要

!!! success "站在巨人肩膀"
    BRV集成优秀成熟的三方库[StateLayout](https://github.com/liangjingkanji/StateLayout)来实现列表缺省页

<br>

主要特点

- [x] 全局/局部缺省页
- [x] 布局或代码声明
- [x] 快速配置点击重试
- [x] 监听缺省页回调
- [x] 自定义动画/布局
- [x] [骨骼动画](https://liangjingkanji.github.io/StateLayout/skeleton.html)
- [x] 传递标签
- [x] 异步线程使用
- [x] 自定义缺省页切换处理
- [x] 配合[BRV](https://github.com/liangjingkanji/BRV/)使用自动化显示列表缺省页 (可选)
- [x] 配合[Net](https://github.com/liangjingkanji/Net/)网络请求自动化显示缺省页 (可选)

<br>

<figure markdown>
  ![](https://i.loli.net/2021/08/14/cliN9VtnAfjb1Z4.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef2/sample/src/main/java/com/drake/brv/sample/ui/fragment/StateLayoutFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>


## 使用

第一步, 在Application中初始化

```kotlin
StateConfig.apply {
    emptyLayout = R.layout.layout_empty
    errorLayout = R.layout.layout_error
    loadingLayout = R.layout.layout_loading
}
```

第二步, 创建缺省页

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

第三步, 创建列表

```kotlin
rv.linear().setup {
    addType<Model>(R.layout.item_simple)
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

自定义StateLayout缺省页动画或监听生命周期可阅读以下文档

- [GitHub](https://github.com/liangjingkanji/StateLayout/)
- [使用文档](https://liangjingkanji.github.io/StateLayout)

## 骨骼动画

骨骼动画实际上就是对应布局的动画或图片, BRV骨骼动画同样由StateLayout实现: [骨骼动画](https://liangjingkanji.github.io/StateLayout/skeleton.html)