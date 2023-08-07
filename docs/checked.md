<figure markdown>
  ![](https://i.loli.net/2021/08/14/MIe74pdKf5c1hTX.gif){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef245e7f312a0077194611f1c2aded647a3c/sample/src/main/java/com/drake/brv/sample/ui/fragment/CheckModeFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

BRV可快速实现支持单选/多选的[选择模式](https://github.com/liangjingkanji/BRV/blob/master/sample/src/main/java/com/drake/brv/sample/ui/fragment/CheckModeFragment.kt)

## 多选列表

1. 创建列表
    ```kotlin
    rv.linear().setup {
        addType<CheckModel>(R.layout.item_check_mode)
    }.models = getData
    ```

2. 为Model创建一个字段用于保存选择的状态
    ```kotlin hl_lines="2"
    data class CheckModel(
        var isChecked: Boolean = false,
        var visibility: Boolean = false
    ) : BaseObservable() // 支持DataBinding数据绑定
    ```

3. 监听选择事件
    ```kotlin hl_lines="3"
    rv.linear().setup {
       // ...
       onChecked { position, isChecked, isAllChecked ->
            val model = getModel<CheckModel>(position)
            model.isChecked = isChecked
            model.notifyChange() // 通知UI跟随数据变化
       }
    }.models = getData
    ```

4. 触发选择事件
    ```kotlin hl_lines="2"
    val isChecked = getModel<CheckModel>().isChecked
    setChecked(adapterPosition, !isChecked) // 在点击事件中触发选择事件, 即点击列表条目就选中
    ```


<br>

## 默认选择

首次加载列表默认选中指定Item, 应调用`setChecked`而不是将Model中某个属性置为true

```kotlin
rv.bindingAdapter.setChecked(0, true) // 一开始就选中第一个
```

!!! question "原因"
    为保持扩展性选中属性由开发者定义, 实际上BRV并不知道什么属性决定的选中状态

    1. `checkedXX()`函数通知BRV更新选中索引
    2. `onChecked()`通知开发者选中状态更新

## 数据变化
如果列表发生插入/覆盖数据集行为, 那么集合位置/数量和BRV内部保存的选中索引`checkedPosition`可能对不上

网络请求刷新列表场景

    建议由开发者保存选中索引+业务Id, 如果新的数据集存在之前已选中Id进行选中状态恢复

## 相关函数

| 函数 | 描述 |
|-|-|
| checkedAll | 全选/全取消 |
| singleMode | 单选模式 |
| isCheckedAll | 当前是否为全选 |
| checkedReverse | 反选 |
| setChecked | 选中指定列表 |
| checkedSwitch | 切换选中状态 |
| setCheckableType | 指定条目类型才允许选中 |
| checkedPosition | 被选中条目的的索引集合 |
| checkedCount | 已选中数量 |
| onChecked | 选中回调 |
