## 1.3.66
- 更新StateLayout至1.3.3

## 1.3.64
- 更新StateLayout至1.3.1
- 新增StateChangedHandler自定义缺省页切换处理
- 新增FadeStateChangedHandler渐变透明切换缺省页
- 新增getBindingOrNull方法

## 1.3.63
Fixed [#164](https://github.com/liangjingkanji/BRV/issues/164)

## 1.3.61
Fixed [#157](https://github.com/liangjingkanji/BRV/issues/157)

## 1.3.60
现在可选依赖DataBinding


## 1.3.58
fixed [#141](https://github.com/liangjingkanji/BRV/issues/141)

## 1.3.57
`PageRefreshLayout.showContent`参数`hasMore`默认值改为true, 避免超出开发者预期关闭加载更多行为

## 1.3.56
修复刷新崩溃

## 1.3.55
解决不调用`PageRefreshLayout.finish`方法而是去调用`SmartRefreshLayout.finishRefresh`导致的上拉加载失效问题


## 1.3.54
- Fixed [#119](https://github.com/liangjingkanji/BRV/issues/119)
- 分组避免重复展开/折叠
- 修复单一展开模式

## 1.3.53
- Fixed [#107](https://github.com/liangjingkanji/BRV/issues/107)
- Fixed [#108](https://github.com/liangjingkanji/BRV/issues/108)

## 1.3.52
避免多次显示加载页面导致缺省页showError无效

## 1.3.51
fix: 修复拖拽功能导致列表显示与数据源不对应的问题

## 1.3.50
升级内置依赖SmartRefreshLayout至最新版本`2.0.5`
```groovy
api 'io.github.scwang90:refresh-layout-kernel:2.0.5'
api 'io.github.scwang90:refresh-header-material:2.0.5'
api 'io.github.scwang90:refresh-footer-classics:2.0.5'
```
注意如果你有添加附属的SmartRefreshLayout刷新头, 需要一并更新至最新的`mavenCentral()`版本

详情查看 [#85](https://github.com/liangjingkanji/BRV/issues/85)

## 1.3.40
- 修复下拉刷新失效
- 添加网格分组/拖拽分组示例
- 优化列表负载更新
- 修改addModels与addData的添加数据逻辑，使得models维持同一个对象
- 更新示例代码

## 1.3.39
暴露对比数据变化的回调接口

## 1.3.38
- 新增对比数据刷新函数`setDifferModels`, 本身BRV就支持官方对比刷新方案, 这次只是优化方案
- 新增`PageRefreshLayout.refreshing`仅在第一次加载数据时时候加载缺省页后续使用静默加载

## 1.3.37
- 修复UpFetch模式下缺省页颠倒问题
- 修复reverseLayout下分割线问题
- 新增ItemDepthUtils来简化获取分组层级深度

## 1.3.36
- 修复UpFetch模式下分割线显示错误问题

## 1.3.35
- 添加多类型列表函数`addType`支持接口多态
- 修复UpFetch拉取加载更多在Activity上内容颠倒问题

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
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    R.id.tv_simple.onClick {
        toast("点击Text")
    }
}.models = getData()
```

## 1.3.20
修复单例缺省页无法覆盖全局缺省页问题

