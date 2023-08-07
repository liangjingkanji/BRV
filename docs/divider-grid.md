## 水平分隔线

<img src="https://i.loli.net/2021/08/14/oyjdg42zDUbkFtu.png" width="250"/>

```kotlin
rv.grid(3).divider(R.drawable.divider_horizontal).setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```


## 垂直分隔线

<img src="https://i.loli.net/2021/08/14/ChG9ZnNiJyasWFr.png" width="250"/>

```kotlin
rv.grid(3, RecyclerView.HORIZONTAL)
  .divider(R.drawable.divider_vertical, DividerOrientation.VERTICAL)
  .setup {
    addType<DividerModel>(R.layout.item_divider_vertical)
}.models = getData()
```

## 网格分隔线

<img src="https://i.loli.net/2021/08/14/NLAPphzIU6yvVnt.png" width="250"/>

```kotlin
rv.grid(3).divider {
    setDrawable(R.drawable.divider_horizontal)
    orientation = DividerOrientation.GRID
}.setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```

## 边缘分隔线

通过两个字段可以控制边缘分隔线是否显示

| 字段 | 描述 |
|-|-|
| [startVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#-2091559976%2FProperties%2F-900954490) | 是否显示上下边缘分隔线 |
| [endVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#-377591023%2FProperties%2F-900954490) | 是否显示左右边缘分隔线 |
| [includeVisible](api/-b-r-v/com.drake.brv/-default-decoration/index.html#1716094302%2FProperties%2F-900954490) | 是否显示周围分隔线 |

### 上下

<img src="https://i.loli.net/2021/08/14/JBjETuMoaORFWHK.png" width="250"/>

```kotlin hl_lines="4"
rv.grid(3).divider {
    setDrawable(R.drawable.divider_horizontal)
    orientation = DividerOrientation.GRID
    startVisible = true
}.setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```


### 左右

<img src="https://i.loli.net/2021/08/14/IcxHsWafFQXh4Eg.png" width="250"/>

```kotlin hl_lines="4"
rv.grid(3).divider {
    setDrawable(R.drawable.divider_horizontal)
    orientation = DividerOrientation.GRID
    endVisible = true
}.setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```

### 四周

<img src="https://i.loli.net/2021/08/14/UmhH5BgFA3a1W2Q.png" width="250"/>

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

## 分隔线间隔

分隔线默认情况下是基于rv设置间隔

<img src="https://cdn.jsdelivr.net/gh/JBFiveHub/picture-storage@master/uPic/Clipboard - 2023-01-17 16.16.01.jpg" width="250"/>

```kotlin
binding.rv.grid(3, orientation = RecyclerView.VERTICAL).divider {
    orientation = DividerOrientation.GRID
    setDivider(1, true)
    setMargin(16, 16, dp = true)
    setColor(Color.WHITE)
}.setup {
    addType<DividerModel>(R.layout.item_divider_vertical)
}.models = getData()
```

<br>
使用`baseItemStart/baseItemEnd`参数以item为基准设置间隔

<img src="https://cdn.jsdelivr.net/gh/JBFiveHub/picture-storage@master/uPic/Clipboard - 2023-01-17 16.30.04.jpg" width="250"/>

<img src="https://cdn.jsdelivr.net/gh/JBFiveHub/picture-storage@master/uPic/Clipboard - 2023-01-17 16.33.04.jpg" width="250"/>

```kotlin
binding.rv.grid(3, orientation = RecyclerView.VERTICAL).divider {
    orientation = DividerOrientation.GRID
    setDivider(1, true)
    setMargin(16, 16, dp = true, baseItemStart = true)
    setColor(Color.WHITE)
}.setup {
    addType<DividerModel>(R.layout.item_divider_vertical)
}.models = getData()
```


## 网格悬停均布间隔

建议使用嵌套列表完成, 避免分隔线出现问题

<img src="https://i.loli.net/2021/08/14/kCS4Kr9qpIfsveQ.gif" width="250"/>

```kotlin
binding.rv.linear().setup {
    onCreate {
        if (itemViewType == R.layout.item_simple_list) { // 构建嵌套网格列表
            findView<RecyclerView>(R.id.rv).divider { // 构建间距
                setDivider(20)
                includeVisible = true
                orientation = DividerOrientation.GRID
            }.grid(2).setup {
                addType<Model>(R.layout.item_group_none_margin)
            }
        }
    }
    onBind {
        if (itemViewType == R.layout.item_simple_list) { // 为嵌套的网格列表赋值数据
            findView<RecyclerView>(R.id.rv).models =
                getModel<NestedGroupModel>().getItemSublist()
        }
    }
    addType<NestedGroupModel>(R.layout.item_simple_list)
    addType<HoverHeaderModel>(R.layout.item_hover_header)
}.models = getData()
```

