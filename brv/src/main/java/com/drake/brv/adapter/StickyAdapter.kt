package com.drake.brv.adapter

import com.drake.brv.listener.OnStickyChangeListener

/**
 * Created by jay on 2017/12/4 上午10:52
 *
 * 为RecyclerView添加悬停效果, 由 [androidx.recyclerview.widget.RecyclerView.Adapter] 实现该接口
 *
 * 原项目地址
 * @link https://github.com/Doist/RecyclerViewExtensions/blob/master/StickyAdapter
 */
interface StickyAdapter {

    var onStickyChangeListener: OnStickyChangeListener?

    /**
     * @return 是否需要悬停
     */
    fun isSticky(position: Int): Boolean
}