<p align="center"><img src="https://i.loli.net/2021/08/14/Pl53LCpG8tdhuMW.gif" width="250"/></p>

## 特点

- 展开/折叠
- 动画
- 递归展开/折叠
- 展开后置顶
- 列表始终仅展开一个分组
- 查找上层分组
- 分组和多类型可以共存

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

> 当你要修改子项itemSublist时请使用类型强转将其转成可变集合后修改 <br>
> 例(itemModel.itemSublist as ArrayList).add或者remove等修改分组集合


??? summary "如果你使用Gson解析产生该Model请展开阅读"
    如果该数据模型是由Gson生成那么其字段默认值全部会被置为null, 这是由于Gson不支持Kotlin的默认值问题

    处理方案分为两种
    
    1. 重写set/get函数
    
        ```kotlin
        class GroupModel(var finalList: List<Model>) {
    
            override var itemSublist: List<Any?>?
                get() = finalList
                set(value) {
                    finalList = value as? List<Model>
                }
        }
        ```
    
    2. 等Gson解析完成返回数据后手动赋值
    
        ```kotlin
        val model = Post<GroupModel>()
        model.itemSublist = realList
        ```



创建列表

```kotlin
rv.linear().setup {
    // 任何条目都需要添加类型到BindingAdapter中
    addType<GroupModel>(R.layout.item_group_title)

    addFastClickable(R.id.item) // 展开和折叠点击事件不需要防抖动, 所以使用该函数
    
    onClick {
        expandOrCollapse() // 展开或者折叠
    }

}.models = getData()
```

## 分组层级

可以使用`ItemDepth`和`List.refreshItemDepth`辅助计算Item的层级
其中`ItemDepth.itemDepth`为当前Model层级，层级计数从0依次递增

示例代码

```kotlin
// Model实现ItemDepth
class SampleItemDepth(override var itemDepth: Int) : ItemDepth

// 在数据赋值给[BindingAdapter]前，刷新一次item层级即可
fun getData(): List<ItemDepth> = List(10) { SampleItemDepth(it) }
RecyclerView(TODO()).linear().setup {
   // ...
}.models = getData().refreshItemDepth()
}
```

## 分组多类型

<img src="https://s2.loli.net/2021/12/10/wo1CAqL5SDIZRKu.png" width="35%"/>

这种添加`spanSizeLookup`即可实现. 请查看示例代码

> 分组和多类型属于互不影响的功能, 分组下的多类型和普通列表的多类型添加方式等同

## 分组拖拽/侧滑
<img src="https://s2.loli.net/2021/12/14/RSpGEF2DWyqPb5J.gif" width="30%"/>

[拖拽](drag.md)/[侧滑](swipe.md)功能和分组本身互不影响. 但是针对已展开的分组需要在动作发生之前折叠以保证列表数据不错乱, 所以我们需要自定义部分实现

```kotlin
binding.rv.linear().setup {

    // 自定义部分实现
    itemTouchHelper = ItemTouchHelper(object : DefaultItemTouchCallback() {
        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) { // 拖拽移动分组前先折叠子列表
                (viewHolder as BindingAdapter.BindingViewHolder).collapse()
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            (viewHolder as BindingAdapter.BindingViewHolder).collapse() // 侧滑删除分组前先折叠子列表
            super.onSwiped(viewHolder, direction)
        }
    })

    // ...
}.models = getData()
```
具体可以看完整示例代码

## 分组全部展开/折叠

遍历集合数据将`itemExpand = true`即可展开全部(反之折叠). 如果要控制展开层级深度请自己遍历时控制

展开全部
```kotlin
binding.rv.bindingAdapter.models = getData().forEach {
                                it.itemExpand = true
                            }
```
折叠全部
```kotlin
binding.rv.bindingAdapter.models = getData().forEach {
                                it.itemExpand = false
                            }
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