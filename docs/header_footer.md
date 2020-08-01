![footer](https://tva1.sinaimg.cn/large/006y8mN6gy1g73msnitazg308m0iong7.gif)

1.  头布局和脚布局在rv中算作一个item, 所以计算`position`的时候应当考虑其中
2.  头布局和脚布局也需要使用`addType`函数添加类型



## 函数

操作头布局

```kotlin
fun addHeader(model: Any?, @IntRange(from = -1) index: Int = -1, animation: Boolean = false)
// 添加头布局
// index: 添加到第几个头布局 
// animation: 添加时是否有列表动画

fun removeHeader(model: Any?, animation: Boolean = false)
// 删除对应数据模型的头布局

fun removeHeaderAt(@IntRange(from = 0) index: Int = 0, animation: Boolean = false)
// 删除指定位置的头布局

fun clearHeader(animation: Boolean = false)
// 清除所有头布局

fun isHeader(@IntRange(from = 0) position: Int): Boolean
// 当前position是否属于头布局

val headerCount: Int
// 当前头布局的数量
```



操作脚布局

```kotlin
fun addFooter(model: Any?, @IntRange(from = -1) index: Int = -1, animation: Boolean = false)

fun removeFooter(model: Any?, animation: Boolean = false)

fun removeFooterAt(@IntRange(from = -1) index: Int = -1, animation: Boolean = false)

fun clearFooter(animation: Boolean = false)

fun isFooter(@IntRange(from = 0) position: Int): Boolean

val footerCount: Int
```



