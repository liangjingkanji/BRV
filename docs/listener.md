## 函数

| 函数 | 描述 |
|-|-|
| [addClickable](api/-b-r-v/com.drake.brv/-binding-adapter/index.html#-418380515%2FFunctions%2F-900954490) | 添加需要监听点击事件的视图Id (500毫秒防抖动) |
| [addFastClickable](api/-b-r-v/com.drake.brv/-binding-adapter/index.html#717762977%2FFunctions%2F-900954490) | 添加需要监听点击事件的视图Id |
| [addLongClickable](api/-b-r-v/com.drake.brv/-binding-adapter/index.html#1688979329%2FFunctions%2F-900954490) | 添加需要监听长按事件的视图Id |
| [onClick](api/-b-r-v/com.drake.brv/-binding-adapter/index.html#-273659380%2FFunctions%2F-900954490) | 添加点击事件监听器, 同时支持参数添加Id |
| [onLongClick](api/-b-r-v/com.drake.brv/-binding-adapter/index.html#1487040368%2FFunctions%2F-900954490) | 添加长按事件监听器, 同时支持参数添加Id |
| [onBind](api/-b-r-v/com.drake.brv/-binding-adapter/index.html#-2089117998%2FFunctions%2F-900954490) | 对应Adapter的`onBindViewHolder`函数回调, 每次显示Item都会触发回调, 用于处理数据绑定 |
| [onCreate](api/-b-r-v/com.drake.brv/-binding-adapter/index.html#1726394510%2FFunctions%2F-900954490) | 对应Adapter的`onCreateViewHolder`函数回调, 用于创建Item的视图 |
| [listBind](api/-b-r-v/com.drake.brv/-binding-adapter/index.html#-1936794380%2FProperties%2F-900954490) | 一个`onBindViewHolder`监听器的集合, 一般用于其他框架来监听扩展, 使用者一般不需要使用 |

> 使用onCreate获取itemViewType在作用域中使用`it`, 而不是其`iteViewType`.

这是因为在onCreateViewHolder期间的ViewHolder的itemViewType实际上是没有值的. 而onCreate就是对应的`onCreateViewHolder`

```kotlin
rv_simple.linear().setup {
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

## 示例

通过使用Item的布局文件中的控件id可以设置点击事件或者长按事件

```kotlin
rv_simple.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    addClickable(R.id.item)
    onClick {
        // Item设置点击事件, 就要给Item的根布局设置一个id, 这里设置的是R.id.item
    }
}.models = getData()
```



其实上面两个步骤可以整合成一个, 这里演示下同时设置多个Id添加点击事件

```kotlin
rv_normal.linear().setup {
    
    addType<NormalModel>(R.layout.item_multi_type_normal)
    
    onClick(R.id.item, R.id.btn_submit) {
        // it就是你设置的id
        when(it){ 
            R.id.item -> {
                // Item设置点击事件, 就要给Item的根布局设置一个id, 这里设置的是R.id.item
            } 
            R.id.btn_submit -> {
                // 做任何事
            }
        }
    }
}.models = getData()
```

## 嵌套

在使用rv嵌套rv时应当在onCreate回调中为内嵌的rv设置视图使用`rv.setup`, 在onBind中绑定数据使用`rv.models`

```kotlin
rv_simple.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    onCreate {
        val rv_nested = findView<RecyclerView>(R.id.rv_check_mode)
        rv_nested.setup {
            addType<NestedModel>(R.layout.item_simple_nested)
        }
    }

    onBind {
        val rv_nested  = findView<RecyclerView>(R.id.rv_check_mode)
        rv_nested.models = getModel<Model>().listNested
    }
}
```