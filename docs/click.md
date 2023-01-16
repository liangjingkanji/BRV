## 函数

| 函数 | 描述 |
|-|-|
| onFastClick | 为指定的Id添加事件监听器 |
| onClick | 为指定的Id添加点击事件监听器, 但包含防抖动(默认500毫秒内不允许重复点击). 也可以[设置间隔](#_4) |
| onLongClick | 为指定的Id添加长按事件监听器 |

> 通过使用Item的布局文件中的控件id可以为任何视图设置点击事件或者长按事件, Item的点击事件就是给Item的根布局添加Id.

## 多对一点击事件

这种使用适用于多个id处理同一个点击业务逻辑

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    onClick(R.id.item) {
        // Item设置点击事件, 就要给Item的根布局设置一个id, 这里设置的是R.id.item
    }
}.models = getData()
```

onClick参数是可变长度. 可以指定多个Id, 并且存在覆盖行为.  onFastClick / onLongClick同理

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

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
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    R.id.tv_simple.onClick {
        toast("点击Text")
    }
    R.id.item.onLongClick {
        toast("点击Item")
    }
}.models = getData()
```

## 点击防抖动

> **防抖动**: 一定间隔时间内只会响应第一次的点击. 避免用户快速点击导致重复响应点击事件

BRV支持防抖动很简单, 使用`onClick`函数设置监听事件即可, `onFastClick`即不包含防抖动的点击事件.

以下代码可以修改防抖动间隔时间(默认为500毫秒)

=== "全局"
    ```kotlin
    BRV.clickThrottle = 1000 // 单位毫秒
    ```

=== "单例"
    ```kotlin hl_lines="2"
    binding.rv.linear().setup {
        clickThrottle = 1000 // 覆盖全局设置

        addType<SimpleModel>(R.layout.item_simple)
        R.id.item.onClick {
            toast("点击文本")
        }
    }.models = getData()
    ```