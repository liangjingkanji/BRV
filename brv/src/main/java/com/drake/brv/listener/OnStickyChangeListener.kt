package com.drake.brv.listener

import android.view.View

/**
 * 由RecyclerView.Adapter实现该接口
 */
interface OnStickyChangeListener {
    /**
     * 当条目悬停时
     * [detachSticky] 该函数可以进行还原操作
     */
    fun attachSticky(v: View)

    /**
     * 条目悬停结束时
     * 一般用于还原[attachSticky]函数的操作
     */
    fun detachSticky(v: View)
}