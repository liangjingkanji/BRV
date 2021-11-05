## 不同数据的多类型

```kotlin
rv.linear().setup {

    addType<Model>(R.layout.item_1)
    addType<Store>(R.layout.item_2)

}.models = data
```



## 不同字段的多类型

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



## 区分类型

`itemViewType`属于onBind函数接受者`BindingViewHolder`的字段



```kotlin
rv.linear().setup {

    addType<Model>(R.layout.item_1)
    addType<Store>(R.layout.item_2)

    onBind {
		// 
        when (itemViewType) {
            R.layout.item_1 -> {
				// 类型 1
            }
            R.layout.item_2 -> {
                // 类型 1
            }
        }
    }

}.models = data
```

## 接口类型

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