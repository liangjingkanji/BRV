<img src="https://s2.loli.net/2022/04/24/JgSrqjWAP26b8x5.gif" width="250"/>

1.  头布局和脚布局在rv中算作一个item, 所以计算`position`的时候应当考虑其中
2.  头布局和脚布局也需要使用`addType`函数添加类型


```kotlin
binding.rv.linear().setup {
    addType<Model>(R.layout.item_multi_type_simple)

    /**
     * BRV的数据集 = Header + Footer + Models
     * 所以本质上他们都是一组多类型而已, 分出来只是为了方便替换Models而不影响Header和Footer
     */

    addType<Header>(R.layout.item_multi_type_header)
    addType<Footer>(R.layout.item_multi_type_footer)
}.models = getData()

binding.rv.bindingAdapter.addHeader(Header(), animation = true)
binding.rv.bindingAdapter.addFooter(Footer(), animation = true)
```

不使用上面介绍的方法实现联动列表的Header还有以下解决方案

1. 如果你不想缺省页覆盖Header请使用CoordinatorLayout来实现Header效果, 因为`PageRefreshLayout`的缺省页功能会导致整个列表可能都会被缺省页遮挡
1. 如果只是不想Header或者Footer干扰BRV可以使用[ConcatAdapter](https://developer.android.com/reference/androidx/recyclerview/widget/ConcatAdapter)实现
1. 使用`ScrollView/NestedScrollView`嵌套rv也可以实现Header但会导致其失去复用item效果, 列表存在大量数据可能会造成卡顿(如果数据量小可以忽略)

<br>

## 函数

| 函数 | 描述 |
|-|-|
| addHeader / addFooter | 添加头布局/脚布局 |
| removeHeader / removeFooter | 删除头布局/脚布局 |
| removeHeaderAt / removeFooterAt | 删除指定索引的头布局/脚布局 |
| clearHeader / clearFooter | 清除全部头布局/脚布局 |
| isHeader / isFooter | 指定索引是否是头布局/脚布局 |
| headerCount / footerCount | 头布局/脚布局数量 |





