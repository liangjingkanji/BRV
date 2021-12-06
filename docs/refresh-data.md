这里介绍的其实都不属于BRV的内容, 但是由于很多开发者常问此需求, 故介绍下

> BRV的数据集合无论是`models`或`addData()`都是添加的`List<Any?>(任意对象数据集合)`. 所以只要是一个集合即可映射出一个列表 <br>
> 如果数据不满足一个集合条件(或任何数据上的问题), 请自己处理下数据


## 添加数据

使用BRV自带的两个函数添加数据会自动更新UI

```kotlin
rv.models = dataList // 自动使用 notifyDataSetChanged
rv.addModels(newDataList)
```

代码示例
```kotlin
binding.rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    onBind {
        findView<TextView>(R.id.tv_simple).text = getModel<SimpleModel>().name
    }
}.models = getData()
```


> 切记Java/Kotlin的引用类型是传址, 如果这两个函数操作的数据集都是同一个会导致问题 - 自己添加自己.  这种属于基本的语法问题

## 对比数据刷新
BRV可以根据新的数据集合和旧的数据集合对比判断来自动使用刷新动画

```kotlin
rv.setDifferModels(getRandomData())
```

该函数内部使用Android自带工具`DiffUtil`进行数据对比刷新, 但是支持异步/同步线程对比
```kotlin
fun setDifferModels(newModels: List<Any?>?, detectMoves: Boolean = true, commitCallback: Runnable? = null)
```
> 数据对比默认使用`equals`函数对比, 你可以为数据手动实现equals函数来修改对比逻辑. 推荐定义数据为 data class, 因其会根据构造参数自动生成equals

如果需要完全自定义对比数据的判断逻辑就实现`ItemDifferCallback`接口

```kotlin hl_lines="3"
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    itemDifferCallback = object : ItemDifferCallback {
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is SimpleModel && newItem is SimpleModel) {
                oldItem.name == newItem.name
            } else super.areContentsTheSame(oldItem, newItem)
        }
    }
    // ...
}.models = getRandomData(true)
```

## 局部刷新

局部刷新某个或者批量Item的内容, 我们可以使用到两种方式

1. `notifyItemChanged`等函数, 这个上面提过
2. DataBinding本身就支持这个特性 (推荐此方法), 性能最高/方便. Demo中的[选择模式](https://github.com/liangjingkanji/BRV/blob/master/sample/src/main/java/com/drake/brv/sample/ui/fragment/CheckModeFragment.kt)就是如此实现

<br>
BRV支持DataBinding绑定数据, DataBinding的数据模型如果继承Observable就可以自动更新UI

```kotlin
data class CheckModel(var checked: Boolean = false, var visibility: Boolean = false) : BaseObservable()
```

可以自动更新UI的字段类型, 这样可以不用整个数据模型继承BaseObservable

- LiveData
- ObservableField

> 以上属于DataBinding使用基础, 具体请阅读: [DataBinding最全使用说明 ](https://juejin.cn/post/6844903549223059463)

## 刷新函数

BRV使用的是自定义的BindingAdapter, 其继承自`RecyclerView.Adapter`, 拥有其全部的数据刷新方法

```kotlin
class BindingAdapter : RecyclerView.Adapter<BindingAdapter.BindingViewHolder>()
```

RecyclerView.Adapter场景的数据刷新方法有

| 函数 | 描述 |
|-|-|
| notifyDataSetChanged | 全部数据刷新(不带动画) |
| notifyItemChanged | 局部数据变更 |
| notifyItemInserted | Item插入 |
| notifyItemMoved | Item移动位置 |
| notifyItemRemoved | Item删除 |
| notifyItemRangeChanged | 指定范围内Item数据变更 |
| notifyItemRangeInserted | 指定范围内Item插入 |
| notifyItemRangeRemoved | 指定范围内删除 |

以上函数BRV全部支持, 具体使用请搜索: `RecycleView局部刷新`

```kotlin
rv_simple.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
}.models = dataList

dataList.add(SimpleModel())

rv_simple.bindingAdapter.notifyItemInserted(dataList.size) // 最后的位置有插入一个新的Item
```
