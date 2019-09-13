## 介绍

我不认为有更方便的同类型框架


### 特性

- 使用Databinding构建MVVM
- 完美拥有Kotlin的特性
- 通用适配器
- 方便实现常见需求
- 代码量最少, 逻辑更清晰.



### 需求

用最少的代码实现开发过程中的常见需求:

- 多类型
  - 单一数据模型一对多
  - 多数据模型
  
- 添加头布局和脚布局

- 点击/长按事件
  
  - 过滤重复点击
  
- 切换模式

- 选择模式
  - 全选/取消全选/反选
  - 单选
  - 监听选择事件
  
- 拖拽移动

- 侧滑删除

- 下拉刷新 | 上拉加载 (PageRefreshLayout|SmartRefreshLayout)

- 多状态缺省页 (PageRefreshLayout)

- 自动分页加载 (PageRefreshLayout)

- 扩展
  - 伸缩布局 (FlexboxLayoutManager)
  
  - 自动化网络请求 (KalleExtension)
  
    该网络请求基于Kalle|RxJava|Moshi|LifeCycle|本库 实现自动化的网络请求



未来将支持:

- 无限滚动
- 分组
  - 展开折叠
  - 顶部附着

## 依赖


project of build.gradle

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```



module of build.gradle

```
implementation 'com.github.liangjingkanji:BRV:1.0.2'
```





## 初始化

```kotlin
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
      
        BindingAdapter.modelId = BR.m // 推荐在Application中进行初始化或使用之前即可
    }
}
```




## LayoutManager

快速创建布局管理器

```kotlin
fun RecyclerView.linear(
    @RecyclerView.Orientation orientation: Int = VERTICAL,
    reverseLayout: Boolean = false
)


fun RecyclerView.grid(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = VERTICAL,
    reverseLayout: Boolean = false
)

fun RecyclerView.staggered(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = VERTICAL
)
```

## 多类型

### 不同数据模型

```kotlin
rv.linear().setup {

  addType<Model>(R.layout.item_1)
  addType<Store>(R.layout.item_2)

}.models = data
```



### 一对多数据模型

开发中常常遇到一个列表, 列表每个数据对应的一个字段来判断itemType

```kotlin
addType<Model>{
  // 使用年龄来作为判断返回不同的布局
  when (age) {
    23 -> {
      R.layout.item_1
    }
    else -> {
      R.layout.item_2
    }
  }
}
```





## 分割线

快速实现分割线

```kotlin
fun RecyclerView.divider(
    @DrawableRes drawable: Int,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    block: ((Rect, View, RecyclerView, RecyclerView.State) -> Boolean)? = null
)
```



## 监听事件

监听事件需要先添加想要监听的view

```kotlin
fun addClickable(@IdRes vararg id: Int)
// 过滤500毫秒内重复点击

fun addFastClickable(@IdRes vararg id: Int)
// 区别于上面函数不过滤重复点击事件

fun addLongClickable(@IdRes vararg id: Int)
// 长按事件
```



事件回调

```kotlin
onClick{

}
// 点击事件

onLongClick{

}
// 长按点击事件

onBind {
  
  false
}
```

`onBind`属于`onBindViewHolder`事件监听. 

如果你需要自己去设置数据或绑定事件就使用该函数. 返回值决定是否用框架内部默认的Databinding绑定布局. `false`表示不绑定.



更简单的写法示例

```kotlin
rv.linear().setup {

    addType<Model>{
        R.layout.item_1
    }

    onClick(R.id.item, R.id.user_portrait){

    }


    onLongClick(R.id.item){

    }

}.models = data
```



## 头布局/脚布局

头布局和脚布局在rv中算作一个item, 所以计算`position`的时候应当考虑其.



操作头布局

```kotlin
fun addHeader(view: View)

fun removeHeader(view: View)

fun clearHeader()

fun isHeader(@IntRange(from = 0) position: Int): Boolean
```



操作脚布局

```kotlin
fun addFooter(view: View)

fun removeFooter(view: View)

fun clearFooter()

fun isFooter(@IntRange(from = 0) position: Int): Boolean
```



获取数据

```kotlin
val headerCount: Int

val footerCount: Int
```



获取数据的最终手段不是只有一种而已可以存在对重对称加密手段

## 切换模式

切换模式相当于会提供一个回调函数遍历所有的item, 你可以在这个回调函数里面依次刷新他们.

常用于切换选择模式.

```kotlin
fun toggle()
// 切换模式

fun getToggleMode(): Boolean
// 范围当前出何种切换模式

fun setToggleMode(toggleMode: Boolean)
// 设置切换模式, 如果设置的是当前正处于的模式不会触发回调
```



回调函数

```kotlin
onToggle { type, position, toggleMode ->  // 类型 位置 切换布尔值
	// 在这里可以给item刷新成选择模式
}

