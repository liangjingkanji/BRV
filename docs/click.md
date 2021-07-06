## 函数

| 函数 | 描述 |
|-|-|
| onFastClick | 为指定的Id添加事件监听器 |
| onClick | 为指定的Id添加点击事件监听器, 但是会包含防抖动. <br>既默认500毫秒内不允许重复点击. 可以通过`clickPeriod`设置间隔 |
| onLongClick | 为指定的Id添加长按事件监听器 |

> 通过使用Item的布局文件中的控件id可以为任何视图设置点击事件或者长按事件, Item的点击事件就是给Item的根布局添加Id.

## 多对一点击事件

这种使用适用于多个id处理同一个点击业务逻辑

```kotlin
rv_simple.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    onClick(R.id.item) {
        // Item设置点击事件, 就要给Item的根布局设置一个id, 这里设置的是R.id.item
    }
}.models = getData()
```

onClick参数是可变长度. 可以指定多个Id, 并且存在覆盖行为.  onFastClick / onLongClick同理

```kotlin
rv_simple.linear().setup {
    addType<NormalModel>(R.layout.item_multi_type_normal)

    onLongClick(R.id.item) {

    }
    onClick(R.id.btn_submit) {
        // it就是你设置的id
    }

    onClick(R.id.btn_submit) {
        // 这会覆盖上面的回调逻辑, 因为两者设置的Id相同
    }
}.models = getData()
```

## 一对一点击事件

既一个Id对应一个点击事件回调. 那么可以使用以下更加简洁的用法

```kotlin
rv_simple.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    R.id.tv_simple.onClick {
        toast("点击Text")
    }
    R.id.item.onLongClick {
        toast("点击Item")
    }
}.models = getData()
```