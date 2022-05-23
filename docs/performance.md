首先希望你已经阅读过rv基础的[生命周期](lifecycle.md)

> BRV并没有对RecyclerView存在卡顿影响, 这里介绍的都是RecyclerView的优化点, 未提及的请自行搜索或补充

性能优化主要是避免滑动列表时频繁耗时操作, 而onBind就是滑动列表过程中频繁触发的回调

## 主线程读写数据

SharePreference(简称sp)是Android自带的键值存储工具, 虽然允许方便地在主线程读写本地数据但是性能堪忧 <br>
如果你在onBind里面读取体积较大的sp会导致列表卡顿甚至引起应用ANR

网上可能大部分推荐使用MMKV来取代, 但我推荐基于MMKV封装的更高效/方便的[Serialze](https://github.com/liangjingkanji/Serialize)来取代

## 嵌套列表

在使用rv嵌套rv时应当在onCreate回调中为内嵌的rv设置视图(使用`rv.setup`), 这是为了避免同一类型反复创建rv导致内存消耗.  而嵌套的rv数据可以在onBind中绑定数据, 使用`rv.models`

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    onCreate {
        val rv = findView<RecyclerView>(R.id.rv_check_mode)
        rv.linear().setup { // 视图在onCreate可以避免列表滑动过程反复回调
            addType<NestedModel>(R.layout.item_simple_nested)
        }
    }

    onBind {
        val rv  = findView<RecyclerView>(R.id.rv_check_mode)
        rv.models = getModel<Model>().listNested // 只有onBind才能获取到数据
    }
}
```

由于被嵌套的rv是无法复用item, 所以建议使用recycledViewPool来复用, 具体请搜索关键词

如果被嵌套的列表非常简单其实也无需考虑其复用优化, 甚至你直接使用addView动态添加可能会比嵌套列表更简单

## 视图添加/删除

要避免频繁操作主线程耗时的视图addView/removeView等操作

1. 建议使用visibility控制视图显示或隐藏
1. 判断数据避免反复addView, 视图已经addView情况下赋值而不是remove
1. 如果只是添加图标建议使用[Spannable](https://github.com/liangjingkanji/spannable)构建图文混排直接赋值给TextView

## 高速滑动节流

实现原理很像自动搜索节流, 延迟到一定时间后item依然显示在屏幕之上才加载数据, 因为高速滑动列表可能大部分item只会停留在屏幕很短时间并不需要加载数据

```kotlin
data class SimpleModel(var name: String = "BRV") : ItemBind, ItemAttached {

    private var itemVisible: Boolean = false

    override fun onViewAttachedToWindow(holder: BindingAdapter.BindingViewHolder) {
        itemVisible = true
    }

    override fun onViewDetachedFromWindow(holder: BindingAdapter.BindingViewHolder) {
        itemVisible = false
    }

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        holder.itemView.postDelayed({
            if (itemVisible) {
                  // 500ms 以后依然可见才会触发加载
            }
        }, 500)
    }

}
```

## 固定布局优化

如果列表所有item的宽高不会因为适配器(Adapter)动态改变, 那么可以使用`setHasFixedSize(true)`来减少测绘次数提高性能


## 列表唯一标识

通过为item配置唯一ID提高列表使用`notifyDataSetChanged()`时的排序性能

```kotlin
binding.rv.linear().setup {
    setHasStableIds(true) // 启用唯一ID
    addType<UserModel>(R.layout.item_user)
}.models = getData()
```

数据模型实现`ItemStableId`

```kotlin
data class UserModel(var userId: Long) : ItemStableId {

    override fun getItemId(): Long {
        return userId // 返回列表中唯一ID
    }
}
```