<figure markdown>
  ![](https://i.loli.net/2021/08/14/Pl53LCpG8tdhuMW.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef245e7f312a0077194611f1c2aded647a3c/sample/src/main/java/com/drake/brv/sample/ui/fragment/group/GroupFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

## 特点

- 展开/折叠
- 动画
- 递归展开/折叠
- 展开后置顶
- 列表始终仅展开一个分组
- 查找上层分组
- 分组和多类型可以共存


!!! note "实现原理"
    列表展开/折叠是通过修改models数据集合实现, 会导致列表元素索引变化(可变集合情况下)

    - 展开(将子列表添加到数据集合中)
    - 折叠(将子列表从数据集合中删除)

## 使用
Model实现[ItemExpand](https://github.com/liangjingkanji/BRV/blob/master/brv/src/main/java/com/drake/brv/item/ItemExpand.kt)

```kotlin
class GroupModel : ItemExpand {
	// 同级别分组的索引位置
    override var itemGroupPosition: Int = 0

    // 当前条目是否展开
    override var itemExpand: Boolean = false

	// 返回子列表
    override fun getItemSublist(): List<Any?>? {
        return sublist
    }

    var sublist: List<Any?> = List(10) { Model() }
}
```

创建列表

```kotlin
rv.linear().setup {
    addType<GroupModel>(R.layout.item_group_title)

    R.id.item.onClick {
        expandOrCollapse() // 展开或者折叠
    }

}.models = getData()
```

!!! failure "数据错乱"
    不要为数据集合重复添加同一对象, 这可能导致数据错乱

## 分组层级

根据数据集合可以计算出当前列表位于的分组层级

BRV已提供`ItemDepth`辅助计算Item的层级, 其中`ItemDepth.itemDepth`为当前Model分组层级, 层级计数从0依次递增

示例代码

```kotlin hl_lines="9"
// Model实现ItemDepth
class SampleItemDepth(override var itemDepth: Int) : ItemDepth

// 构建示例数据
fun getData(): List<ItemDepth> = List(10) { SampleItemDepth(it) }

rv.linear().setup {
   // ...
}.models = ItemDepth.refreshItemDepth(getData())
```

## 分组多类型

<figure markdown>
  ![](https://s2.loli.net/2021/12/10/wo1CAqL5SDIZRKu.png){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef245e7f312a0077194611f1c2aded647a3c/sample/src/main/java/com/drake/brv/sample/ui/fragment/group/GroupGridFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

自定义`GridLayoutManager.spanSizeLookup`即可, 多类型和分组不存在影响

## 分组拖拽/侧滑
<figure markdown>
  ![](https://s2.loli.net/2021/12/14/RSpGEF2DWyqPb5J.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef245e7f312a0077194611f1c2aded647a3c/sample/src/main/java/com/drake/brv/sample/ui/fragment/group/GroupDragFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

[拖拽](drag.md)/[侧滑](swipe.md)功能和分组原本互不影响, 但删除/移动已展开的分组未同步他的子列表会导致数据错乱, 需要以下处理

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

            // 如果侧滑删除的是分组里面的子列表, 要删除对应父分组的itemSublist数据, 否则会导致数据异常
            // itemSublist必须为可变集合, 否则无法被删除
            (vh.findParentViewHolder()?.getModelOrNull<ItemExpand>()?.getItemSublist() as? ArrayList)?.remove(vh.getModelOrNull())
        }
    })

    // ...
}.models = getData()
```

## 分组全部展开/折叠

遍历集合`itemExpand=true`即可展开全部(反之折叠), 如果要控制展开层级深度请自己遍历时控制

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


## 嵌套分组删除

删除分组时请同步删除他的子列表, 避免数据错乱

```kotlin
// 点击删除嵌套分组
val model = getModel<GroupBasicModel>()
val parentPosition = findParentPosition()
if (parentPosition != -1) {
    // 删除父item的嵌套分组数据
    (getModel<ItemExpand>(parentPosition).getItemSublist() as MutableList).remove(model)

    // 正常删除item
    mutable.removeAt(layoutPosition)
    notifyItemRemoved(layoutPosition)
}
```


## 分组相关函数

| BindingAdapter | 描述 |
|-|-|
| expandAnimationEnabled | 展开是否显示渐隐动画, 默认true |
| singleExpandMode | 是否只允许一个分组展开(即展开当前分组就折叠上个分组), 默认false |
| onExpand | 展开回调监听 |
| expand | 展开指定条目 |
| collapse | 折叠指定条目 |
| expandOrCollapse | 展开或者折叠指定条目(根据当前条目状态决定是折叠/展开) |
| isSameGroup | 指定两个索引是否处于相同分组 |


| BindingViewHolder | 描述 |
|-|-|
| expand | 展开指定条目 |
| collapse | 折叠指定条目 |
| expandOrCollapse | 展开或者折叠指定条目(根据当前条目状态决定是折叠/展开) |
| findParentPosition | 查找父项条目的索引(即当前条目属于哪个分组下), 如果没有返回-1 |
| findParentViewHolder | 查找父项条目ViewHolder, null表示不存在父项或没有显示在屏幕中 |