
BRV没有自定义RecyclerView, 所以RV如何操作数据BRV就如何操作数据. 对于RV基础不了解的可以阅读本章后网上搜索

你要知道的是RV操作数据需要两个步骤

1. 更新集合(删除或者添加元素)
2. 调用对应的notify**()方法更新列表

但是BRV的`models/addData`赋值会自动notifyDataChanged(), 无需调用更新列表. 如果不想自动更新列表可以使用BindingAdapter的`_data`字段

> BRV的数据集合无论是`models`或`addData()`都是添加的`List<Any?>(任意对象数据集合)`. 所以只要是一个集合即可映射出一个列表 <br>
> 如果数据不满足一个集合条件(或任何数据上的问题), 请自己处理下数据


## 添加数据

使用BRV自带的两个方法添加数据会自动刷新UI

```kotlin
rv.models = dataList // 自动使用 notifyDataSetChanged
rv.addModels(newDataList) // 自动使用 notifyItemRangeInserted, 当然也可以禁止动画
binding.rv.addModels(newDataList, index = 3) // 在索引3后面添加数据
```

代码示例
```kotlin
binding.rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    onBind {
        findView<TextView>(R.id.tv_simple).text = getModel<SimpleModel>().name
    }
}.models = getData()

binding.rv.addModels(data, index = 3) // 添加数据
binding.rv.models = newDataList // 覆盖原来的列表
```


> 切记Java/Kotlin的引用类型是传址, 如果这两个方法操作的数据集都是同一个会导致问题 - 自己添加自己.  这种属于基本的语法问题

## 删除数据

BRV操作数据和RV没有任何区别(毕竟只是自定义Adapter), 删除全部数据可以为models赋值null

如果删除指定数据, 比如删除第二条

```kotlin
rv.mutable.removeAt(2) // 先删除数据
rv.bindingAdapter.notifyItemRemoved(2) // 然后刷新列表
```

如果你删除N条或者添加/插入N条, 请调用对应的`notifyItem**()`方法, 具体方法你可以查看本章末尾或者网上搜索

## 对比数据刷新
BRV可以根据新的数据集合和旧的数据集合对比判断来自动使用刷新动画

```kotlin
rv.setDifferModels(getRandomData())
```

该方法内部使用Android自带工具`DiffUtil`进行数据对比刷新, 但是支持异步/同步线程对比
```kotlin
fun setDifferModels(newModels: List<Any?>?, detectMoves: Boolean = true, commitCallback: Runnable? = null)
```
> 数据对比默认使用`equals`方法对比, 你可以为数据手动实现equals方法来修改对比逻辑. 推荐定义数据为 data class, 因其会根据构造参数自动生成equals

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

使用`setDifferModels`对比刷新时, 相同item刷新有白屏动画这是因为`getChangePayload`返回null, 随便返回一个对象即可关闭

```kotlin
rv.linear().setup {
    // ...
    itemDifferCallback = object : ItemDifferCallback {
        override fun getChangePayload(oldItem: Any, newItem: Any): Any? {
            return true
        }
    }
}.models = getRandomData(true)
```

## 局部刷新

局部刷新某个或者批量Item的内容, 我们可以使用到两种方式

1. `notifyItemChanged`等方法, 这个上面提过
2. DataBinding本身就支持这个特性 (推荐此方法), 性能最高/方便. Demo中的[选择模式](https://github.com/liangjingkanji/BRV/blob/master/sample/src/main/java/com/drake/brv/sample/ui/fragment/CheckModeFragment.kt)就是如此实现

<br>
BRV支持DataBinding绑定数据, DataBinding的数据模型如果继承Observable就可以自动更新UI

```kotlin
data class CheckModel(var checked: Boolean = false, var visibility: Boolean = false) : BaseObservable()
```

可以自动更新UI的字段类型, 这样可以不用整个数据模型继承BaseObservable

- LiveData
- ObservableField

自动更新LiveData字段要求先为DataBinding配置生命周期, 因为liveData观察者需要lifecycleOwner
```kotlin
binding.lifecycleOwner = this
```

> 以上属于DataBinding使用基础, 更多DataBinding使用方法请阅读: [DataBinding最全使用说明 ](https://juejin.cn/post/6844903549223059463)

## 刷新方法

这里介绍的属于RecyclerView官方方法, BRV的`BindingAdapter`继承`RecyclerView.Adapter`, 自然拥有父类的数据刷新方法.
由于很多开发者常问此需求, 故统一介绍下

```kotlin
class BindingAdapter : RecyclerView.Adapter<BindingAdapter.BindingViewHolder>()
```

| 刷新方法 | 描述 |
|-|-|
| notifyDataSetChanged | 全部数据刷新(不带动画) |
| notifyItemChanged | 局部数据变更 |
| notifyItemInserted | Item插入 |
| notifyItemMoved | Item移动位置 |
| notifyItemRemoved | Item删除 |
| notifyItemRangeChanged | 指定范围内Item数据变更 |
| notifyItemRangeInserted | 指定范围内Item插入 |
| notifyItemRangeRemoved | 指定范围内删除 |

具体区别可以搜索: `RecycleView局部刷新`<br>
要注意的是这些方法都是刷新UI, 如果你列表数据并没有发生改变那么刷新是无效的

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
}.models = dataList

dataList.add(SimpleModel())

rv.bindingAdapter.notifyItemInserted(dataList.size) // 最后的位置有插入一个新的Item
```
