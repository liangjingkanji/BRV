package com.drake.brv.listener

import androidx.recyclerview.widget.DiffUtil

/**
 * 数据对比默认使用`equals`函数对比, 你可以为数据手动实现equals函数来修改对比逻辑. 推荐定义数据为 data class, 因其会根据构造参数自动生成equals
 * @param newModels 新的数据
 * @param oldModels 旧的数据
 */
class DefaultDifferCallback(private val newModels: List<Any?>?, private val oldModels: List<Any?>?) : DiffUtil.Callback() {
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
            oldModels[oldItemPosition] == newModels[newItemPosition]
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (oldModels == null || newModels == null) {
            false
        } else {
            oldModels[oldItemPosition] == newModels[newItemPosition]
        }
    }
}