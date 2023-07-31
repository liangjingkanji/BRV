!!! warning "前言"
    介绍的是RV卡顿优化, 和有没有使用BRV没有关系, 未提及或有误的欢迎补充

首先请了解RV的[生命周期](lifecycle.md), 正常情况下不使用优化方案列表也不会卡顿

## 滑动列表耗时

!!! question "滑动卡顿"
    卡顿优化重点是避免滑动列表时耗时, 应减少在onBind中耗时行为

 `SharedPreferences`(简称sp)在读取数据相对耗时, 建议替换为[Serialze](https://github.com/liangjingkanji/Serialize)

## 嵌套列表

RV嵌套时尽量在`onCreate`中创建`Adapter`, 因为`onBind`在滑动列表时会反复创建耗时

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

由于是两个RV, 即使存在相同类型也无法相互复用, 但可指定同一个`recycledViewPool`来复用视图

## 视图添加/删除

一些简单列表不使用RV性能更好

1. 控制`visibility`视图显示或隐藏
2. 使用[Spannable](https://github.com/liangjingkanji/spannable)构建图文列表, 简单高性能

## 高速滑动节流

!!! question "节流"
    在一定时间间隔内，只执行一次请求, 忽略其他多余的请求

滑动列表中, 当指定条目停留在屏幕一定时间, 才允许他加载图片

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

如果使用的Glide还可以阅读文章: [Glide + RV](https://muyangmin.github.io/glide-docs-cn/int/recyclerview.html)

## 固定布局优化

如果条目宽高不会因Adapter动态改变, 那么可以使用`setHasFixedSize(true)`来减少测绘次数提高性能


## 列表唯一标识

为条目指定唯一Id可提高调用`notifyDataSetChanged()`时排序性能

```kotlin
binding.rv.linear().setup {
    setHasStableIds(true) // 启用唯一ID
    addType<UserModel>(R.layout.item_user)
}.models = getData()
```

Model实现`ItemStableId`

```kotlin
data class UserModel(var userId: Long) : ItemStableId {

    override fun getItemId(): Long {
        return userId // 返回列表中唯一ID
    }
}
```