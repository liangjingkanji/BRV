<img src="https://i.imgur.com/XOAv59E.gif" width="50%"/>

可编辑/多选列表在开发中很常见, BRV可以短短几行代码就可以实现一个选择模式: [详细Demo](https://github.com/liangjingkanji/BRV/blob/master/smaple/src/main/java/com/drake/brv/sample/ui/fragment/CheckModeFragment.kt)

## 多选列表

1. 创建列表
    ```kotlin
    rv_check_mode.linear().setup {
        addType<CheckModel>(R.layout.item_check_mode)
    }.models = getData
    ```

2. 为Model创建一个字段用于保存选择的状态
    ```kotlin hl_lines="2"
    data class CheckModel(
        var checked: Boolean = false,
        var visibility: Boolean = false
    ) : BaseObservable() // BaseObservable 这是DataBinding的数据绑定写法
    ```

3. 监听选择事件
    ```kotlin hl_lines="3"
    rv_check_mode.linear().setup {
       addType<CheckModel>(R.layout.item_check_mode)
       onChecked { position, isChecked, isAllChecked ->
            val model = getModel<CheckModel>(position)
            model.checked = isChecked
            model.notifyChange() // 通知UI跟随数据变化
       }
    }.models = getData
    ```

4. 触发选择事件
    ```kotlin hl_lines="11"
    rv_check_mode.linear().setup {
       addType<CheckModel>(R.layout.item_check_mode)
       onChecked { position, isChecked, isAllChecked ->
            val model = getModel<CheckModel>(position)
            model.checked = isChecked
            model.notifyChange() // 通知UI跟随数据变化
       }

       onClick(R.id.cb, R.id.item) {
            var checked = (getModel() as CheckModel).checked
            setChecked(adapterPosition, checked) // 在点击事件中触发选择事件, 即点击列表条目就选中
       }
    }.models = getData
    ```


<br>

!!! tip "更多"
    学完如何创建一个多选列表后可以查看函数学习更复杂的功能配置
    <img src="https://i.imgur.com/dPKZUVY.png" width="80%"/>

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

