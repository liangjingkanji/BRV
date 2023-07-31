
!!! question "预加载/预拉取"
    即列表末尾还未显示就会开始加载下一页

## 当前列表预加载索引

BRV默认开启预加载/预拉取, 指定字段`preloadIndex`控制显示到倒数第几个条目时开始预加载

```kotlin
var preloadIndex = 3 // 默认为3
```

## 全局预加载索引

`PageRefreshLayout.preloadIndex`设置全局默认值, 所有列表都按照指定的索引开始预加载