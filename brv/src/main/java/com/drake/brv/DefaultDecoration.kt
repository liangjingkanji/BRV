/*
 * MIT License
 *
 * Copyright (c) 2023 劉強東 https://github.com/liangjingkanji
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.drake.brv

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.drake.brv.DefaultDecoration.Edge.Companion.computeEdge
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.item.ItemExpand
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * 最强大的分割线工具
 *
 * 1. 分隔图片
 * 2. 分隔颜色
 * 3. 分隔间距
 * 4. 回调函数判断间隔
 * 5. 首尾是否显示分隔线, 可以展示表格效果
 * 6. 类型池来指定是否显示分割线
 * 7. 支持全部的LayoutManager, 竖向/横向/网格分割线
 * 8. 优于其他框架, 完美支持均布网格分隔物
 * 9. 支持分组条目的分割线
 */
class DefaultDecoration constructor(private val context: Context) : RecyclerView.ItemDecoration() {

    /**
     * 根据[orientation]值不同
     * [DividerOrientation.VERTICAL] 为是否显示顶部分割线
     * [DividerOrientation.HORIZONTAL] 为是否显示左侧分割线
     * [DividerOrientation.GRID] 为最上/下侧是否显示分割线
     */
    var startVisible = false

    /**
     * 根据[orientation]值不同
     * [DividerOrientation.VERTICAL] 为是否显示底部分割线
     * [DividerOrientation.HORIZONTAL] 为是否显示右侧分割线
     * [DividerOrientation.GRID] 为最左/右侧是否显示分割线
     */
    var endVisible = false

    /**
     * 列表四周显示分割线
     * 等于[startVisible]和[endVisible]全为true
     */
    var includeVisible
        get() = startVisible && endVisible
        set(value) {
            startVisible = value
            endVisible = value
        }

    /**
     * 展开分组条目后该条目是否显示分割线
     * 控制[ItemExpand.itemExpand]为true的情况下是否显示分割线, 但当你配置[onEnabled]后则无效, 因为此字段为其默认实现所用
     */
    var expandVisible = false

    /**
     * 分割线的方向, 仅支持[GridLayoutManager], 其他LayoutManager都是根据其方向自动适应
     * 布局管理器为[LinearLayoutManager] 和 [StaggeredGridLayoutManager] 时，该值会通过[adjustOrientation]自动调整
     */
    var orientation = DividerOrientation.HORIZONTAL

    /**
     * 列表是否被反转
     */
    private val RecyclerView.LayoutManager.isReverseLayout: Boolean
        get() = when (this) {
            is LinearLayoutManager -> reverseLayout
            is StaggeredGridLayoutManager -> reverseLayout
            else -> false
        }

    private var size = 1
    private var marginStart = 0
    private var marginEnd = 0
    private var marginBaseItemStart = false
    private var marginBaseItemEnd = false
    private var divider: Drawable? = null

    //<editor-fold desc="类型">

    /** 集合内包含的类型才显示分割线 */
    var typePool: MutableList<Int>? = null

    private var onEnabled: (BindingAdapter.BindingViewHolder.() -> Boolean)? = null

    /**
     * 根据回调函数来决定是否启用分割线
     *
     * @param block 函数返回值决定参数[BindingAdapter.BindingViewHolder]对应的Item是否启用分割线
     */
    fun onEnabled(block: BindingAdapter.BindingViewHolder.() -> Boolean) {
        this.onEnabled = block
    }

    /**
     * 添加类型后只允许该类型的条目显示分割线
     * 从未添加类型则默认为允许全部条目显示分割线
     *
     * @param typeArray 布局Id, 对应[BindingAdapter.addType]中的参数
     */
    fun addType(@LayoutRes vararg typeArray: Int) {
        if (typePool == null) {
            typePool = mutableListOf()
            onEnabled = {
                typePool?.contains(itemViewType) ?: true
            }
        }
        typeArray.forEach { typePool?.add(it) }
    }


    //</editor-fold>

