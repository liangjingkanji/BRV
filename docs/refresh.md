BRV的下拉刷新和上拉加载扩展自 [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout), 支持其所有特性

![refresh](https://tva1.sinaimg.cn/large/006y8mN6gy1g73mt2hy6xg308m0iox3o.gif)



本库已引入SmartRefreshLayout, 无需再次引入

```groovy
implementation  'com.scwang.smart:refresh-layout-kernel:2.0.1'
```



SmartRefreshLayout的指定的刷新头和刷新脚布局请分别依赖(其库如此设计)

可选配置的刷新头布局和脚布局

```groovy
implementation 'androidx.appcompat:appcompat:1.0.0'                 //必须 1.0.0 以上

implementation  'com.scwang.smart:refresh-layout-kernel:2.0.1'      //核心必须依赖
implementation  'com.scwang.smart:refresh-header-classics:2.0.1'    //经典刷新头
implementation  'com.scwang.smart:refresh-header-radar:2.0.1'       //雷达刷新头
implementation  'com.scwang.smart:refresh-header-falsify:2.0.1'     //虚拟刷新头
implementation  'com.scwang.smart:refresh-header-material:2.0.1'    //谷歌刷新头 (内置)
implementation  'com.scwang.smart:refresh-header-two-level:2.0.1'   //二级刷新头
implementation  'com.scwang.smart:refresh-footer-ball:2.0.1'        //球脉冲加载
implementation  'com.scwang.smart:refresh-footer-classics:2.0.1'    //经典加载 (内置)
```



刷新布局要求必须先初始化, 推荐在Application中

```
SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout -> ClassicsHeader(this) }
SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> ClassicsFooter(this) }
```





## PageRefreshLayout

该控件继承自`SmartRefreshLayout`, 增加以下特性

1.  简化函数
2.  细节优化
3.  缺省页
4.  分页加载
5.  拉取加载更多
6.  预加载 / 预拉取

### 创建方式

支持两种方式创建, 推荐布局包裹, 



=== "代码包裹"

    ```xml
    val page = rv.page()
    ```

=== "布局包裹"

    ```xml
    <com.drake.brv.PageRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:id="@+id/page"
    app:stateEnabled="true"
    android:layout_height="match_parent"
    tools:context="com.drake.brv.sample.fragment.RefreshFragment">
    
    <androidx.recyclerview.widget.RecyclerView
    android:id="@+id/rv"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
    
    </com.drake.brv.PageRefreshLayout>
    ```



### 监听状态

```kotlin
// 下拉刷新
page.onRefresh {
	// 这里可以进行网络请求等异步操作
}

// 上拉加载
page.onLoadMore {
	// 这里可以进行网络请求等异步操作
}
```

1.  如果`onLoadMore` 不调用则上拉加载同样也会回调`onRefresh`函数, 因为下拉刷新和上拉加载在一般接口中定义只是分页字段不一样
2.  



### 缺省页

触发刷新状态(两者都会回调函数onRefresh)

1. `autoRefesh`  下拉刷新
2. `showLoading`  加载Loading缺省页, 当然得先设置`loadingLayout`(或者读取`StateConfig`全局缺省页配置)
3. `refresh` 静默刷新, 不会触发任何动画



这3种方式都会导致索引`index=startIndex`重置.



缺省页状态控制

```
showEmpty()
showError()
showContent
showLoading()
```





**配置全局缺省页**

这块代码和StateLayout公用

```kotlin
/**
         *  推荐在Application中进行全局配置缺省页, 当然同样每个页面可以单独指定缺省页.
         *  具体查看 https://github.com/liangjingkanji/StateLayout
         */
StateConfig.apply {
  emptyLayout = R.layout.layout_empty
  errorLayout = R.layout.layout_error
  loadingLayout = R.layout.layout_loading

  onLoading {
    // 此生命周期可以拿到LoadingLayout创建的视图对象, 可以进行动画设置或点击事件.
  }
}
```



单例缺省页配置支持两种方式

1. XML

```xml
<com.drake.brv.PageRefreshLayout 
    .....
    app:error_layout="@layout/layout_error"
    app:empty_layout="@layout/layout_empty"
    app:loading_layout="@layout/layout_loading">
```



2. 代码

```kotlin
page.apply {
    loadingLayout = R.layout.layout_loading
    emptyLayout = R.layout.layout_empty
    errorLayout = R.layout.layout_error
}
```



默认会使用缺省页, 如果你已经设置了全局缺省页但是此刻不想使用. 可以使用属性|函数: `stateEnabled`



想要使用缺省页又要求缺省页不遮盖头布局, 头布局请使用`CoordinatorLayout`实现. 注意使用`NestedScrollView`会导致rv一次性加载完item内存消耗大.



### 刷新数据

前面提到 PageRefreshLayout 支持自动分页加载



```kotlin
// 设置分页加载第一页的索引, 默认=1, 触发刷新会重置索引. 如果需要修改在Application设置一次即可
// PageRefreshLayout.startIndex = 1

pageLayout.onRefresh { 
  // 下拉刷新和上拉加载都会执行这个网络请求, 除非另外设置onLoadMore
  get("/path") {
      param("key", "value")
      param("page", pageLayout.index) // 这里使用框架提供的属性
  }.page(this) {
    // 该回调函数参数返回一个布尔类型用于判断是否存在下一页, 决定上拉加载的状态. 以及当前属于刷新还是加载更多条目
    addData(data){ adapter.itemCount < data.count // 这里是判断是否由更多页, 具体逻辑根据接口定义 } 
  }
}
```



这里的网络请求使用的是我开源的另一个项目Net, 支持扩展BRV. GitHub: [Net](https://github.com/liangjingkanji/Net).  

