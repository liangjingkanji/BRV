## RecyclerView

为RV简化的扩展函数, 例如`rv.bindingAdapter.models` 改为`rv.models`

| 函数            | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| bindingAdapter  | 如果adapter是`BindingAdapter`则返回, 否则抛出异常            |
| models          | 设置集合, 会`notifyDataChanged()`                            |
| setDifferModels | 设置集合, 使用`DiffUtil.calculateDiff`来决定`notifyXX()`更新视图 |
| addModels       | 添加/插入集合, 会`notifyDataChanged()`                       |
| _data           | 对应列表的集合对象, 需手动通知更新                           |

## 布局管理器

快速创建布局管理器

=== "线性列表"
    ```kotlin hl_lines="1"
    rv.linear().setup {
        addType<SimpleModel>(R.layout.item_simple)
    }.models = getData()
    ```
=== "网格列表"
    ```kotlin hl_lines="1"
    rv.grid(3).setup {
        addType<SimpleModel>(R.layout.item_simple)
    }.models = getData()
    ```
=== "瀑布流列表"
    ```kotlin hl_lines="1"
    rv.staggered(3).setup {
        addType<SimpleModel>(R.layout.item_simple)
    }.models = getData()
    ```

| 函数 | 描述 |
|-|-|
| [linear](api/-b-r-v/com.drake.brv.utils/grid.html) | 使用`LinearLayoutManager`创建线性列表 |
| [grid](api/-b-r-v/com.drake.brv.utils/grid.html) | 使用`GridLayoutManager`创建网格列表 |
| [staggered](api/-b-r-v/com.drake.brv.utils/staggered.html) | 使用`StaggeredLayoutManager`创建瀑布流列表 |



## 分隔线

使用`divider`快速创建[DefaultDecoration](api/-b-r-v/com.drake.brv/-default-decoration/index.html)

```kotlin hl_lines="1"
rv.linear().divider(R.drawable.divider_horizontal).setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```