    //<editor-fold desc="图片">

    /**
     * 将图片作为分割线, 图片宽高即分割线宽高
     */
    fun setDrawable(drawable: Drawable) {
        divider = drawable
    }

    /**
     * 将图片作为分割线, 图片宽高即分割线宽高
     */
    fun setDrawable(@DrawableRes drawableRes: Int) {
        val drawable = ContextCompat.getDrawable(context, drawableRes)
            ?: throw IllegalArgumentException("Drawable cannot be find")
        divider = drawable
    }
    //</editor-fold>

    //<editor-fold desc="颜色">
    /**
     * 设置分割线颜色, 如果不设置分割线宽度[setDivider]则分割线宽度默认为1px
     * 所谓分割线宽度指的是分割线的粗细, 而非水平宽度
     */
    fun setColor(@ColorInt color: Int) {
        divider = ColorDrawable(color)
    }

    /**
     * 设置分割线颜色, 如果不设置分割线宽度[setDivider]则分割线宽度默认为1px
     * 所谓分割线宽度指的是分割线的粗细, 而非水平宽度
     *
     * @param color 16进制的颜色值字符串
     */
    fun setColor(color: String) {
        val parseColor = Color.parseColor(color)
        divider = ColorDrawable(parseColor)
    }

    /**
     * 设置分割线颜色, 如果不设置分割线宽度[setDivider]则分割线宽度默认为1px
     * 所谓分割线宽度指的是分割线的粗细, 而非水平宽度
     *
     * @param color 颜色资源Id
     */
    fun setColorRes(@ColorRes color: Int) {
        val colorRes = ContextCompat.getColor(context, color)
        divider = ColorDrawable(colorRes)
    }
    //</editor-fold>

    //<editor-fold desc="间距">

    /**
     * 设置分割线宽度
     * 如果使用[setDrawable]则无效
     * @param width 分割线的尺寸 (分割线垂直时为宽, 水平时为高 )
     * @param dp 是否单位为dp, 默认为false即使用像素单位
     */
    fun setDivider(width: Int = 1, dp: Boolean = false) {
        if (!dp) {
            this.size = width
        } else {
            val density = context.resources.displayMetrics.density
            this.size = (density * width).roundToInt()
        }
    }

    /**
     * 设置分隔左右或上下间距, 依据分割线为垂直或者水平决定具体方向间距
     *
     * @param start 分割线为水平则是左间距, 垂直则为上间距, 如果网格布局则为上下间距
     * @param end 分割线为水平则是右间距, 垂直则为下间距, 如果网格布局则为左右间距, 默认等于[start]
     * @param dp 是否单位为dp, 默认为false即使用像素单位
     * @param baseItemStart 是否以item为基准设置间距, 默认为false即以RecyclerView为基准设置分割线的间距
     * @param baseItemEnd 是否以item为基准设置间距, 默认为false即以RecyclerView为基准设置分割线的间距
     */
    fun setMargin(
        start: Int = 0,
        end: Int = start,
        dp: Boolean = false,
        baseItemStart: Boolean = false,
        baseItemEnd: Boolean = false,
    ) {
        marginBaseItemStart = baseItemStart
        marginBaseItemEnd = baseItemEnd
        if (!dp) {
            this.marginStart = start
            this.marginEnd = end
        } else {
            val density = context.resources.displayMetrics.density
            this.marginStart = (start * density).roundToInt()
            this.marginEnd = (end * density).roundToInt()
        }
    }
    //</editor-fold>


    //<editor-fold desc="覆写">
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val layoutManager = parent.layoutManager ?: return

        onEnabled?.let {
            val vh = parent.findContainingViewHolder(view) as BindingAdapter.BindingViewHolder
            val modelOrNull = vh.getModelOrNull<Any>()
            if (!expandVisible && modelOrNull != null && modelOrNull is ItemExpand && modelOrNull.itemExpand) return
            if (!it.invoke(vh)) return
        }

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val divider = divider
        val height = when {
            divider == null -> size
            divider.intrinsicHeight != -1 -> divider.intrinsicHeight
            divider.intrinsicWidth != -1 -> divider.intrinsicWidth
            else -> size
        }

