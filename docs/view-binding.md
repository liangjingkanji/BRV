如果已经使用DataBinding那么本章不用了解

| 功能                 | ViewBinding                       | DataBinding                               |
| -------------------- | --------------------------------- | ----------------------------------------- |
| 取代`findViewById()` | :material-check-all:              | :material-check-all:                      |
| 双向数据绑定         | :material-close:                  | :material-check: MVVM最优方案             |
| 复用xml属性          | :material-close:                  | :material-check: 减少代码量               |
| 防止View空指针       | :material-close:                  | :material-check: 代码更健壮               |
| 编译期生成代码       | :material-close: 所有布局全部生成 | :material-check: 仅生成包含`<layout>`生成 |
| 封装实现             | :material-close: 反射+泛型        | :material-check: 使用Api创建          |
| 替换原有xml属性      | :material-close:                  | :material-check: 优先自定义属性           |

## 使用

使用`getBinding()`获取ViewBinding对象

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    onBind {
        val binding = getBinding<ItemSimpleBinding>()
    }
}.models = getData()
```

!!! Failure "多类型需要判断类型"
    如果是多类型请先判断类型再`getBinding()`, 避免获取到错的ViewBinding导致崩溃, 请阅读[区分类型](multi-type.md#_4)
