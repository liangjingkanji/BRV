/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：5/5/20 9:12 PM
 */

package com.drake.brv

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
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
import com.drake.brv.annotaion.Orientation
import com.drake.brv.item.ItemExpand
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * 最强大的分割线工具
 *
 * 完全功能需要配合[BindingAdapter]使用
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
     * 第一个条目之前是否显示分割线, 当处于[Orientation.GRID] 时水平方向顶端和末端是否显示分割线
     */
    var startVisible = false

    /**
     * 最后一个条目是否显示分割线, 当处于[Orientation.GRID] 时垂直方向顶端和末端是否显示分割线
     */
    var endVisible = false

    /**
     * 展开分组条目后该条目是否显示分割线
     */
    var expandVisible = false

    /**
     * 当使用[LinearLayoutManager]或[StaggeredGridLayoutManager]时该属性无需设置
     * [LinearLayoutManager]仅根据[LinearLayoutManager.getOrientation]来自动指定分割线方向
     * [StaggeredGridLayoutManager]仅可使用网格[Orientation.GRID]分割线
     */
    var orientation = Orientation.HORIZONTAL

    private var size = 1
    private var marginStart = 0
    private var marginEnd = 0
    private var divider: Drawable? = null

    //<editor-fold desc="类型">

    /**
     * 集合内包含的类型才显示分割线
     */
    var typePool: MutableList<Int>? = null

    private var onEnabled: (BindingAdapter.BindingViewHolder.() -> Boolean)? = null

    /**
     * 根据[BindingAdapter.BindingViewHolder]来判断是否启用分割线
     */
    fun onEnabled(enabled: BindingAdapter.BindingViewHolder.() -> Boolean) {
        this.onEnabled = enabled
    }

    /**
     * 添加类型后只允许该类型的条目显示分割线
     * 从未添加类型则默认为允许全部条目显示分割线
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
     * 设置图片
     */
    fun setDrawable(drawable: Drawable) {
        divider = drawable
    }

    /**
     * 自定义分隔物
     */
    fun setDrawable(@DrawableRes drawableRes: Int) {
        val drawable = ContextCompat.getDrawable(context, drawableRes)
            ?: throw IllegalArgumentException("Drawable cannot be find")
        divider = drawable
    }
    //</editor-fold>

    //<editor-fold desc="颜色">
    /**
     * 设置颜色
     */
    fun setColor(@ColorInt color: Int) {
        divider = ColorDrawable(color)
    }

    /**
     * 设置十六进制的颜色值
     */
    fun setColor(color: String) {
        val parseColor = Color.parseColor(color)
        divider = ColorDrawable(parseColor)
    }

    /**
     * 设置颜色资源文件
     */
    fun setColorRes(@ColorRes color: Int) {
        val colorRes = ContextCompat.getColor(context, color)
        divider = ColorDrawable(colorRes)
    }

    private var background = Color.TRANSPARENT

    /**
     * 分割线背景色
     * 分割线有时候会存在间距或属于虚线, 可以设置背景色解决不统一的问题, 默认为透明[Color.TRANSPARENT]
     */
    fun setBackground(@ColorInt color: Int) {
        background = color
    }

    fun setBackground(colorString: String) {
        try {
            background = Color.parseColor(colorString)
        } catch (e: Exception) {
            throw IllegalArgumentException("Unknown color: $colorString")
        }
    }

    fun setBackgroundRes(@ColorRes color: Int) {
        background = ContextCompat.getColor(context, color)
    }

    //</editor-fold>

    //<editor-fold desc="间距">

    /**
     * 设置分割线宽度
     * 如果使用[setDrawable]则无效
     */
    fun setDivider(width: Int = 1, pixel: Boolean = false) {
        if (pixel) {
            this.size = width
        } else {
            val density = context.resources.displayMetrics.density
            this.size = (density * width).roundToInt()
        }
    }

    /**
     * 设置分隔左右或上下间距, 依据分割线为垂直或者水平决定具体方向间距
     * 未设置分割线颜色[setColor]或者图片[setDrawable]同样有效
     */
    fun setMargin(start: Int = 0, end: Int = 0, pixel: Boolean = false) {
        if (pixel) {
            this.marginStart = start
            this.marginEnd = end
        } else {
            val density = context.resources.displayMetrics.density
            this.marginStart = (start * density).roundToInt()
            this.marginEnd = (end * density).roundToInt()
        }
    }
    //</editor-fold>


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

        val height = when {
            divider == null -> size
            divider?.intrinsicHeight != -1 -> divider!!.intrinsicHeight
            divider?.intrinsicWidth != -1 -> divider!!.intrinsicWidth
            else -> size
        }

        val width = when {
            divider == null -> size
            divider?.intrinsicWidth != -1 -> divider!!.intrinsicWidth
            divider?.intrinsicHeight != -1 -> divider!!.intrinsicHeight
            else -> size
        }

        val edge = computeEdge(position, layoutManager)
        adjustOrientation(layoutManager)

        when (orientation) {
            Orientation.HORIZONTAL -> {
                val top = if (startVisible && edge.top) height else 0
                val bottom = if ((endVisible && edge.bottom) || !edge.bottom) height else 0
                outRect.set(0, top, 0, bottom)
            }
            Orientation.VERTICAL -> {
                val left = if (startVisible && edge.left) width else 0
                val right = if ((endVisible && edge.right) || !edge.right) width else 0
                outRect.set(left, 0, right, 0)
            }
            Orientation.GRID -> {

                val spanCount = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.spanCount
                    is StaggeredGridLayoutManager -> layoutManager.spanCount
                    else -> 1
                }

                val spanGroupCount = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.spanSizeLookup.getSpanGroupIndex(
                        state.itemCount - 1,
                        spanCount
                    ) + 1
                    is StaggeredGridLayoutManager -> ceil(state.itemCount / spanCount.toFloat()).toInt()
                    else -> 1
                }

                val spanIndex = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.spanSizeLookup.getSpanIndex(
                        position,
                        spanCount
                    )
                    is StaggeredGridLayoutManager -> (layoutManager.findViewByPosition(position)!!.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
                    else -> 0
                }

                val spanGroupIndex = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.spanSizeLookup.getSpanGroupIndex(
                        position,
                        spanCount
                    )
                    is StaggeredGridLayoutManager -> ceil((position + 1) / spanCount.toFloat()).toInt() - 1
                    else -> 0
                }

                val spanSize = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.spanSizeLookup.getSpanSize(position)
                    else -> 1
                }

                val orientation = when (layoutManager) {
                    is GridLayoutManager -> layoutManager.orientation
                    is StaggeredGridLayoutManager -> layoutManager.orientation
                    else -> RecyclerView.VERTICAL
                }

                val left = when {
                    endVisible && orientation == RecyclerView.VERTICAL -> width - spanIndex * width / spanCount
                    startVisible && orientation == RecyclerView.HORIZONTAL -> width - spanIndex * width / spanCount
                    else -> spanIndex * width / spanCount
                }

                val right = when {
                    endVisible && orientation == RecyclerView.VERTICAL -> (spanIndex + spanSize) * width / spanCount
                    startVisible && orientation == RecyclerView.HORIZONTAL -> (spanIndex + spanSize) * width / spanCount
                    else -> width - (spanIndex + spanSize) * width / spanCount
                }

                val top = when {
                    startVisible && orientation == RecyclerView.VERTICAL -> height - spanGroupIndex * height / spanGroupCount
                    endVisible && orientation == RecyclerView.HORIZONTAL -> height - spanGroupIndex * height / spanGroupCount
                    else -> spanGroupIndex * height / spanGroupCount
                }

                val bottom = when {
                    startVisible && orientation == RecyclerView.VERTICAL -> (spanGroupIndex + 1) * height / spanGroupCount
                    endVisible && orientation == RecyclerView.HORIZONTAL -> (spanGroupIndex + 1) * height / spanGroupCount
                    else -> height - (spanGroupIndex + 1) * height / spanGroupCount
                }

                if (orientation == RecyclerView.VERTICAL) {
                    outRect.set(left, top, right, bottom)
                } else outRect.set(top, left, bottom, right)
            }
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
        if (layoutManager == null || divider == null) return

        adjustOrientation(layoutManager)

        when (orientation) {
            Orientation.HORIZONTAL -> drawHorizontal(canvas, parent)
            Orientation.VERTICAL -> drawVertical(canvas, parent)
            Orientation.GRID -> drawGrid(canvas, parent)
        }
    }

    /**
     * 自动调整不同布局管理器应该对应的[orientation]
     */
    private fun adjustOrientation(layoutManager: RecyclerView.LayoutManager) {
        if (layoutManager !is GridLayoutManager && layoutManager is LinearLayoutManager) {
            orientation =
                if ((layoutManager as? LinearLayoutManager)?.orientation == RecyclerView.VERTICAL) Orientation.HORIZONTAL else Orientation.VERTICAL
        } else if (layoutManager is StaggeredGridLayoutManager) {
            orientation = Orientation.GRID
        }
    }

    /**
     * 绘制水平分割线
     */
    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
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

            Log.d("日志", "(DefaultDecoration.kt:372)    ")

            val position = parent.getChildAdapterPosition(child)
            val layoutManager = parent.layoutManager ?: return
            val edge = computeEdge(position, layoutManager)

            if (orientation != Orientation.GRID && !endVisible && edge.bottom) {
                continue@loop
            }

            divider?.apply {
                val decoratedBounds = Rect()
                parent.getDecoratedBoundsWithMargins(child, decoratedBounds)

                val firstBottom =
                    if (intrinsicHeight == -1) decoratedBounds.top + size else decoratedBounds.top + intrinsicHeight
                val firstTop = decoratedBounds.top

                val bottom = decoratedBounds.bottom
                val top = if (intrinsicHeight == -1) bottom - size else bottom - intrinsicHeight

                if (background != Color.TRANSPARENT) {
                    val paint = Paint()
                    paint.color = background
                    paint.style = Paint.Style.FILL

                    if (startVisible && edge.top) {
                        val firstRect = Rect(
                            parent.paddingLeft,
                            firstTop,
                            parent.width - parent.paddingRight,
                            firstBottom
                        )
                        canvas.drawRect(firstRect, paint)
                    }

                    val rect =
                        Rect(parent.paddingLeft, top, parent.width - parent.paddingRight, bottom)
                    canvas.drawRect(rect, paint)
                }

                if (startVisible && edge.top) {
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
    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
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

            val edge = computeEdge(position, layoutManager)

            if (orientation != Orientation.GRID && !endVisible && edge.right) {
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

                if (background != Color.TRANSPARENT) {
                    val paint = Paint()
                    paint.color = background
                    paint.style = Paint.Style.FILL

                    if (startVisible && edge.left) {
                        val firstRect = Rect(
                            firstLeft,
                            parent.paddingTop,
                            firstRight,
                            parent.height - parent.paddingBottom
                        )
                        canvas.drawRect(firstRect, paint)
                    }

                    val rect =
                        Rect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
                    canvas.drawRect(rect, paint)
                }

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
    private fun drawGrid(canvas: Canvas, parent: RecyclerView) {
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
            val layoutManager = parent.layoutManager ?: return
            val edge = computeEdge(position, layoutManager)

            val height = when {
                divider == null -> size
                divider?.intrinsicHeight != -1 -> divider!!.intrinsicHeight
                divider?.intrinsicWidth != -1 -> divider!!.intrinsicWidth
                else -> size
            }

            val width = when {
                divider == null -> size
                divider?.intrinsicWidth != -1 -> divider!!.intrinsicWidth
                divider?.intrinsicHeight != -1 -> divider!!.intrinsicHeight
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


                // top
                if (!endVisible && edge.right) {
                    setBounds(bounds.left - width, bounds.top - height, bounds.right, bounds.top)
                    draw(canvas)
                } else if (!endVisible && edge.left) {
                    setBounds(bounds.left, bounds.top - height, bounds.right + width, bounds.top)
                    draw(canvas)
                } else if (!edge.top || (startVisible && edge.top)) {
                    setBounds(
                        bounds.left - width,
                        bounds.top - height,
                        bounds.right + width,
                        bounds.top
                    )
                    draw(canvas)
                }

                // bottom
                if (!endVisible && edge.right) {
                    setBounds(
                        bounds.left - width,
                        bounds.bottom,
                        bounds.right,
                        bounds.bottom + height
                    )
                    draw(canvas)
                } else if (!endVisible && edge.left) {
                    setBounds(
                        bounds.left,
                        bounds.bottom,
                        bounds.right + width,
                        bounds.bottom + height
                    )
                    draw(canvas)
                } else if (!edge.bottom || (startVisible && edge.bottom)) {
                    setBounds(
                        bounds.left - width,
                        bounds.bottom,
                        bounds.right + width,
                        bounds.bottom + height
                    )
                    draw(canvas)
                }

                // left
                if (!edge.left || (endVisible && edge.left)) {
                    setBounds(bounds.left - width, bounds.top, bounds.left, bounds.bottom)
                    draw(canvas)
                }

                // right
                if (!edge.right || (endVisible && edge.right)) {
                    setBounds(bounds.right, bounds.top, bounds.right + width, bounds.bottom)
                    draw(canvas)
                }
            }
        }

        canvas.restore()
    }

    /**
     * 列表条目是否靠近边缘的结算结果
     */
    data class Edge(
        var left: Boolean = false,
        var top: Boolean = false,
        var right: Boolean = false,
        var bottom: Boolean = false
    )

    companion object {

        /**
         * 计算指定条目的边缘位置
         */
        fun computeEdge(position: Int, layoutManager: RecyclerView.LayoutManager): Edge {

            val index = position + 1
            val itemCount = layoutManager.itemCount

            return Edge().apply {
                when (layoutManager) {
                    is StaggeredGridLayoutManager -> {
                        val spanCount = layoutManager.spanCount
                        val spanIndex =
                            (layoutManager.findViewByPosition(position)!!.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex + 1

                        if (layoutManager.orientation == RecyclerView.VERTICAL) {
                            left = spanIndex == 1
                            right = spanIndex == spanCount
                            top = index <= spanCount
                            bottom = index > itemCount - spanCount
                        } else {
                            left = index <= spanCount
                            right = index > itemCount - spanCount
                            top = spanIndex == 1
                            bottom = spanIndex == spanCount
                        }
                    }
                    is GridLayoutManager -> {
                        val spanSizeLookup = layoutManager.spanSizeLookup
                        val spanCount = layoutManager.spanCount
                        val spanGroupIndex = spanSizeLookup.getSpanGroupIndex(position, spanCount)
                        val spanIndex = spanSizeLookup.getSpanIndex(position, spanCount) + 1
                        val spanSize = spanSizeLookup.getSpanSize(position)

                        if (layoutManager.orientation == RecyclerView.VERTICAL) {
                            left = spanIndex == 1
                            right = spanIndex + spanSize - 1 == spanCount || index == itemCount
                            top =
                                index <= spanCount && spanGroupIndex == spanSizeLookup.getSpanGroupIndex(
                                    position - 1,
                                    spanCount
                                )
                            bottom = index > itemCount - spanCount
                        } else {
                            left = spanGroupIndex == 0
                            right = index > itemCount - spanCount
                            top = spanIndex == 1
                            bottom = spanIndex + spanSize - 1 == spanCount
                        }
                    }
                    is LinearLayoutManager -> {
                        if (layoutManager.orientation == RecyclerView.VERTICAL) {
                            left = true
                            right = true
                            top = index == 1
                            bottom = index == itemCount
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
