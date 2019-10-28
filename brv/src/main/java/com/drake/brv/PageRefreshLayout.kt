/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：9/12/19 1:55 PM
 */

package com.drake.brv

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.listener.OnMultiStateListener
import com.drake.brv.utils.bindingAdapter
import com.drake.statelayout.StateLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshComponent
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener


/**
 * 扩展SmartRefreshLayout
 *
 * 功能:
 * - 下拉刷新
 * - 上拉加载
 * - 分页加载
 * - 添加数据
 * - 缺省状态页
 */
@Suppress("UNUSED_PARAMETER")
open class PageRefreshLayout : SmartRefreshLayout, OnRefreshLoadMoreListener {


    var emptyLayout = View.NO_ID
    var errorLayout = View.NO_ID
    var loadingLayout = View.NO_ID
    var index = startIndex
    var hasMore = true
    var stateEnabled = true // 启用缺省页 (如果设置布局或者监听器会自动开启)


    companion object {
        var startIndex = 1
    }


    private var adapter: BindingAdapter? = null
    private var contentView: View? = null
    private var state: StateLayout? = null

    private var onEmpty: (View.() -> Unit)? = null
    private var onError: (View.() -> Unit)? = null
    private var onLoading: (View.() -> Unit)? = null
    private var onRefresh: (PageRefreshLayout.() -> Unit)? = null
    private var onLoadMore: (PageRefreshLayout.() -> Unit)? = null


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PageRefreshLayout)

        try {
            stateEnabled = attributes.getBoolean(R.styleable.PageRefreshLayout_stateEnabled, false)
            emptyLayout = attributes.getResourceId(
                R.styleable.PageRefreshLayout_empty_layout,
                View.NO_ID
            )
            errorLayout = attributes.getResourceId(
                R.styleable.PageRefreshLayout_error_layout,
                View.NO_ID
            )

            loadingLayout = attributes.getResourceId(
                R.styleable.PageRefreshLayout_loading_layout,
                View.NO_ID
            )
        } finally {
            attributes.recycle()
        }

    }


    // <editor-fold desc="刷新数据">

    /**
     * 触发刷新
     */
    fun refresh() {
        onRefresh(this)
    }


    /**
     * 直接接受数据, 自动判断刷新还是加载更多.
     * @param data List<Any?>? 数据集
     * @param hasMore [@kotlin.ExtensionFunctionType] Function1<PageRefreshLayout, Boolean> 在函数参数中返回布尔类型来判断是否存在更多页
     */
    fun addData(data: List<Any?>?, hasMore: PageRefreshLayout.() -> Boolean) {

        this.hasMore = this.hasMore()

        if (data.isNullOrEmpty() && getState() == RefreshState.Refreshing) {
            showEmpty()
            return
        } else {
            showContent()
        }

        if (adapter == null && contentView != null && contentView is RecyclerView && (contentView as RecyclerView).adapter is BindingAdapter) {
            adapter = (contentView as RecyclerView).bindingAdapter
        }

        adapter?.let {
            if (index == startIndex) {
                adapter!!.models = data
            } else {
                adapter!!.addModels(data)
            }

            index++
        }
    }

    // </editor-fold>


    // <editor-fold desc="生命周期">

    fun onError(block: View.() -> Unit) {
        onError = block
    }

    fun onEmpty(block: View.() -> Unit) {
        onEmpty = block
    }

    fun onLoading(block: View.() -> Unit) {
        onLoading = block
    }

    fun onRefresh(block: PageRefreshLayout.() -> Unit) {
        onRefresh = block
    }

    fun onLoadMore(block: PageRefreshLayout.() -> Unit) {
        onLoadMore = block
    }


    /**
     * 监听多种状态, 不会拦截已有的刷新(onRefresh)和加载生命周期(onLoadMore)
     * @param onMultiStateListener OnMultiStateListener
     * @return PageRefreshLayout
     */
    fun setOnMultiStateListener(onMultiStateListener: OnMultiStateListener) {
        setOnMultiListener(onMultiStateListener)
    }

    // </editor-fold>


    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }

    internal fun init() {

        setOnRefreshLoadMoreListener(this)


        if (contentView == null) {
            for (i in 0 until childCount) {
                val view = getChildAt(i)
                if (view !is RefreshComponent) {
                    contentView = view
                    break
                }
            }
        } else return


        if (stateEnabled) {

            state = StateLayout(context)
            state?.let {

                removeView(contentView)
                state!!.addView(contentView)
                state!!.setContentView(contentView!!)
                setRefreshContent(state!!)

                it.emptyLayout = emptyLayout
                it.errorLayout = errorLayout
                it.loadingLayout = loadingLayout

                it.onLoading(onLoading)
                it.onEmpty(onEmpty)
                it.onError(onError)
            }
        }
    }


    /**
     * 关闭下拉加载|上拉刷新
     * @param success Boolean 刷新结果 true: 成功 false: 失败
     */
    fun finish(success: Boolean = true) {

        if (getState() == RefreshState.Refreshing) {
            finishRefresh(success)
        } else if (getState() == RefreshState.Loading) {
            if (hasMore) {
                finishLoadMore(success)
            } else {
                finishLoadMoreWithNoMoreData()
            }
        } else {
            setEnableRefresh(true)
        }
    }


    // <editor-fold desc="状态变更">


    fun showEmpty() {
        state?.showEmpty()
        finish()
    }


    fun showError() {
        state?.showError()
        finish(false)
    }

    fun showLoading() {
        state?.showLoading()
        setEnableRefresh(false)
        onRefresh(this)
    }

    fun showContent() {
        state?.showContent()
        finish()
    }

    // </editor-fold>

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        if (onLoadMore == null) {
            onRefresh?.invoke(this)
        } else {
            onLoadMore?.invoke(this)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        index = startIndex

        setNoMoreData(false)
        onRefresh?.invoke(this)
    }

}
