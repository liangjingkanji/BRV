## 1.3.22
为减少添加点击事件Id后还得判断Id. 点击事件现在和Id对应配置, 不做统一处理. 废弃部分函数

| 废弃函数 | 替换 |
|-|-|
| addFastClickable| 替换为onFastClick |
| addClickable | 替换为onClick |
| addLongClickable | 替换为onLongClick |

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

