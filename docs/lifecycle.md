!!! question "RV生命周期是什么"
    最重要的两个方法

    1. `onCreateViewHolder` 创建视图<br>
        调用次数为屏幕同时可展示的Item数量, 对视图的频繁操作优先考虑此回调中进行
    2. `onBindViewHolder` 绑定数据<br>
        每次Item被显示到屏幕上时回调, 会在快速滑动列表的时候反复调用! 不建议耗时操作

<br>
而在BRV中简化了这两个函数

| 函数 | 描述 |
|-|-|
| onCreate | 对应Adapter的`onCreateViewHolder`函数 |
| onBind | 对应Adapter的`onBindViewHolder`函数 |
| onBindViewHolders | 一个`onBindViewHolder`监听器的集合, 提供给开发者扩展功能使用 |

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    onCreate {
        // 一般在这里配置事件监听
    }

    onBind {
        val binding = getBinding<ItemSimpleBinding>() // 使用ViewBinding/DataBinding都可以使用本方法
        val data = holder.getModel<SimpleModel>()
    }
}.models = getData()
```

## 重写

任何没有提供的方法回调可继承`BindingAdapter`复写

```kotlin
binding.rv.linear().adapter = object : BindingAdapter() {
    override fun onViewRecycled(holder: BindingViewHolder) {
        super.onViewRecycled(holder)
        // ....
    }
}.apply {
    addType<SimpleModel>(R.layout.item_simple)
    models = getData()
}
```

!!! warning "不允许重复调用"
    onBind或onCreate请勿重复调用, 其存在覆盖关系

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    onCreate {
        when(itemViewType){
            R.layout.item_simple -> {
                // 特殊处理
            }
        }
    }
}.models = getData()
```

## 显示/隐藏

Model实现`ItemAttached`可以监听Item显示/隐藏

```kotlin
data class SimpleModel(var name: String = "BRV") : ItemAttached {

    var visibility: Boolean = false // 显示隐藏

    override fun onViewAttachedToWindow(holder: BindingAdapter.BindingViewHolder) {
        visibility = true
    }

    override fun onViewDetachedFromWindow(holder: BindingAdapter.BindingViewHolder) {
        visibility = false
    }

}
```