// 切换完成
onToggleEnd {
	// 例如切换工具栏为选择模式
}
```



## 选择模式

```kotlin
fun allChecked()
// 全选

fun allChecked(isAllChecked: Boolean)
// 全选或者全部取消全选

fun clearChecked()
// 取消全选

fun reverseChecked() 
// 反选

fun setChecked(@IntRange(from = 0) position: Int, checked: Boolean)
// 设置某个item的选择状态

fun toggleChecked(@IntRange(from = 0) position: Int)
// 切换某个item的选择状态

fun <M> getCheckedModels(): List<M>

fun setCheckableType(@LayoutRes vararg checkableItemType: Int)
// 设置哪些type允许进入选择状态

val checkedCount: Int
```

## 拖拽/侧滑

只支持拖拽移动和侧滑删除



步骤:

1. 开启`ItemTouchHelper`支持
2. 数据模型继承`ItemModel`
3. 自定义扩展



BindingAdapter提供一个字段来开启ItemTouchHelper支持

```kotlin
var touchEnable = false // 默认关闭
```



然后数据模型要求继承`ItemModel`, 根据需求重写函数.

示例:

```kotlin
data class Model(val name: String) : ItemModel() {

    override fun getDrag(): Int {
        return UP or DOWN
    }

    override fun getSwipe(): Int {
        return RIGHT or LEFT
    }
    
}
```

`RIGHT or LEFT`是控制拖拽和侧滑的方向(侧滑不支持UP/DOWN)的常量.

拖拽移动item会自动改变数据模型在数据集合中的位置.



> 扩展功能

如果想要扩展ItemTouchHelper可以给BindingAdapter的变量`itemTouchHelper`赋值

```kotlin
rv.linear().setup {

  addType<Model>(R.layout.item)
  touchEnable = true
  
  itemTouchHelper = ItemTouchHelper(object : DefaultItemTouchCallback(this) {
			// 这里可以重写函数
  })

}.models = data
```



通过给view打上tag标签 `swipe` 可以控制侧滑将会移动的视图.

```xml
<RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="80dp"
        android:orientation="horizontal"
        android:tag="swipe"/>
```



## 缺省页

内部依赖 [StateLayout](https://github.com/liangjingkanji/StateLayout), 该库支持任意 Activity | Fragment | View 实现缺省页功能. 



主要特点

- 全局配置
- 单例配置
- 生命周期(可以加载动画或者处理事件)
- 支持activity/fragment/view替换
- 支持代码或者XML实现
- 无网络情况下showLoading显示错误布局, 有网则显示加载中布局



本库已经依赖, 无需再次依赖. 使用方法见GitHub.

## PageRefreshLayout

该布局扩展自 [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)(该库强烈推荐使用), 支持其所有特性.



本库已引入SmartRefreshLayout `'com.scwang.smart:refresh-layout-kernel:2.0.0-alpha-1'`, 无需再次引入.

针对需要的请求头可以去其开源地址分包引入.



在此基础上增加特性

- 缺省页
- 自动分页加载
- 支持KT特性
- 代码替换

### 创建方式

```xml
// 1. 代码创建, 通过扩展函数
val page = rv.page()

// 2. 布局包裹
<com.drake.brv.PageRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
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

如果`onLoadMore` 不调用则上拉加载同样也会回调`onRefresh`函数, 因为下拉刷新和上拉加载可能都是走的同一个网络请求.



### 缺省页

触发刷新状态(两者都会回调函数onRefresh)

1. `autoRefesh`  这是触发的下拉刷新
2. `showLoading` 这是触发的加载页面, 当然得先设置loadingLayout(或者读取全局缺省页配置)
3. `refresh` 静默刷新, 不会触发任何动画



第二种方式一般用于初次进入页面时候加载数据, 这三种方式都会导致索引`index=startIndex`重置.



其他状态控制

```
showEmpty()
showError()
showContent
```





>  配置全局缺省页

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
    app:stateEnabled="true"
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

    stateEnabled = true
}
```



两个方式可以看到都需要执行`stateEnabled = true`这个函数, 否则无效. 如果不设置单例就会显示默认的全局缺省页



想要使用缺省页又要求缺省页不遮盖头布局, 请使用`CoordinatorLayout`. 使用`NestedScrollView`会导致rv无法复用item.



### 刷新数据

前面提到 PageRefreshLayout 支持自动分页加载



```kotlin
// 设置分页加载第一页的索引, 默认=1, 触发刷新会重置索引.
PageRefreshLayout.startIndex = 0 

page.onRefresh {

  // 这是网络请求
  post("/path") {
		addParames("key", "value")
  }.net(activity) {
    // 该回调函数参数返回一个布尔类型用于判断是否存在下一页, 决定上拉加载的状态.
    addData(data, { adapter.count < data.page }) 
  }

}
```



如果感觉网络请求的代码很优雅, 可以关注我的Github网络请求库. 