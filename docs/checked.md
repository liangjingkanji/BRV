## 示例

![check_mode](https://tva1.sinaimg.cn/large/006y8mN6gy1g73mqbzzbyg308m0iotdk.gif)

## 函数

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

