/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：5/5/20 9:12 PM
 */

package com.drake.brv.utils

import android.app.Dialog
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.drake.brv.BindingAdapter
import com.drake.brv.DefaultDecoration
import com.drake.brv.layoutmanager.HoverGridLayoutManager
import com.drake.brv.layoutmanager.HoverLinearLayoutManager
import com.drake.brv.layoutmanager.HoverStaggeredGridLayoutManager


val RecyclerView.bindingAdapter
    get() = adapter as BindingAdapter

var RecyclerView.models
    get() = bindingAdapter.models
    set(value) {
        bindingAdapter.models = value
    }


//<editor-fold desc="配置列表">
/**
 * 设置适配器
 */
fun RecyclerView.setup(block: BindingAdapter.(RecyclerView) -> Unit): BindingAdapter {
    val adapter = BindingAdapter()
    adapter.block(this)
    this.adapter = adapter
    return adapter
}
//</editor-fold>


//<editor-fold desc="布局管理器">
fun RecyclerView.linear(
    @RecyclerView.Orientation orientation: Int = VERTICAL,
    reverseLayout: Boolean = false
): RecyclerView {
    layoutManager = HoverLinearLayoutManager(context, orientation, reverseLayout)
    return this
}

fun RecyclerView.grid(
    spanCount: Int = 1,
    @RecyclerView.Orientation orientation: Int = VERTICAL,
    reverseLayout: Boolean = false
): RecyclerView {
    layoutManager = HoverGridLayoutManager(context, spanCount, orientation, reverseLayout)
    return this
}

fun RecyclerView.staggered(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = VERTICAL
): RecyclerView {
    layoutManager = HoverStaggeredGridLayoutManager(spanCount, orientation)
    return this
}
//</editor-fold>

//<editor-fold desc="分割线">

/**
 * 函数配置分割线
 */
fun RecyclerView.divider(
    block: DefaultDecoration.() -> Unit
): RecyclerView {
    val itemDecoration = DefaultDecoration(context).apply(block)
    addItemDecoration(itemDecoration)
    return this
}

/**
 * 指定Drawable资源为分割线, 分割线的间距和宽度应在资源文件中配置
 */
fun RecyclerView.divider(
    @DrawableRes drawable: Int
): RecyclerView {
    return divider {
        setDrawable(drawable)
    }
}

/**
 * 指定颜色为分割线
 * @param color 颜色
 * @param marginStart 左或上间距
 * @param marginEnd 右或下间距
 * @param width 分割线宽度
 */
fun RecyclerView.dividerColor(
    @ColorInt color: Int,
    marginStart: Int = 0,
    marginEnd: Int = 0,
    width: Int = 1
): RecyclerView {
    return divider {
        setColor(color)
        setMargin(marginStart, marginEnd)
        setWith(width)
    }
}

fun RecyclerView.dividerColor(
    color: String,
    marginStart: Int = 0,
    marginEnd: Int = 0,
    width: Int = 1
): RecyclerView {
    return divider {
        setColor(color)
        setMargin(marginStart, marginEnd)
        setWith(width)
    }
}

fun RecyclerView.dividerColorRes(
    @ColorRes color: Int,
    marginStart: Int = 0,
    marginEnd: Int = 0,
    width: Int = 1
): RecyclerView {
    return divider {
        setColorRes(color)
        setMargin(marginStart, marginEnd)
        setWith(width)
    }
}
//</editor-fold>


//<editor-fold desc="对话框">
/**
 *  对话框设置一个列表组件
 */
fun Dialog.brv(block: BindingAdapter.(RecyclerView) -> Unit): Dialog {
    val context = context
    val recyclerView = RecyclerView(context)
    recyclerView.setup(block)
    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, MATCH_PARENT)
    setContentView(recyclerView)
    return this
}
//</editor-fold>



