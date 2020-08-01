## 预览

![state](https://i.imgur.com/mbYUa7D.gif)

BRV的缺省页实现使用 [StateLayout](https://github.com/liangjingkanji/StateLayout), 该库支持任意 Activity | Fragment | View 实现缺省页功能. 



主要特点

- [x] 全局配置
- [x] 单例配置
- [x] 生命周期(可以加载动画或者处理事件)
- [x] 支持activity/fragment/view替换
- [x] 支持代码或者XML实现
- [x] 无网络情况下showLoading显示错误布局, 有网则显示加载中布局



本库已经依赖, 无需再次依赖. 使用方法见 StateLayout的 [GitHub](https://github.com/liangjingkanji/StateLayout)





关于下拉刷新上拉加载的缺省页展示

```kotlin
// 创建列表
rv.linear().setup {
    addType<Model>(R.layout.item_multi_type_simple)
    addType<DoubleItemModel>(R.layout.item_multi_type_two)
}

// 下拉刷新请求数据
page.onRefresh {
    postDelayed({ // 模拟网络请求, 创建假的数据集
        val data = getData()
        
        addData(data) {
            index < total // 通过判断是否有更多页
        }
        
    }, 2000)

    toast("右上角菜单可以操作刷新结果, 默认2s结束")

}.autoRefresh()
```

