/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    get() = adapter as? BindingAdapter ?: throw NullPointerException("BindingAdapter is null")

var RecyclerView.models
    get() = bindingAdapter.models
    set(value) {
        bindingAdapter.models = value
    }

/**
 * 添加数据
 * @param models 被添加的数据
 * @param animation 添加数据是否显示动画
 */
fun RecyclerView.addModels(models: List<Any?>?, animation: Boolean = true) {
    bindingAdapter.addModels(models, animation)
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
    reverseLayout: Boolean = false,
    scrollEnabled: Boolean = true
): RecyclerView {
    layoutManager = HoverLinearLayoutManager(
        context,
        orientation,
        reverseLayout
    ).setScrollEnabled(scrollEnabled)
    return this
}

fun RecyclerView.grid(
    spanCount: Int = 1,
    @RecyclerView.Orientation orientation: Int = VERTICAL,
    reverseLayout: Boolean = false,
    scrollEnabled: Boolean = true
): RecyclerView {
    layoutManager =
        HoverGridLayoutManager(context, spanCount, orientation, reverseLayout).setScrollEnabled(
            scrollEnabled
        )
    return this
}

fun RecyclerView.staggered(
    spanCount: Int,
    @RecyclerView.Orientation orientation: Int = VERTICAL,
    scrollEnabled: Boolean = true
): RecyclerView {
    layoutManager =
        HoverStaggeredGridLayoutManager(spanCount, orientation).setScrollEnabled(scrollEnabled)
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
        setDivider(width)
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
        setDivider(width)
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
        setDivider(width)
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



