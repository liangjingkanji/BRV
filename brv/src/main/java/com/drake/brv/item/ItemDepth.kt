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

package com.drake.brv.item

import com.drake.brv.item.ItemDepth.Companion.refreshItemDepth

/**
 * Item实现该接口来用于记录元素位于集合的层级深度[itemDepth]
 * @see refreshItemDepth 实现接口后还需要手动在任意位置调用一次该函数进行初始化赋值[ItemDepth.itemDepth]
 */
interface ItemDepth {
    /** 当前item在分组中的深度 */
    var itemDepth: Int

    companion object {
        /**
         * 递归遍历列表为所有实现[ItemDepth]的元素中的字段[ItemDepth.itemDepth]赋值当前位于集合的层级深度
         * @param initDepth  层级深度初始值
         */
        fun <T> refreshItemDepth(
            models: List<T>,
            initDepth: Int = 0
        ): List<T> = models.onEach { item ->
            if (item is ItemDepth) {
                item.itemDepth = initDepth
            }
            if (item is ItemExpand) {
                item.getItemSublist()?.run {
                    refreshItemDepth(this, initDepth + 1)
                }
            }
        }
    }
}
