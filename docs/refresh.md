<img src="https://i.imgur.com/LZh90KX.gif" width="250"/>

[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout) 应该是目前Android上扩展性最强的刷新框架,
而BRV的下拉刷新和上拉加载正是扩展的SmartRefreshLayout , 支持其所有特性并且还增加了新的功能.
<br>

> 如果需要更多的下拉刷新或者上拉加载的自定义需求请查看[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)的使用文档 <br>
  本框架中的`PageRefreshLayout`继承自`SmartRefreshLayout`, 故拥有其所有特性

<br>
本库已引入SmartRefreshLayout, 无需再次引入
```groovy
api 'com.scwang.smart:refresh-layout-kernel:2.0.0'
api 'com.scwang.smart:refresh-footer-classics:2.0.0'
api 'com.scwang.smart:refresh-header-material:2.0.0'
```


SmartRefreshLayout的指定的刷新头和刷新脚布局请分别依赖(其库如此设计)

可选配置的刷新头布局和脚布局

```groovy
implementation  'com.scwang.smart:refresh-header-classics:2.0.1'    //经典刷新头
implementation  'com.scwang.smart:refresh-header-radar:2.0.1'       //雷达刷新头
implementation  'com.scwang.smart:refresh-header-falsify:2.0.1'     //虚拟刷新头
implementation  'com.scwang.smart:refresh-header-material:2.0.1'    //谷歌刷新头 (内置)
implementation  'com.scwang.smart:refresh-header-two-level:2.0.1'   //二级刷新头
implementation  'com.scwang.smart:refresh-footer-ball:2.0.1'        //球脉冲加载
implementation  'com.scwang.smart:refresh-footer-classics:2.0.1'    //经典加载 (内置)
```

## 初始化
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

### 包住RecyclerView

支持两种方式创建, 推荐布局包裹, 

=== "布局包裹"

    ```xml
    <com.drake.brv.PageRefreshLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
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

=== "代码包裹"

    ```xml
    val page = rv.page()
    ```

### 创建列表
```kotlin
rv.linear().setup {
    addType<Model>(R.layout.item_multi_type_simple)
    addType<DoubleItemModel>(R.layout.item_multi_type_two)
}

page.onRefresh {
    postDelayed({ // 模拟网络请求2秒后成功, 创建假的数据集
        val data = getData()
        addData(data) {
            index < total // 判断是否有更多页
        }
    }, 2000)
}.autoRefresh()
```

1. `onRefresh`即每次刷新/上拉加载都会执行的函数

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

1.  如果不调用`onLoadMore`则上拉加载同样也会执行`onRefresh`函数, 因为下拉刷新和上拉加载在项目中一般是同一个接口只是分页字段值不同而已


### 缺省页

PageRefreshLayout内嵌`StateLayout`同时具备显示缺省页的能力

拥有三个函数可以触发刷新状态(都会回调函数onRefresh)

| 函数 | 描述 |
|-|-|
| autoRefresh | 显示下拉刷新动画 |
| showLoading | 显示加载中缺省页, 当然得先设置`loadingLayout`(或者读取`StateConfig`全局缺省页配置) |
| refresh | 静默刷新(无动画) |

> 这3种触发刷新方式都会导致重置索引 `index=startIndex`, index就是默认根据分页默认递增的字段, 后面会演示如何使用该字段

缺省页状态控制

| 函数 | 描述 |
|-|-|
| showLoading | 显示加载中缺省页 |
| showEmpty | 显示空缺省页 |
| showError | 显示错误缺省页 |
| showContent | 显示内容页 |


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

前面提到 PageRefreshLayout 支持自动分页加载, 自动分页不需要你调用`rv.models`函数去设置数据, 使用`addData`即可

```kotlin hl_lines="8"
//
//

// 下拉刷新和上拉加载都会执行onRefresh, 除非另外设置onLoadMore
pageLayout.onRefresh {
    scope {
        val data = Get<String>("/path").await()
        addData(data.list){
            // 该回调函数参数返回一个布尔类型用于判断是否存在下一页, 决定上拉加载是否关闭
            adapter.itemCount < data.count // 这里是判断是否由更多页, 具体逻辑根据接口定义
        }
    }
}
```

大部分情况后端定义分页字段第一页为1, 但是可能存在部分后端定义为0, 这里我们可以在Application中设置`index`的初始值即第一页的字段

```kotlin
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        PageRefreshLayout.startIndex = 1 // startIndex即index变量的初始值
    }
```

这里的网络请求使用的是我开源的另一个项目Net, 支持扩展BRV. GitHub: [Net](https://github.com/liangjingkanji/Net).
<br>
> 假设`PageRefreshLayout`没有直接包裹RecyclerView, 这个时候需要[addData](api/-b-r-v/com.drake.brv/-page-refresh-layout/index.html#-704450894%2FFunctions%2F-900954490)函数指定参数adapter来使用自动分页, 否则将抛出异常

