## RecyclerView

提供一些`BindingAdapter`常用的调用

| 函数 | 描述 |
|-|-|
| bindingAdapter | 如果Adapter是[BindingAdapter]则返回对象, 否则抛出异常 |
| models | 数据模型集合, 无需执行`notify*`函数, 自动使用`notifyDataChanged`刷新 |
| _data | 和models的唯一区别是不会自动使用`notifyDataChanged`刷新 |
| mutable | 可增删的非空[models]只读数据模型集合, 需要执行`notify*`函数手动刷新列表,如果实际没有赋值数据该函数会抛出异常  |
| addModels | 添加数据, 自动刷新列表 |

## 布局管理器

框架还提供快速创建布局管理器的扩展函数, 上面使用示例

=== "LinearLayoutManager"
    ```kotlin hl_lines="1"
    rv.linear().setup {
        addType<SimpleModel>(R.layout.item_simple)
    }.models = getData()
    ```
=== "GridLayoutManager"
    ```kotlin hl_lines="1"
    rv.grid(3).setup {
        addType<SimpleModel>(R.layout.item_simple)
    }.models = getData()
    ```
=== "StaggeredLayoutManager"
    ```kotlin hl_lines="1"
    rv.staggered(3).setup {
        addType<SimpleModel>(R.layout.item_simple)
    }.models = getData()
    ```

相关函数

| 函数 | 描述 |
|-|-|
| [linear](api/-b-r-v/com.drake.brv.utils/grid.html) | 使用`LinearLayoutManager`创建线性列表 |
| [grid](api/-b-r-v/com.drake.brv.utils/grid.html) | 使用`GridLayoutManager`创建网格列表 |
| [staggered](api/-b-r-v/com.drake.brv.utils/staggered.html) | 使用`StaggeredLayoutManager`创建瀑布流列表 |



## 分割线

框架提供快速设置分隔物扩展函数

```kotlin hl_lines="1"
rv.linear().divider(R.drawable.divider_horizontal).setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```
扩展函数实际上就是使用的[DefaultDecoration](api/-b-r-v/com.drake.brv/-default-decoration/index.html)来创建对象


## 对话框

通过扩展函数快速给对话框创建列表

```
Dialog(activity).setAdapter(bindingAdapter).show()
```

函数
```kotlin
fun Dialog.brv(block: BindingAdapter.(RecyclerView) -> Unit): Dialog
```

