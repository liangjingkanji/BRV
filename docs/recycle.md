因为BRV并不需要创建Adapter, 所以很多人可能不知道如何复用相似列表. 实际上更加简单

首先根据请你了解BRV[三种绑定数据的方式](index.md)

## 使用DataBinding

如果你使用DataBinding那么就更好实现了. DataBinding的业务逻辑都在Model中,  视图都在XML文件中. 如果UI或者业务相同你注册同样的类和XML文件即可

```kotlin
// 列表 1
rv.linear().setup {
    addType<Model>(R.layout.item_simple)
}.models = getData()

// 列表2
rv2.linear().setup {
    addType<Model>(R.layout.item_simple)
}.models = getData()
```


<br>

### 数据模型差异

如果数据模型不一样, 但是XML一样. 你可以使用以下两种方式
<br>

1. 将多个类包装到一个类对象中作为列表数据(万物皆对象)
    ```kotlin
    class ComposeModel(var model: Model, var model2: Model2)

    rv.linear().setup {
        addType<ComposeModel>(R.layout.item_simple)
    }.models = getData()
    ```
2. 多个类实现指定接口: [接口类型](/multi-type/#_4)
    ```kotlin
    interface ModelImpl {
        var text: String = defaultText // 暴露使用函数
    }
    class Model():ModelImpl {
        override var text: String = otherText
    }
    class Model2():ModelImpl {
        override var text: String = otherText
    }

    rv.linear().setup {
        addType<ModelImpl>(R.layout.item_simple)
    }.models = getData()
    ```


## 实现ItemBind接口

这种数据绑定形式业务和UI都在一个函数中实现, 所以你重复使用这个数据模型集合, 如果UI相同但是数据有差异参考上面包装数据模型

```kotlin
class SimpleModel(var name: String = "BRV") : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val appName = holder.context.getString(R.string.app_name)
        holder.findView<TextView>(R.id.tv_simple).text = appName + itemPosition
    }
}
```

```kotlin
// 列表 1
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
}.models = getData()

// 列表2
rv2.linear().setup {
    addType<SimpleModel>(R.layout.item_simple2)
}.models = getData()
```

## 使用onBind

以下这种形式不太方便复用. 本身也只是图方便并不是很推荐大量逻辑场景下使用. 这个你要复制就使用复制粘贴吧....

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    onBind {
        findView<TextView>(R.id.tv_simple).text = getModel<SimpleModel>().name
    }
}.models = getData()
```


