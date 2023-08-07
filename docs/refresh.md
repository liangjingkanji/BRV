<figure markdown>
  ![](https://i.loli.net/2021/08/14/lV4ktFRAweYorsC.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef2/sample/src/main/java/com/drake/brv/sample/ui/fragment/PullRefreshFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout) 是目前Android上最优秀的刷新框架
<br>

!!! success "PageRefreshLayout"
    BRV通过继承[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)扩展出`PageRefreshLayout`, 在其基础上新增更多功能

<br>
BRV包含以下基础依赖, 无需再次引入
```groovy
api 'io.github.scwang90:refresh-layout-kernel:2.0.5'
api 'io.github.scwang90:refresh-header-material:2.0.5'
api 'io.github.scwang90:refresh-footer-classics:2.0.5'
```

可选配置的下拉/上拉动画

```groovy
implementation  'io.github.scwang90:refresh-layout-kernel:2.0.5'      //核心必须依赖
implementation  'io.github.scwang90:refresh-header-classics:2.0.5'    //经典刷新头
implementation  'io.github.scwang90:refresh-header-radar:2.0.5'       //雷达刷新头
implementation  'io.github.scwang90:refresh-header-falsify:2.0.5'     //虚拟刷新头
implementation  'io.github.scwang90:refresh-header-material:2.0.5'    //谷歌刷新头
implementation  'io.github.scwang90:refresh-header-two-level:2.0.5'   //二级刷新头
implementation  'io.github.scwang90:refresh-footer-ball:2.0.5'        //球脉冲加载
implementation  'io.github.scwang90:refresh-footer-classics:2.0.5'    //经典加载
```

## 初始化

在Application中初始化
```
SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout -> MaterialHeader(this) }
SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> ClassicsFooter(this) }
```


## PageRefreshLayout

` extended SmartRefreshLayout`, 新增以下特性

1.  简化函数
2.  细节优化
3.  缺省页
4.  分页加载
5.  拉取加载更多
6.  预加载 / 预拉取

### 声明

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

### 创建列表
```kotlin
rv.linear().setup {
    addType<Model>(R.layout.item_simple)
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

1. 每次下拉刷新触发`onRefresh`
2. 如果没有配置`onLoadMore`上拉加载也会触发`onRefresh`

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

!!! success "一个onRefresh足矣"
    大部分场景下拉/上拉加载使用同一接口, 仅请求参数不同, 完全可以只用`onRefresh`

### 触发刷新

拥有三个函数触发刷新(回调onRefresh)

| 函数 | 描述 |
|-|-|
| autoRefresh | 显示下拉刷新动画 |
| showLoading | 显示加载中缺省页, 得先设置`loadingLayout` |
| refresh | 静默刷新, 无动画 |
| refreshing | 初次调用等于`showLoading`, 当加载成功后, 再次调用等于`refresh` |

!!! success "请求分页索引"
    刷新会重置索引 `index=startIndex`, index会根据分页`++1`, 可用来当请求分页参数

### 缺省页

PageRefreshLayout内嵌`StateLayout`实现缺省页功能

| 缺省页状态 | 描述 |
|-|-|
| showLoading | 显示加载中缺省页 |
| showEmpty | 显示空缺省页 |
| showError | 显示错误缺省页 |
| showContent | 显示内容页 |

使用`showXX()`方法更改缺省页状态不需要再调用`finishXX()`方法

### 缺省页配置

全局缺省页配置和`StateLayout`共享

1. 全局配置

    ```kotlin
    // 在Application中
    StateConfig.apply {
      emptyLayout = R.layout.layout_empty
      errorLayout = R.layout.layout_error
      loadingLayout = R.layout.layout_loading
    }
    ```

2. 单例配置

=== "布局声明"
    ```xml hl_lines="3 4 5"
    <com.drake.brv.PageRefreshLayout
        .....
        app:error_layout="@layout/layout_error"
        app:empty_layout="@layout/layout_empty"
        app:loading_layout="@layout/layout_loading">

    </com.drake.brv.PageRefreshLayout>
    ```

=== "代码声明"
    ```kotlin
    page.run {
        loadingLayout = R.layout.layout_loading
        emptyLayout = R.layout.layout_empty
        errorLayout = R.layout.layout_error
    }
    ```

1. 禁用缺省页: `stateEnabled`
2. [缺省页覆盖头布局](header-footer.md#_1)



### 自动分页

PageRefreshLayout支持自动分页加载, 直接使用`addData`即可

```kotlin hl_lines="5"
pageLayout.onRefresh {
    scope {
        val data = Get<String>("/path").await()
        addData(data.list){
            // 该回调返回一个布尔值判断是否有更多页, 决定上拉加载是否关闭
            adapter.itemCount < data.count
        }
    }
}
```

分页索引第一页默认为1, 修改如下

```kotlin
 PageRefreshLayout.startIndex = 1 // startIndex即index变量的初始值
```

<br>
> 如果`PageRefreshLayout`非RV直接父类, 请指定[addData](api/-b-r-v/com.drake.brv/-page-refresh-layout/index.html#-704450894%2FFunctions%2F-900954490)参数`adapter`

!!! success "完全自动化"
    当使用 BRV + [Net]((https://github.com/liangjingkanji/Net)) 联动时, 可以实现根据网络结果自动分页/缺省页/生命周期

## 缺省页禁止下拉刷新

控制PageRefreshLayout缺省页显示错误/空状态时, 是否禁止下拉刷新

| 函数 | 描述 |
|-|-|
| refreshEnableWhenEmpty | 是否显示空缺省页时启用下拉刷新 |
| refreshEnableWhenError | 是否显示错误缺省页时启用下拉刷新 |

支持全局/单例配置, 如需更复杂实现建议在回调中由`PageRefreshLayout.setEnableRefresh()`自由控制