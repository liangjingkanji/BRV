## 生命周期

首先要知道的RecyclerView(以下简称rv)的基础知识

1. onCreateViewHolder (创建视图) 调用次数为屏幕同时可展示的Item数量, 对视图的频繁操作优先考虑此回调中进行!
1. onBindViewHolder (绑定数据) 在每次Item被显示到屏幕上时回调, 故会在快速滑动列表的时候反复调用! 建议不要在里面进行耗时操作 <br>
比如建议使用[Serialze](https://github.com/liangjingkanji/Serialize)取代SharePreference等耗时存储

<br>
而在BRV中简化了这两个函数

| 函数 | 描述 |
|-|-|
| onCreate | 对应Adapter的`onCreateViewHolder`函数回调 |
| onBind | 对应Adapter的`onBindViewHolder`函数回调 |
| onBindViewHolders | 一个`onBindViewHolder`监听器的集合, 一般用于其他框架来监听扩展, 使用者一般不需要使用 |

## 注意

BindingAdapter是`open class` 可以被继承重写, 任何没有提供的函数回调可以通过继承或者匿名类实现 <br>

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

> onBind或onCreate只有最后设置的有效, 存在覆盖关系

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

通过让数据模型实现`ItemAttached`可以感知item显示/隐藏, 比如item的动画就是监听item显示时触发的

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

当然你直接继承BindingAdapter也可以实现

