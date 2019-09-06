## 介绍

### 特性

- 使用Databinding构建MVVM
- 拥有Kotlin的特性
- 通用适配器
- 方便实现常见需求
- 代码量更少, 逻辑更清晰.



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
- 扩展
  - 下拉刷新 (SmartRefreshLayout)
  - 上拉加载 (SmartRefreshLayout)
  - 多状态缺省页 (StateLayout)
  - 伸缩布局 (FlexboxLayoutManager)



TODO

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
implementation 'com.github.liangjingkanji:BRV:1.0.1'
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



