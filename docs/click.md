## 函数

| 函数 | 描述 |
|-|-|
| onClick | 添加指定Id点击事件, 包含防抖动([500ms禁止重复点击](#_4)) |
| onFastClick | 添加指定Id点击事件 |
| onLongClick | 添加指定Id长按事件 |

!!! Failure "点击无效"
    请勿在onBind中设置点击事件, 因为onBind会在列表复用时反复操作同一个View, 导致监听事件覆盖

## 多对一点击事件

适用于多个ID点击使用相同业务处理

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    onClick(R.id.item, R.id.btn_confirm) {
        // 此处it即指定的id
        // Item设置点击事件, 就是给Item的根布局设置一个id, 这里设置的是R.id.item
    }

    onLongClick(R.id.item) {

    }
}.models = getData()
```

!!! Failure "重复点击监听"
    相同ID反复设置点击监听会覆盖, 仅最后生效

## 一对一点击事件

既一个Id对应一个点击事件回调, 那么以下用法更加简洁

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

!!! question "防抖动"
    即防止在短时间内过于频繁的执行相同的任务

BRV使用`onClick`函数设置监听事件即包含防抖动, 以下为修改防抖动间隔时间(默认为500毫秒)

=== "全局"
    ```kotlin
    BRV.debounceClickInterval = 1000 // 单位毫秒
    ```

=== "单例"
    ```kotlin hl_lines="2"
    binding.rv.linear().setup {
        debounceClickInterval = 1000 // 覆盖全局设置

        addType<SimpleModel>(R.layout.item_simple)
        R.id.item.onClick {
            toast("点击文本")
        }
    }.models = getData()
    ```
=== "共享防抖动"
    ```kotlin
    // 启用item所有view点击事件共享防抖动间隔, BRV默认防抖动仅针对单个view
    BRV.debounceGlobalEnabled = true
    ```

!!! question "全局防抖动"
    使用`BRV.lastDebounceClickTime`来判断抖动间隔时间, 即可统一BRV和你实现的防抖动点击事件监听

    - [防抖动示例](https://github.com/liangjingkanji/BRV/blob/master/brv/src/main/java/com/drake/brv/listener/OnDebounceClickListener.kt)