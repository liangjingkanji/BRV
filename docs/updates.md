## 1.3.34
修复分组嵌套展开时产生重复数据问题(子列表为可变集合时引发此问题)

## 1.3.33
- 修复无法仅启用上拉加载问题

## 1.3.32
- 允许BindingAdapter被重写
- 修复加载更多时发生错误判断为没有更多页问题

## 1.3.31
修复PageRefreshLayout缺省页崩溃问题

## 1.3.30
1. 更新依赖SmartRefreshLayout至2.0.3
1. 更新依赖StateLayout至1.2.0
2. 添加缺省页onContent监听

## 1.3.29
隐藏内部函数 throttleClick

## 1.3.28
修复onClick点击防抖动失效问题

## 1.3.27
全局配置[点击防抖动](click.md#_4)间隔时间

## 1.3.26
fixed #20 修复shoLoading缺省页参数错误

## 1.3.25
1. 新增函数isSampleGroup来判断两个位置的item是否处于同一分组下
1.  组查询父项findParentPosition的性能

## 1.3.24
修复局部刷新添加数据时动态分割线可能发生错误

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

