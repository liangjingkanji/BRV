<img src="https://tva1.sinaimg.cn/large/006y8mN6gy1g73msnitazg308m0iong7.gif" width="250"/>

1.  头布局和脚布局在rv中算作一个item, 所以计算`position`的时候应当考虑其中
2.  头布局和脚布局也需要使用`addType`函数添加类型

<br>

> `PageRefreshLayout`的缺省页功能会导致整个列表可能都会被缺省页遮挡 <br>
如果你希望头布局不会被数据问题影响, 并且和RV联动划动 <br>
请使用`CoordinatorLayout`嵌套RV <br>
而不是`ScrollView/NestedScrollView`嵌套RV或者使用BRV的头布局(Header)


## 函数

| 函数 | 描述 |
|-|-|
| addHeader / addFooter | 添加头布局/脚布局 |
| removeHeader / removeFooter | 删除头布局/脚布局 |
| removeHeaderAt / removeFooterAt | 删除指定索引的头布局/脚布局 |
| clearHeader / clearFooter | 清除全部头布局/脚布局 |
| isHeader / isFooter | 指定索引是否是头布局/脚布局 |
| headerCount / footerCount | 头布局/脚布局数量 |





