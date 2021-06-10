支持`GridLayoutManager`的网格布局的分割线

> 由于网格分割线是动态计算的, 所以在RecyclerView中假设你添加新的数据使用局部刷新函数(notifyItem*等函数)以后可能需要使用`rv.invalidateItemDecorations()`刷新分隔物, 否则可能会分割线宽高错乱

## 水平分割线

<img src="https://i.imgur.com/jXdKlpQ.png" width="250"/>

```kotlin
rv.grid(3).divider(R.drawable.divider_horizontal).setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```


## 垂直分割线

<img src="https://i.imgur.com/RInJ0qL.png" width="250"/>
```kotlin
rv.grid(3, RecyclerView.HORIZONTAL)
  .divider(R.drawable.divider_vertical, DividerOrientation.VERTICAL)
  .setup {
    addType<DividerModel>(R.layout.item_divider_vertical)
}.models = getData()
```

## 网格分割线

<img src="https://i.imgur.com/udblR3G.png" width="250"/>

```kotlin
rv.grid(3).divider {
    setDrawable(R.drawable.divider_horizontal)
    orientation = DividerOrientation.GRID
}.setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```

## 边缘分割线

通过两个字段可以控制边缘分割线是否显示

| 字段 | 描述 |
|-|-|
| [startVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#-2091559976%2FProperties%2F-900954490) | 是否显示上下边缘分割线 |
| [endVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#-377591023%2FProperties%2F-900954490) | 是否显示左右边缘分割线 |
| [includeVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#1716094302%2FProperties%2F-900954490) | 是否显示周围分割线 |

### 1) 上下

<img src="https://i.imgur.com/ujjSdw4.png" width="250"/>

```kotlin hl_lines="4"
rv.grid(3).divider {
    setDrawable(R.drawable.divider_horizontal)
    orientation = DividerOrientation.GRID
    startVisible = true
}.setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```


### 2) 左右

<img src="https://i.imgur.com/aCczxuD.png" width="250"/>

```kotlin hl_lines="4"
rv.grid(3).divider {
    setDrawable(R.drawable.divider_horizontal)
    orientation = DividerOrientation.GRID
    endVisible = true
}.setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```

### 3) 四周

<img src="https://i.imgur.com/d7H3Nz2.png" width="250"/>

```kotlin hl_lines="4 5"
rv.grid(3).divider {
    setDrawable(R.drawable.divider_horizontal)
    orientation = DividerOrientation.GRID
    startVisible = true
    endVisible = true
}.setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```