        val width = when {
            divider == null -> size
            divider.intrinsicWidth != -1 -> divider.intrinsicWidth
            divider.intrinsicHeight != -1 -> divider.intrinsicHeight
            else -> size
        }

        val reverseLayout = layoutManager.isReverseLayout
        val edge = computeEdge(position, layoutManager, reverseLayout)
        adjustOrientation(layoutManager)

        when {
            orientation == DividerOrientation.GRID ||
                    (layoutManager is GridLayoutManager && layoutManager.orientation == RecyclerView.HORIZONTAL && orientation == DividerOrientation.HORIZONTAL) ||
                    (layoutManager is GridLayoutManager && layoutManager.orientation == RecyclerView.VERTICAL && orientation == DividerOrientation.VERTICAL) -> {

                val rvOrientation = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.orientation
                    is StaggeredGridLayoutManager -> layoutManager.orientation
                    else -> RecyclerView.VERTICAL
                }

                val spanCount = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.spanCount
                    is StaggeredGridLayoutManager -> layoutManager.spanCount
                    else -> 1
                }

                val spanGroupCount = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.spanSizeLookup.getSpanGroupIndex(state.itemCount - 1, spanCount) + 1
                    is StaggeredGridLayoutManager -> ceil(state.itemCount / spanCount.toFloat()).toInt()
                    else -> 1
                }

                val spanIndex = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.spanSizeLookup.getSpanIndex(position, spanCount)
                    is StaggeredGridLayoutManager -> {
                        val v = layoutManager.findViewByPosition(position) ?: return
                        (v.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
                    }

                    else -> 0
                }

                val spanGroupIndex = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.spanSizeLookup.getSpanGroupIndex(position, spanCount)
                    is StaggeredGridLayoutManager -> ceil((position + 1) / spanCount.toFloat()).toInt() - 1
                    else -> 0
                }

                val spanSize = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.spanSizeLookup.getSpanSize(position)
                    else -> 1
                }

                val left = when {
                    endVisible && rvOrientation == RecyclerView.VERTICAL -> width - spanIndex * width / spanCount
                    startVisible && rvOrientation == RecyclerView.HORIZONTAL -> width - spanIndex * width / spanCount
                    else -> spanIndex * width / spanCount
                }

                val right = when {
                    endVisible && rvOrientation == RecyclerView.VERTICAL -> (spanIndex + spanSize) * width / spanCount
                    startVisible && rvOrientation == RecyclerView.HORIZONTAL -> (spanIndex + spanSize) * width / spanCount
                    else -> width - (spanIndex + spanSize) * width / spanCount
                }

                val top = when {
                    layoutManager is StaggeredGridLayoutManager -> {
                        if (rvOrientation == RecyclerView.VERTICAL) {
                            if (edge.top) if (startVisible) height else 0 else 0
                        } else {
                            if (edge.left) if (endVisible) width else 0 else 0
                        }
                    }

                    (startVisible && rvOrientation == RecyclerView.VERTICAL) ||
                            (endVisible && rvOrientation == RecyclerView.HORIZONTAL) -> if (reverseLayout) {
                        (spanGroupIndex + 1) * height / spanGroupCount
                    } else {
                        height - spanGroupIndex * height / spanGroupCount
                    }

                    else -> if (reverseLayout) {
                        height - (spanGroupIndex + 1) * height / spanGroupCount
                    } else {
                        spanGroupIndex * height / spanGroupCount
                    }
                }

                val bottom = when {
                    layoutManager is StaggeredGridLayoutManager -> {
                        if (rvOrientation == RecyclerView.VERTICAL) {
                            if (edge.bottom) if (startVisible) height else 0 else height
                        } else {
                            if (edge.right) if (endVisible) width else 0 else height
                        }
                    }

                    (startVisible && rvOrientation == RecyclerView.VERTICAL) ||
                            (endVisible && rvOrientation == RecyclerView.HORIZONTAL) -> if (reverseLayout) {
                        height - spanGroupIndex * height / spanGroupCount
                    } else {
                        (spanGroupIndex + 1) * height / spanGroupCount
                    }

                    else -> if (reverseLayout) {
                        spanGroupIndex * height / spanGroupCount
                    } else {
                        height - (spanGroupIndex + 1) * height / spanGroupCount
                    }
                }

                when {
                    orientation == DividerOrientation.VERTICAL -> outRect.set(left, 0, right, 0)
                    orientation == DividerOrientation.HORIZONTAL -> outRect.set(0, left, 0, right)
                    rvOrientation == RecyclerView.VERTICAL -> outRect.set(left, top, right, bottom)
                    rvOrientation == RecyclerView.HORIZONTAL -> outRect.set(top, left, bottom, right)
                }
            }

            orientation == DividerOrientation.HORIZONTAL -> {
                val top = if (reverseLayout) {
                    if ((endVisible && edge.top) || !edge.top) height else 0
                } else {
                    if (startVisible && edge.top) height else 0
                }
                val bottom = if (reverseLayout) {
                    if (startVisible && edge.bottom) height else 0
                } else {
                    if ((endVisible && edge.bottom) || !edge.bottom) height else 0
                }
                outRect.set(0, top, 0, bottom)
            }

            orientation == DividerOrientation.VERTICAL -> {
                val left = if (startVisible && edge.left) width else 0
                val right = if ((endVisible && edge.right) || !edge.right) width else 0
                outRect.set(left, 0, right, 0)
            }
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
        if (layoutManager == null || divider == null) return

        adjustOrientation(layoutManager)
        val reverseLayout = layoutManager.isReverseLayout

        when (orientation) {
            DividerOrientation.HORIZONTAL -> drawHorizontal(canvas, parent, reverseLayout)
            DividerOrientation.VERTICAL -> drawVertical(canvas, parent, reverseLayout)
            DividerOrientation.GRID -> drawGrid(canvas, parent, reverseLayout)
        }
    }
    //</editor-fold>

    /**
     * 自动调整不同布局管理器应该对应的[orientation]
     */
    private fun adjustOrientation(layoutManager: RecyclerView.LayoutManager) {
        if (layoutManager !is GridLayoutManager && layoutManager is LinearLayoutManager) {
            orientation =
                if ((layoutManager as? LinearLayoutManager)?.orientation == RecyclerView.VERTICAL) DividerOrientation.HORIZONTAL else DividerOrientation.VERTICAL
        } else if (layoutManager is StaggeredGridLayoutManager) {
            orientation = DividerOrientation.GRID
        }
    }

    /**
     * 绘制水平分割线
     */
    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView, reverseLayout: Boolean) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft + this.marginStart
            right = parent.width - parent.paddingRight - marginEnd
        } else {
            left = 0 + this.marginStart
            right = parent.width - marginEnd
        }

        loop@ for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)

            if (onEnabled != null) {
                val vh = parent.getChildViewHolder(child) as BindingAdapter.BindingViewHolder
                val modelOrNull = vh.getModelOrNull<Any>()
                if (!expandVisible && modelOrNull != null && modelOrNull is ItemExpand && modelOrNull.itemExpand) continue@loop
                val enabled = onEnabled?.invoke(vh) ?: true
                if (!enabled) continue@loop
            }

            val position = parent.getChildAdapterPosition(child)
            val layoutManager = parent.layoutManager ?: return
            val edge = computeEdge(position, layoutManager, reverseLayout)

            if (orientation != DividerOrientation.GRID && !endVisible && (if (reverseLayout) edge.top else edge.bottom)) {
                continue@loop
            }

            divider?.apply {
                val decoratedBounds = Rect()
                parent.getDecoratedBoundsWithMargins(child, decoratedBounds)

                val firstTop: Int
                val firstBottom: Int
                if (reverseLayout) {
                    firstBottom = decoratedBounds.bottom
                    firstTop = if (intrinsicHeight == -1) firstBottom - size else firstBottom - intrinsicHeight
                } else {
                    firstTop = decoratedBounds.top
                    firstBottom = if (intrinsicHeight == -1) firstTop + size else firstTop + intrinsicHeight
                }

                val top: Int
                val bottom: Int
                if (reverseLayout) {
                    top = decoratedBounds.top
                    bottom = if (intrinsicHeight == -1) top + size else top + intrinsicHeight
                } else {
                    bottom = decoratedBounds.bottom
                    top = if (intrinsicHeight == -1) bottom - size else bottom - intrinsicHeight
                }

                if (startVisible && if (reverseLayout) edge.bottom else edge.top) {
                    setBounds(left, firstTop, right, firstBottom)
                    draw(canvas)
                }

                setBounds(left, top, right, bottom)
                draw(canvas)
            }
        }
        canvas.restore()
    }

    /**
     * 绘制垂直分割线
     */
    private fun drawVertical(canvas: Canvas, parent: RecyclerView, reverseLayout: Boolean) {
        canvas.save()
        val top: Int
        val bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop + marginStart
            bottom = parent.height - parent.paddingBottom - marginEnd
        } else {
            top = 0 + marginStart
            bottom = parent.height - marginEnd
        }

        val childCount = parent.childCount

        loop@ for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            if (onEnabled != null) {
                val vh = parent.getChildViewHolder(child) as BindingAdapter.BindingViewHolder
                val modelOrNull = vh.getModelOrNull<Any>()
                if (!expandVisible && modelOrNull != null && modelOrNull is ItemExpand && modelOrNull.itemExpand) continue@loop
                val enabled = onEnabled?.invoke(vh) ?: true
                if (!enabled) continue@loop
            }

            val position = parent.getChildAdapterPosition(child)
            val layoutManager = parent.layoutManager ?: return
            val edge = computeEdge(position, layoutManager, reverseLayout)

            if (orientation != DividerOrientation.GRID && !endVisible && edge.right) {
                continue@loop
            }

            divider?.apply {
                val decoratedBounds = Rect()
                parent.getDecoratedBoundsWithMargins(child, decoratedBounds)

                val firstRight =
                    if (intrinsicWidth == -1) decoratedBounds.left + size else decoratedBounds.left + intrinsicWidth
                val firstLeft = decoratedBounds.left

                val right = (decoratedBounds.right + child.translationX).roundToInt()
                val left = if (intrinsicWidth == -1) right - size else right - intrinsicWidth

                if (startVisible && edge.left) {
                    setBounds(firstLeft, top, firstRight, bottom)
                    draw(canvas)
                }

                setBounds(left, top, right, bottom)
                draw(canvas)
            }
        }

        canvas.restore()
    }

    /**
     * 绘制网格分割线
     */
    private fun drawGrid(canvas: Canvas, parent: RecyclerView, reverseLayout: Boolean) {
        canvas.save()

        val childCount = parent.childCount

        loop@ for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            if (onEnabled != null) {
                val vh = parent.getChildViewHolder(child) as BindingAdapter.BindingViewHolder
                val modelOrNull = vh.getModelOrNull<Any>()
                if (!expandVisible && modelOrNull != null && modelOrNull is ItemExpand && modelOrNull.itemExpand) continue@loop
                val enabled = onEnabled?.invoke(vh) ?: true
                if (!enabled) continue@loop
            }

            val position = parent.getChildAdapterPosition(child)
            if (position == RecyclerView.NO_POSITION) {
                parent.invalidateItemDecorations()
                continue@loop
            }
            val layoutManager = parent.layoutManager ?: return
            val edge = computeEdge(position, layoutManager, reverseLayout)

            val divider = divider
            val height = when {
                divider == null -> size
                divider.intrinsicHeight != -1 -> divider.intrinsicHeight
                divider.intrinsicWidth != -1 -> divider.intrinsicWidth
                else -> size
            }

            val width = when {
                divider == null -> size
                divider.intrinsicWidth != -1 -> divider.intrinsicWidth
                divider.intrinsicHeight != -1 -> divider.intrinsicHeight
                else -> size
            }

            divider?.apply {
                val layoutParams = child.layoutParams as RecyclerView.LayoutParams
                val bounds = Rect(
                    child.left + layoutParams.leftMargin,
                    child.top + layoutParams.topMargin,
                    child.right + layoutParams.rightMargin,
                    child.bottom + layoutParams.bottomMargin
                )

                val baseItemStartMargin = if (marginBaseItemStart && marginStart != 0) marginStart + height else 0
                val baseItemEndMargin = if (marginBaseItemEnd && marginEnd != 0) marginEnd + width else 0

                // top
                if (startVisible && edge.top) {
                    setBounds(bounds.left - width, bounds.top - height, bounds.right + width, bounds.top)
                    draw(canvas)
                } else if (!edge.top && edge.right) {
                    setBounds(bounds.left - width + baseItemEndMargin, bounds.top - height, bounds.right - marginEnd, bounds.top)
                    draw(canvas)
                } else if (!edge.top && edge.left) {
                    setBounds(bounds.left + marginEnd, bounds.top - height, bounds.right + width - baseItemEndMargin, bounds.top)
                    draw(canvas)
                } else if (!edge.top) {
                    setBounds(bounds.left - width + baseItemEndMargin, bounds.top - height, bounds.right + width - baseItemEndMargin, bounds.top)
                    draw(canvas)
                }

                // bottom
                if (startVisible && edge.bottom) {
                    setBounds(bounds.left - width, bounds.bottom, bounds.right + width, bounds.bottom + height)
                    draw(canvas)
                } else if (!edge.bottom && edge.right) {
                    setBounds(bounds.left - width + baseItemEndMargin, bounds.bottom, bounds.right - marginEnd, bounds.bottom + height)
                    draw(canvas)
                } else if (!edge.bottom && edge.left) {
                    setBounds(bounds.left + marginEnd, bounds.bottom, bounds.right + width - baseItemEndMargin, bounds.bottom + height)
                    draw(canvas)
                } else if (!edge.bottom) {
                    setBounds(bounds.left - width + baseItemEndMargin, bounds.bottom, bounds.right + width - baseItemEndMargin, bounds.bottom + height)
                    draw(canvas)
                }

                // left
                if (endVisible && edge.left) {
                    setBounds(bounds.left - width, bounds.top, bounds.left, bounds.bottom + height)
                    draw(canvas)
                } else if (!edge.left && edge.top) {
                    setBounds(bounds.left - width, bounds.top + marginStart, bounds.left, bounds.bottom + height - baseItemStartMargin)
                    draw(canvas)
                } else if (!edge.left && edge.bottom) {
                    setBounds(bounds.left - width, bounds.top - height + baseItemStartMargin, bounds.left, bounds.bottom - marginStart)
                    draw(canvas)
                } else if (!edge.left) {
                    setBounds(bounds.left - width, bounds.top + baseItemStartMargin, bounds.left, bounds.bottom + height - baseItemStartMargin)
                    draw(canvas)
                }

                // right
                if (endVisible && edge.right) {
                    setBounds(bounds.right, bounds.top, bounds.right + width, bounds.bottom + height)
                    draw(canvas)
                } else if (!edge.right && edge.top) {
                    setBounds(bounds.right, bounds.top + marginStart, bounds.right + width, bounds.bottom + height - baseItemStartMargin)
                    draw(canvas)
                } else if (!edge.right && edge.bottom) {
                    setBounds(bounds.right, bounds.top - height + baseItemStartMargin, bounds.right + width, bounds.bottom - marginStart)
                    draw(canvas)
                } else if (!edge.right) {
                    setBounds(bounds.right, bounds.top + baseItemStartMargin, bounds.right + width, bounds.bottom + height - baseItemStartMargin)
                    draw(canvas)
                }
            }
        }

        canvas.restore()
    }

    /**
     * 列表条目是否靠近边缘的结算结果
     *
     * @param left 是否靠左
     * @param right 是否靠左
     * @param top 是否靠顶
     * @param bottom 是否靠底
     */
    data class Edge(
        var left: Boolean = false,
        var top: Boolean = false,
        var right: Boolean = false,
        var bottom: Boolean = false
    ) {

        companion object {

            /**
             * 计算指定条目的边缘位置
             * @param position 指定计算的Item索引
             * @param layoutManager 当前列表的LayoutManager
             */
            fun computeEdge(
                position: Int,
                layoutManager: RecyclerView.LayoutManager,
                reverseLayout: Boolean
            ): Edge {

                val index = position + 1
                val itemCount = layoutManager.itemCount

                return Edge().apply {
                    when (layoutManager) {
                        is StaggeredGridLayoutManager -> {
                            val spanCount = layoutManager.spanCount
                            // 如果item尚未布局将返回null, 此时本方法无效
                            val viewByPosition = layoutManager.findViewByPosition(position) ?: return@apply
                            val spanIndex = (viewByPosition.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex + 1

                            if (layoutManager.orientation == RecyclerView.VERTICAL) {
                                left = spanIndex == 1
                                right = spanIndex == spanCount
                                top = if (reverseLayout) index > itemCount - spanCount else index <= spanCount
                                bottom = if (reverseLayout) index <= spanCount else index > itemCount - spanCount
                            } else {
                                left = index <= spanCount
                                right = index > itemCount - spanCount
                                top = if (reverseLayout) spanIndex == spanCount else spanIndex == 1
                                bottom = if (reverseLayout) spanIndex == 1 else spanIndex == spanCount
                            }
                        }

                        is GridLayoutManager -> {
                            val spanSizeLookup = layoutManager.spanSizeLookup
                            val spanCount = layoutManager.spanCount
                            val spanGroupIndex = spanSizeLookup.getSpanGroupIndex(position, spanCount)
                            val maxSpanGroupIndex = spanSizeLookup.getSpanGroupIndex(itemCount - 1, spanCount)
                            val spanIndex = spanSizeLookup.getSpanIndex(position, spanCount) + 1
                            val spanSize = spanSizeLookup.getSpanSize(position)

                            if (layoutManager.orientation == RecyclerView.VERTICAL) {
                                left = spanIndex == 1
                                right = spanIndex + spanSize - 1 == spanCount
                                top = if (reverseLayout) {
                                    spanGroupIndex == maxSpanGroupIndex
                                } else {
                                    index <= spanCount && spanGroupIndex == spanSizeLookup.getSpanGroupIndex(
                                        position - 1,
                                        spanCount
                                    )
                                }
                                bottom = if (reverseLayout) {
                                    index <= spanCount && spanGroupIndex == spanSizeLookup.getSpanGroupIndex(
                                        position - 1,
                                        spanCount
                                    )
                                } else {
                                    spanGroupIndex == maxSpanGroupIndex
                                }
                            } else {
                                left = spanGroupIndex == 0
                                right = spanGroupIndex == maxSpanGroupIndex
                                top = if (reverseLayout) spanIndex + spanSize - 1 == spanCount else spanIndex == 1
                                bottom = if (reverseLayout) spanIndex == 1 else spanIndex + spanSize - 1 == spanCount
                            }
                        }

                        is LinearLayoutManager -> {
                            if (layoutManager.orientation == RecyclerView.VERTICAL) {
                                left = true
                                right = true
                                top = if (reverseLayout) index == itemCount else index == 1
                                bottom = if (reverseLayout) index == 1 else index == itemCount
                            } else {
                                left = index == 1
                                right = index == itemCount
                                top = true
                                bottom = true
                            }
                        }
                    }
                }
            }
        }
    }
}