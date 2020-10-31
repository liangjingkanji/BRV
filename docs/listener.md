## 函数

| 函数 | 描述 |
|-|-|
| [addClickable](api/brv/com.drake.brv/-binding-adapter/add-clickable.md) | 添加需要监听点击事件的视图Id (500毫秒防抖动) |
| [addFastClickable](api/brv/com.drake.brv/-binding-adapter/add-fast-clickable.md) | 添加需要监听点击事件的视图Id |
| [addLongClickable](api/brv/com.drake.brv/-binding-adapter/add-long-clickable.md) | 添加需要监听长按事件的视图Id |
| [onClick](api/brv/com.drake.brv/-binding-adapter/on-click.md) | 添加点击事件监听器, 同时支持参数添加Id |
| [onLongClick](api/brv/com.drake.brv/-binding-adapter/on-long-click.md) | 添加长按事件监听器, 同时支持参数添加Id |
| [onBind](api/brv/com.drake.brv/-binding-adapter/on-bind.md) | 对应Adapter的`onBindViewHolder`函数回调, 每次显示Item都会触发回调, 用于处理数据绑定 |
| [onCreate](api/brv/com.drake.brv/-binding-adapter/on-create.md) | 对应Adapter的`onCreateViewHolder`函数回调, 用于创建Item的视图 |
| [listBind](api/brv/com.drake.brv/-binding-adapter/list-bind.md) | 一个`onBindViewHolder`监听器的集合, 一般用于其他框架来监听扩展, 使用者一般不需要使用 |

## 示例

通过使用Item的布局文件中的控件id可以设置点击事件或者长按事件

```kotlin
rv_normal.linear().setup {
    
    addType<NormalModel>(R.layout.item_multi_type_normal)
    
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