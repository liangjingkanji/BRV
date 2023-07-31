在BRV中创建多类型非常简单, 通过多次调用`addType()`来添加多个类型即可

## 添加类型

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
当前`addType`的大括号内的`this`就是指定的泛型, 所以可以通过`Model.age`来判断返回不同的多类型

### 接口类型

当`addType<BaseInterfaceModel>`添加一个接口类型, 那么Models中添加的其子类也会被识别为该类型

但当指定的泛型为抽象类/普通类, 将无法识别为其子类, 请使用`addInterfaceType()`来替代

示例数据

```kotlin
private fun getData(): List<Any> {
    return List(3) { InterfaceModel1("item $it") } +
            List(3) { InterfaceModel2(it, "item ${3 + it}") } +
            List(3) { InterfaceModel3("item ${6 + it}") }
}
```

声明列表

```kotlin
binding.rv.linear().setup {
    addType<BaseInterfaceModel>(R.layout.item_interface_type)
}.models = getData()
```

仅简单演示, 实际可以让`BaseInterfaceModel`子类分别实现不同业务逻辑


## 区分类型

每个类型的数据和视图可能不同, 所以需要根据不同类型做不同处理

!!! Failure "类型不匹配"
    如果多类型列表不区分类型进行`getBinding()`或者`getModel()`会导致类型转换失败抛出异常


=== "根据`itemViewType`区分类型"
    ```kotlin
    when(itemViewType) {
        R.layout.item_simple -> {
            getBinding<ItemSimpleBinding>().tvName.text = "文本内容"
        }
        R.layout.item_simple_2 -> {
            getBinding<ItemSimpleBinding2>().tvName.text = "类型2-文本内容"
        }
    }
    ```

=== "根据`getBindingOrNull()`区分类型"
    ```kotlin
    getBindingOrNull<ItemSimpleTextBinding>()?.run {
        tvSimple.text = layoutPosition.toString()
    }
    getBindingOrNull<ItemCommentBinding>()?.run {
        tvContent.text = layoutPosition.toString()
    }
    ```

=== "根据`getModel()`区分类型"
    ```kotlin
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
    ```