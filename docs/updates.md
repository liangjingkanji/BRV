## 1.3.21
新增可以使用Id直接调用onClick/onFastClick/onLongClick
```kotlin
rv_simple.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    R.id.tv_simple.onClick {
        toast("点击Text")
    }
}.models = getData()
```

## 1.3.20
修复单例缺省页无法覆盖全局缺省页问题

