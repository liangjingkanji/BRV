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

import androidx.recyclerview.widget.DiffUtil

/**
 * 将数据对比实现转交给[ItemDifferCallback]
 * @param newModels 新的数据
 * @param oldModels 旧的数据
 * @param callback 实际对比数据接口
 */
internal class ProxyDiffCallback(private val newModels: List<Any?>?, private val oldModels: List<Any?>?, val callback: ItemDifferCallback) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldModels?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newModels?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (oldModels == null || newModels == null) {
            false
        } else {
            val oldItem = oldModels[oldItemPosition]
            val newItem = newModels[newItemPosition]
            if (oldItem != null && newItem != null) {
                callback.areItemsTheSame(oldItem, newItem)
            } else {
                oldItem == null && newItem == null
            }
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (oldModels == null || newModels == null) {
            false
        } else {
            val oldItem = oldModels[oldItemPosition]
            val newItem = newModels[newItemPosition]
            if (oldItem != null && newItem != null) {
                callback.areContentsTheSame(oldItem, newItem)
            } else {
                oldItem == null && newItem == null
            }
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return if (oldModels == null || newModels == null) {
            null
        } else {
            val oldItem = oldModels[oldItemPosition]
            val newItem = newModels[newItemPosition]
            if (oldItem != null && newItem != null) {
                callback.getChangePayload(oldItem, newItem)
            } else null
        }
    }
}