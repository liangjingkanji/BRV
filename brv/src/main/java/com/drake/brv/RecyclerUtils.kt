/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv

import android.graphics.Rect
import android.util.NoSuchPropertyException
import android.view.View
import android.view.View.NO_ID
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/**
 * 详细设置适配器
 * @receiver RecyclerView
 * @param block BindingAdapter.() -> Unit
 */
fun RecyclerView.setup(block: BindingAdapter.(RecyclerView) -> Unit): BindingAdapter {
    val adapter = BindingAdapter()
    adapter.block(this)
    this.adapter = adapter
    return adapter
}

/**
 * 快速创建多类型
 * itemLayout和block二者选一, 分别对应 单一类型/一对多数据类型,
 * 普通多类型配置请使用 {@link RecyclerView.mAdapter(block: BindingAdapter.() -> Unit): BindingAdapter}
 *
 * @receiver RecyclerView
 * @param itemLayout Int
 * @param block (M.(Int) -> Int)?
 * @return BindingAdapter
 */
inline fun <reified M> RecyclerView.setup(@LayoutRes itemLayout: Int = NO_ID, noinline block: (M.(Int) -> Int)? = null): BindingAdapter {
    val adapter = BindingAdapter()
    when {
        itemLayout != NO_ID -> adapter.addType<M>(itemLayout)
        block != null -> adapter.addType(block)
        else -> throw NoSuchPropertyException("Please add item model type")
    }
    this.adapter = adapter
    return adapter
}

val RecyclerView.BindingAdapter: BindingAdapter
    get() = adapter as BindingAdapter

fun RecyclerView.linear(
    @RecyclerView.Orientation orientation: Int = VERTICAL,
    reverseLayout: Boolean = false
): RecyclerView {
    layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
    return this
}

fun RecyclerView.grid(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = VERTICAL,
    reverseLayout: Boolean = false
): RecyclerView {
    layoutManager = GridLayoutManager(context, spanCount, orientation, reverseLayout)
    return this
}

fun RecyclerView.staggered(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = VERTICAL
): RecyclerView {
    layoutManager =
        StaggeredGridLayoutManager(spanCount, orientation) as RecyclerView.LayoutManager?
    return this
}

fun RecyclerView.divider(
    @DrawableRes drawable: Int,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    block: ((Rect, View, RecyclerView, RecyclerView.State) -> Boolean)? = null
): RecyclerView {
    val decoration = DefaultDecoration(context).apply {
        setDrawable(drawable)
    }
    block?.let {
        decoration.onItemOffsets(block)
    }
    addItemDecoration(decoration)
    return this
}



