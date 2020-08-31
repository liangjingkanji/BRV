## RecyclerView

提供一些`BindingAdapter`常用的调用

```kotlin
fun RecyclerView.setup(block: BindingAdapter.(RecyclerView) -> Unit): BindingAdapter
// 创建一个BindingAdapter在闭包中进行配置

fun RecyclerView.addModels(models: List<Any?>?, animation: Boolean = true)
// 等效于[BindingAdapter.addModels]添加数据

var RecyclerView.models
// 等效于[BindingAdapter.models]添加数据

val RecyclerView.bindingAdapter
// 等效于`adapter as? BindingAdapter`添加数据

```

## 布局管理器

框架还提供快速创建布局管理器的扩展函数, 上面使用示例

=== "LinearLayoutManager"
    ```kotlin hl_lines="1"
    rv_simple.linear().setup {
        addType<SimpleModel>(R.layout.item_simple)
    }.models = getData()
    ```
=== "GridLayoutManager"
    ```kotlin hl_lines="1"
    rv_simple.grid(3).setup {
        addType<SimpleModel>(R.layout.item_simple)
    }.models = getData()
    ```
=== "StaggeredLayoutManager"
    ```kotlin hl_lines="1"
    rv_simple.staggered(3).setup {
        addType<SimpleModel>(R.layout.item_simple)
    }.models = getData()
    ```

相关函数

| 函数 | 描述 |
|-|-|
| [linear](brv/com.drake.brv.utils/androidx.recyclerview.widget.-recycler-view/linear.md) | 使用`LinearLayoutManager`创建线性列表 |
| [grid](api/brv/com.drake.brv.utils/androidx.recyclerview.widget.-recycler-view/grid.md) | 使用`GridLayoutManager`创建网格列表 |
| [staggered]((brv/com.drake.brv.utils/androidx.recyclerview.widget.-recycler-view/staggered.md)) | 使用`StaggeredLayoutManager`创建瀑布流列表 |



## 分割线

框架提供快速设置分隔物扩展函数

```kotlin hl_lines="1"
rv.linear().divider(R.drawable.divider_horizontal).setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```
扩展函数实际上就是使用的[DefaultDecoration](docs/api/brv/com.drake.brv/-default-decoration/index.md)来创建对象


## 对话框

通过扩展函数快速给对话框创建列表

```
Dialog(activity).setAdapter(bindingAdapter).show()
```

函数
```kotlin
fun Dialog.brv(block: BindingAdapter.(RecyclerView) -> Unit): Dialog
```

