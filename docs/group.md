
<p align="center"><img src="https://i.imgur.com/DQdEHQG.gif" width="250"/></p>

## 特点

- 展开/折叠
- 动画
- 递归展开/折叠
- 展开后置顶
- 列表始终仅展开一个分组
- 查找上层分组

## 使用
要求Model实现[ItemExpand](https://github.com/liangjingkanji/BRV/blob/master/brv/src/main/java/com/drake/brv/item/ItemExpand.kt)

```kotlin
class GroupModel : ItemExpand {
	// 同级别分组的索引位置
    override var itemGroupPosition: Int = 0

    // 当前条目是否展开
    override var itemExpand: Boolean = false

	// 该变量存储子列表
    override var itemSublist: List<Any?>? = listOf(Model(), Model(), Model(), Model())
}
```

> 当你要修改分组下的子项itemSublist时请使用类型强转将其转成可变集合后修改 <br>
> 例(itemModel.itemSublist as ArrayList).add或者remove等修改分组集合


创建列表

```kotlin
rv_group.linear().setup {
    // 任何条目都需要添加类型到BindingAdapter中
    addType<GroupModel>(R.layout.item_group_title)

    addFastClickable(R.id.item) // 展开和折叠点击事件不需要防抖动, 所以使用该函数
    
    onClick {
        expandOrCollapse() // 展开或者折叠
    }

}.models = getData()
```

## 分组相关函数

| BindingAdapter的函数 | 描述 |
|-|-|
| expandAnimationEnabled | 展开是否显示渐隐动画, 默认true |
| singleExpandMode | 是否只允许一个分组展开(即展开当前分组就折叠上个分组), 默认false |
| onExpand | 展开回调监听 |
| expand | 展开指定条目 |
| collapse | 折叠指定条目 |
| expandOrCollapse | 展开或者折叠指定条目(根据当前条目状态决定是折叠/展开) |
| isSameGroup | 指定两个索引是否处于相同分组 |


| BindingViewHolder的函数 | 描述 |
|-|-|
| expand | 展开指定条目 |
| collapse | 折叠指定条目 |
| expandOrCollapse | 展开或者折叠指定条目(根据当前条目状态决定是折叠/展开) |
| findParentPosition | 查找父项条目的索引(即当前条目属于哪个分组下), 如果没有返回-1 |
| findParentViewHolder | 查找父项条目ViewHolder, 如果没有返回null |