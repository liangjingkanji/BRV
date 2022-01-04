## 函数

首先你要知道的RecyclerView(以下简称rv)的基础知识

- onCreateViewHolder显示的次数为需要创建Item视图的个数. 大概比屏幕一次最多可展示的Item数量多, 但是比总Item数量少.
- onBindViewHolder会在每次Item被显示到屏幕上时回调

而在BRV中简化了这两个函数

| 函数 | 描述 |
|-|-|
| onCreate | 对应Adapter的`onCreateViewHolder`函数回调, 用于创建Item的视图 |
| onBind | 对应Adapter的`onBindViewHolder`函数回调, 每次显示Item都会触发回调, 用于处理数据绑定 |
| onBindViewHolders | 一个`onBindViewHolder`监听器的集合, 一般用于其他框架来监听扩展, 使用者一般不需要使用 |

> BindingAdapter是`open class` 可以被继承重写, 任何没有提供的函数回调可以通过继承或者匿名类实现 <br>
> onBind或onCreate只有最后设置的有效. 存在覆盖关系

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    onCreate {
        when(it){
            R.layout.item_simple -> {
                // 特殊处理
            }
        }
    }
}.models = getData()
```

> 在onCreate中获取想要itemViewType, 需要在作用域中使用`it`, 而不是其`itemViewType`字段. <br>
> 这是因为在onCreateViewHolder期间的ViewHolder的itemViewType实际上是没有值的. 而onCreate就是对应的`onCreateViewHolder`


## 嵌套RecyclerView

在使用rv嵌套rv时应当在onCreate回调中为内嵌的rv设置视图(使用`rv.setup`), 这是为了避免同一类型反复创建rv导致内存消耗.  而嵌套的rv数据可以在onBind中绑定数据, 使用`rv.models`

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    onCreate {
        val rv = findView<RecyclerView>(R.id.rv_check_mode)
        rv.linear().setup {
            addType<NestedModel>(R.layout.item_simple_nested)
        }
    }

    onBind {
        val rv  = findView<RecyclerView>(R.id.rv_check_mode)
        rv.models = getModel<Model>().listNested
    }
}
```
