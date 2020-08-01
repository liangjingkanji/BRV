

### 使用

分组要求Model实现[ItemExpand](https://github.com/liangjingkanji/BRV/blob/master/brv/src/main/java/com/drake/brv/item/ItemExpand.kt)

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

可能你的数据结构不存在



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





### 函数

[BindingAdapter](https://github.com/liangjingkanji/BRV/blob/master/brv/src/main/java/com/drake/brv/BindingAdapter.kt)控制展开和折叠

```kotlin
var expandAnimationEnabled = true
// 展开折叠是否存在动画

var singleExpandMode = false
// 是否只允许一个分组展开(即展开当前分组就折叠上个分组)

fun onExpand(block: BindingViewHolder.(Boolean) -> Unit)
// 展开回调监听

fun expand(
        @IntRange(from = 0) position: Int, // 指定条目位置
        scrollTop: Boolean = false, // 展开后是否在列表中移动置顶当前条目
        @IntRange(from = -1) depth: Int = 0 // 递归级别, -1 表示展开当前条目的所有子列表
    ): Int
// 展开指定条目

fun collapse(@IntRange(from = 0) position: Int, @IntRange(from = -1) depth: Int = 0): Int
// 折叠指定条目

fun expandOrCollapse(
        @IntRange(from = 0) position: Int,
        scrollTop: Boolean = false,
        @IntRange(from = -1) depth: Int = 0
    ): Int
// 展开或者折叠指定条目(根据当前条目状态决定是折叠/展开)
```

