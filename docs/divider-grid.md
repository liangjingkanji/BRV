支持`GridLayoutManager`的网格布局的分割线

## 水平分割线

<img src="https://i.imgur.com/jXdKlpQ.png" width="40%"/>

```kotlin
rv.grid(3).divider(R.drawable.divider_horizontal).setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```


## 垂直分割线

<img src="https://i.imgur.com/RInJ0qL.png" width="40%"/>
```kotlin
rv.grid(3, RecyclerView.HORIZONTAL)
  .divider(R.drawable.divider_vertical, DividerOrientation.VERTICAL)
  .setup {
    addType<DividerModel>(R.layout.item_divider_vertical)
}.models = getData()
```

## 网格分割线

<img src="https://i.imgur.com/udblR3G.png" width="40%"/>

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
| [startVisible](api/brv/com.drake.brv/-default-decoration/start-visible.md) | 是否显示上下边缘分割线 |
| [endVisible](api/brv/com.drake.brv/-default-decoration/end-visible.md) | 是否显示左右边缘分割线 |
| [includeVisible](api/brv/com.drake.brv/-default-decoration/include-visible.md) | 是否显示周围分割线 |

### 1) 上下

<img src="https://i.imgur.com/ujjSdw4.png" width="40%"/>

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

<img src="https://i.imgur.com/aCczxuD.png" width="40%"/>

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

<img src="https://i.imgur.com/d7H3Nz2.png" width="40%"/>

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