!!! question "RV如何更新列表"
    列表数据来源于集合, 任何更新方法都是基于下面的封装

    1. 操作集合
    2. 调用Adapter的`notifyXX`等方法通知RV更新视图


BindingAdapter/RV新增以下扩展函数操作数据

| 函数| 动画 | 描述  |
|-|-|-|
| models | 无动画 | 设置集合, 会`notifyDataChanged()` |
| setDifferModels | 有动画 | 设置集合, 使用`DiffUtil.calculateDiff`来决定`notifyXX()`更新视图 |
| addModels | 可选动画 | 添加/插入集合, 会`notifyDataChanged()` |
| _data | 无动画 | 对应列表的集合对象, 需手动通知更新 |


## 添加数据

```kotlin
binding.rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
}.models = getData()

binding.rv.addModels(data, index = 3) // 插入到第四个
binding.rv.models = newDataList // 覆盖列表
```
!!! failure "同一个集合对象反复添加"
    引用类型是传址, 反复添加同一个集合对象, 并不会更新列表, 这属于基本语法知识

## 删除数据

比如删除第3条

```kotlin
binding.rv.mutable.removeAt(2) // 删除数据
binding.rv.bindingAdapter.notifyItemRemoved(2) // 通知更新
// binding.rv.bindingAdapter.notifyDataChanged() 通知更新但无动画
```
批量删除
```kotlin
binding.rv.mutable.run {
    removeAt(1)
    removeAt(2)
    removeAt(3)
}
binding.rv.bindingAdapter.notifyItemRangeRemoved(1, 3) // 通知更新
// binding.rv.bindingAdapter.notifyDataChanged() 通知更新但无动画
```

## 对比刷新
新旧数据对比然后自动局部更新并展示对应动画

```kotlin
binding.rv.setDifferModels(getRandomData())
```

内部使用官方`DiffUtil`进行数据对比刷新, 并支持子线程, 自定义请实现`ItemDifferCallback`

```kotlin hl_lines="3"
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    itemDifferCallback = MyItemDifferCallback()
}.models = getRandomData(true)
```

- [白屏动画](https://github.com/liangjingkanji/BRV/blob/5269ef245e7f312a0077194611f1c2aded647a3c/brv/src/main/java/com/drake/brv/listener/ItemDifferCallback.kt#L63)
- [自定义对比条件](https://github.com/liangjingkanji/BRV/blob/5269ef245e7f312a0077194611f1c2aded647a3c/brv/src/main/java/com/drake/brv/listener/ItemDifferCallback.kt#L48)

## 局部刷新

指定刷新某个Item的数据

1. 使用`notifyXX()`方法
2. 使用DataBinding双向绑定特性, 数据变化会自动更新视图

!!! success "简单高效"
    DataBinding 零代码, 最小范围(高性能), 不考虑索引

    扩展阅读: [DataBinding最全使用说明 ](https://juejin.cn/post/6844903549223059463)

## 通知更新

`BindingAdapter`继承`RecyclerView.Adapter`, 拥有父类的通知更新方法

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

!!! warning  "更新异常"
    1. 以上方法是通知更新视图, 若列表数据未改变那么更新无效的 <br>
    2. 若更新item导致分隔线无效, 请使用`invalidateItemDecorations()`刷新