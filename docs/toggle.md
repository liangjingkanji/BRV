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

