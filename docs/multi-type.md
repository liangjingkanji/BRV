在BRV中创建多类型非常简单, 通过多次调用`addType()`来添加多个类型即可

## 添加多类型

### 多对多

即列表数据集合中的每个数据类型都对应一个Item类型

```kotlin
rv.linear().setup {

    addType<Model>(R.layout.item_1)
    addType<Store>(R.layout.item_2)

}.models = data
```



### 一对多

即列表数据集合中数据类型都是一样, 但是根据数据类中的某个字段不同来添加不同Item类型

```kotlin
rv.linear().setup {

    addType<Model>{
        // 使用年龄来作为判断返回不同的布局
        when (age) {
            23 -> {
                R.layout.item_1
            }
            else -> {
                R.layout.item_2
            }
        }
    }

}.models = data
```
当前`addType`的大括号内的`this`就是你指定的泛型, 所以我们直接通过`Model.age`来判断返回不同的多类型

### 接口实现

接口类型是可以addType一个类型, 然后由可以添加N个其子类作为models的数据. 接口具体实现由不同的子类不同实现

示例

```kotlin
interface BaseInterfaceModel {
    var text: String
}

data class InterfaceModel1(override var text: String) : BaseInterfaceModel

data class InterfaceModel2(val otherData: Int, override var text: String) : BaseInterfaceModel

data class InterfaceModel3(val otherText: String) : BaseInterfaceModel {
    override var text: String = otherText
}
```

构建示例数据

```kotlin
private fun getData(): List<Any> {
    // 在Model中也可以绑定数据
    return List(3) { InterfaceModel1("item $it") } +
            List(3) { InterfaceModel2(it, "item ${3 + it}") } +
            List(3) { InterfaceModel3("item ${6 + it}") }
}
```

声明列表

```kotlin
 binding.rv.linear().setup {
            addType<BaseInterfaceModel>(R.layout.item_interface_type)
            R.id.item.onClick {
                toast("点击文本")
            }
        }.models = getData()
```

这里只是演示简单的文本, 具体可以编写更加复杂的业务逻辑


## 区分类型

上面章节提到每个Item类型的数据类型, 所以我们取数据类来进行业务逻辑时需要根据不同类型做不同处理


根据`itemViewType`区分类型

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    addType<ItemSimpleBinding2>(R.layout.item_simple)
    onBind {
        when(itemViewType) {
            R.layout.item_simple -> {
                getBinding<ItemSimpleBinding>().tvName.text = "文本内容"
            }
            R.layout.item_simple_2 -> {
                getBinding<ItemSimpleBinding2>().tvName.text = "类型2-文本内容"
            }
        }
    }
}.models = getData()
```

根据`getBinding()`区分类型
```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    addType<ItemComplexBinding>(R.layout.item_simple)
    onBind {
        when (val viewBinding = getBinding<ViewBinding>()) {
            is ItemSimpleBinding -> {
                viewBinding.tvName.text = "文本内容"
            }
            is ItemComplexBinding -> {
                viewBinding.tvName.text = "类型2-文本内容"
            }
        }
    }
}.models = getData()
```

根据`getModel()`区分类型
```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    addType<ItemComplexBinding>(R.layout.item_simple)
    onBind {
        when (val model = getModel<Any>()) {
            is ChatModel -> {
                model.input = "消息内容"
                model.notifyChange()
            }
            is CommentModel -> {
                model.input = "评论内容"
                model.notifyChange()
            }
        }
    }
}.models = getData()
```