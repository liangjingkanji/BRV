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

package com.drake.brv.listener

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.BindingAdapter

/**
 * 列表滑动多选触摸监听器
 *
 * 在编辑模式([BindingAdapter.toggleMode])下，按下并滑动手指可以批量选择/取消选择列表项:
 * - 按下时根据当前条目的选中状态决定本次手势是"选中"还是"取消选中"
 * - 滑动手指经过的所有条目将被统一执行选中/取消选中操作
 * - 向回滑动时，离开范围的条目将恢复到手势开始前的原始状态
 *
 * 通过 [RecyclerView.addOnItemTouchListener] 或扩展函数 [com.drake.brv.utils.addSwipeSelect] 使用
 */
class SwipeSelectTouchListener(private val adapter: BindingAdapter) : RecyclerView.OnItemTouchListener {

    private var isActive = false
    private var startPosition = RecyclerView.NO_POSITION
    private var lastPosition = RecyclerView.NO_POSITION

    /** true 表示本次手势执行"选中"，false 表示"取消选中" */
    private var selectMode = true

    /** 记录手势开始时各条目的原始选中状态，用于向回滑动时恢复 */
    private val originalStates = mutableMapOf<Int, Boolean>()

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        if (!adapter.toggleMode) return false

        return when (e.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                val childView = rv.findChildViewUnder(e.x, e.y) ?: return false
                val position = rv.getChildAdapterPosition(childView)
                if (position == RecyclerView.NO_POSITION) return false

                startPosition = position
                lastPosition = position
                selectMode = !adapter.checkedPosition.contains(position)
                isActive = true
                originalStates.clear()
                originalStates[position] = adapter.checkedPosition.contains(position)
                adapter.setChecked(position, selectMode)
                true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                reset()
                false
            }
            else -> isActive
        }
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        if (!isActive) return
        when (e.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                val childView = rv.findChildViewUnder(e.x, e.y) ?: return
                val position = rv.getChildAdapterPosition(childView)
                if (position == RecyclerView.NO_POSITION || position == lastPosition) return

                val from = minOf(startPosition, position)
                val to = maxOf(startPosition, position)
                val prevFrom = minOf(startPosition, lastPosition)
                val prevTo = maxOf(startPosition, lastPosition)

                // 新进入范围的条目：执行选中/取消选中
                for (i in from..to) {
                    if (i !in prevFrom..prevTo) {
                        if (!originalStates.containsKey(i)) {
                            originalStates[i] = adapter.checkedPosition.contains(i)
                        }
                        adapter.setChecked(i, selectMode)
                    }
                }

                // 离开范围的条目：恢复到手势开始前的原始状态
                for (i in prevFrom..prevTo) {
                    if (i !in from..to) {
                        val original = originalStates.getOrDefault(i, false)
                        adapter.setChecked(i, original)
                    }
                }

                lastPosition = position
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> reset()
        }
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        if (disallowIntercept) reset()
    }

    private fun reset() {
        isActive = false
        startPosition = RecyclerView.NO_POSITION
        lastPosition = RecyclerView.NO_POSITION
        originalStates.clear()
    }
}
