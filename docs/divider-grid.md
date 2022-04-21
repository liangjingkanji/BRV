支持`GridLayoutManager`的网格布局的分割线


## 水平分割线

<img src="https://i.loli.net/2021/08/14/oyjdg42zDUbkFtu.png" width="250"/>

```kotlin
rv.grid(3).divider(R.drawable.divider_horizontal).setup {
    addType<DividerModel>(R.layout.item_divider_horizontal)
}.models = getData()
```


## 垂直分割线

<img src="https://i.loli.net/2021/08/14/ChG9ZnNiJyasWFr.png" width="250"/>

```kotlin
rv.grid(3, RecyclerView.HORIZONTAL)
  .divider(R.drawable.divider_vertical, DividerOrientation.VERTICAL)
  .setup {
    addType<DividerModel>(R.layout.item_divider_vertical)
}.models = getData()
```

## 网格分割线

<img src="https://i.loli.net/2021/08/14/NLAPphzIU6yvVnt.png" width="250"/>

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


### 2) 左右

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

### 3) 四周

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

## 网格悬停均布间隔

这里建议使用嵌套列表完成, 避免分割线出现问题. 这种需求比较常见所以演示实现思路

<img src="https://i.loli.net/2021/08/14/kCS4Kr9qpIfsveQ.gif" width="250"/>

```kotlin
binding.rv.linear().setup {
    onCreate {
        if (it == R.layout.item_simple_list) { // 构建嵌套网格列表
            findView<RecyclerView>(R.id.rv).divider { // 构建间距
                setDivider(20)
                includeVisible = true
                orientation = DividerOrientation.GRID
            }.grid(2).setup {
                addType<Model>(R.layout.item_multi_type_simple_none_margin)
            }
        }
    }
    onBind {
        if (itemViewType == R.layout.item_simple_list) { // 为嵌套的网格列表赋值数据
            findView<RecyclerView>(R.id.rv).models =
                getModel<NestedGroupModel>().itemSublist
        }
    }
    addType<NestedGroupModel>(R.layout.item_simple_list)
    addType<HoverHeaderModel>(R.layout.item_hover_header)
}.models = getData()
```

