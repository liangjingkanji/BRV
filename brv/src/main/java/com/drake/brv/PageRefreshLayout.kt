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

package com.drake.brv

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnLayoutChangeListener
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.listener.OnBindViewHolderListener
import com.drake.brv.listener.OnMultiStateListener
import com.drake.brv.utils.bindingAdapter
import com.drake.statelayout.StateConfig
import com.drake.statelayout.StateConfig.errorLayout
import com.drake.statelayout.StateLayout
import com.drake.statelayout.Status
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshComponent
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.scwang.smart.refresh.layout.simple.SimpleBoundaryDecider


/**
 * 扩展SmartRefreshLayout的功能
 *
 * 功能:
 * - 下拉刷新u
 * - 上拉加载
 * - 拉取
 * - 预加载
 * - 预拉取
 * - 自动分页
 * - 添加数据
 * - 缺省状态页
 */
@Suppress("UNUSED_PARAMETER")
open class PageRefreshLayout : SmartRefreshLayout, OnRefreshLoadMoreListener {

    companion object {
        var startIndex = 1 // 全局分页初始索引
        var preloadIndex = 3 // 全局预加载索引
    }

    var index = startIndex // 分页索引
    var contentView: View? = null
    var stateLayout: StateLayout? = null // 可以指定缺省页

    // 监听onBindViewHolder事件
    var onBindViewHolderListener = object : OnBindViewHolderListener {
        override fun onBindViewHolder(
            rv: RecyclerView,
            adapter: BindingAdapter,
            holder: BindingAdapter.BindingViewHolder,
            position: Int
        ) {
            if (mEnableLoadMore && !mFooterNoMoreData && preloadIndex != -1 && (adapter.itemCount - preloadIndex <= position)) {
                post {
                    if (state == RefreshState.None) {
                        notifyStateChanged(RefreshState.Loading)
                        onLoadMore(this@PageRefreshLayout)
                    }
                }
            }
        }
    }

    private var stateChanged = false
    private var finishInflate = false
    private var trigger = false
    private var realEnableLoadMore = false
    private var realEnableRefresh = false
    private var onRefresh: (PageRefreshLayout.() -> Unit)? = null
    private var onLoadMore: (PageRefreshLayout.() -> Unit)? = null


    var upFetchEnabled = false
        set(value) {
            if (value == field) return
            field = value
            if (field) {
                setEnableRefresh(false)
                setEnableNestedScroll(false)
                setEnableAutoLoadMore(true)
                setEnableScrollContentWhenLoaded(true)
                setScrollBoundaryDecider(object : SimpleBoundaryDecider() {
                    override fun canLoadMore(content: View?): Boolean {
                        return super.canRefresh(content)
                    }
                })
                layout.scaleY = -1F
                mRefreshContent.view.scaleY = -1F
                refreshFooter?.view?.scaleY = -1F
            } else {
                setEnableNestedScroll(false)
                setScrollBoundaryDecider(SimpleBoundaryDecider())
                layout.scaleY = 1F
                contentView?.scaleY = 1F
                refreshFooter?.view?.scaleY = 1F
            }
        }

    // <editor-fold desc="构造函数">

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PageRefreshLayout)

        try {
            stateEnabled =
                attributes.getBoolean(R.styleable.PageRefreshLayout_stateEnabled, stateEnabled)

            mEnableLoadMoreWhenContentNotFull = false
            mEnableLoadMoreWhenContentNotFull = attributes.getBoolean(
                R.styleable.SmartRefreshLayout_srlEnableLoadMoreWhenContentNotFull,
                mEnableLoadMoreWhenContentNotFull
            )

            emptyLayout =
                attributes.getResourceId(R.styleable.PageRefreshLayout_empty_layout, View.NO_ID)
            errorLayout =
                attributes.getResourceId(R.styleable.PageRefreshLayout_error_layout, View.NO_ID)
            loadingLayout =
                attributes.getResourceId(R.styleable.PageRefreshLayout_loading_layout, View.NO_ID)
        } finally {
            attributes.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (upFetchEnabled) {
            layout.scaleY = -1F
            contentView?.scaleY = -1F
            refreshFooter?.view?.scaleY = -1F
        }
    }

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    internal fun initialize() {
        setOnRefreshLoadMoreListener(this)

        realEnableLoadMore = mEnableLoadMore
        realEnableRefresh = mEnableRefresh

        if (realEnableLoadMore) {
            super.setEnableLoadMore(false)
        }

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
            initStateLayout()
        }

        val rv = contentView
        if (rv is RecyclerView) {
            rv.addOnLayoutChangeListener(OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                val adapter = rv.adapter
                if (adapter is BindingAdapter) {
                    adapter.onBindViewHolders.add(onBindViewHolderListener)
                }
            })
        }
    }

    // </editor-fold>


    // <editor-fold desc="刷新数据">

    /**
     * 指定列表位置(倒序索引)显示自动预加载
     */
    var preloadIndex = PageRefreshLayout.preloadIndex

    /**
     * 触发刷新 (不包含下拉动画)
     */
    fun refresh() {
        if (state == RefreshState.None) {
            notifyStateChanged(RefreshState.Refreshing)
            onRefresh(this)
        }
    }

    /**
     * 自动分页自动加载数据, 自动判断当前属于下拉刷新还是上拉加载更多
     *
     * 此函数每次调用会导致[index]递增或者下拉刷新会导致[index]等于[startIndex]
     * @param data 数据集
     * @param adapter 假设PageRefreshLayout不能直接包裹RecyclerView, 然后也想使用自动分页, 请指定此参数, 因为自动分页需要[BindingAdapter]实例
     * @param hasMore 在函数参数中返回布尔类型来判断是否还存在下一页数据, 默认值true表示始终存在
     * @param isEmpty 返回true表示数据为空, 将显示缺省页 -> 空布局, 默认以[data.isNullOrEmpty()]则为空
     */
    fun addData(
        data: List<Any?>?,
        adapter: BindingAdapter? = null,
        isEmpty: () -> Boolean = { data.isNullOrEmpty() },
        hasMore: BindingAdapter.() -> Boolean = { true }
    ) {

        val adjustAdapter = when {
            adapter != null -> adapter
            contentView is RecyclerView -> (contentView as RecyclerView).bindingAdapter
            else -> throw UnsupportedOperationException("Use parameter [adapter] on [addData] function or PageRefreshLayout direct wrap RecyclerView")
        }

        val isRefreshState = state == RefreshState.Refreshing

        if (isRefreshState) {
            adjustAdapter.models = data
            if (isEmpty()) {
                showEmpty()
                return
            }
        } else {
            adjustAdapter.addModels(data)
        }

        val hasMoreResult = adjustAdapter.hasMore()
        index += 1

        if (isRefreshState) showContent(hasMoreResult) else finish(true, hasMoreResult)
    }

    // </editor-fold>


    // <editor-fold desc="生命周期">

    fun onError(block: View.(Any?) -> Unit): PageRefreshLayout {
        stateLayout?.onError(block)
        return this
    }

    fun onEmpty(block: View.(Any?) -> Unit): PageRefreshLayout {
        stateLayout?.onEmpty(block)
        return this
    }

    fun onLoading(block: View.(Any?) -> Unit): PageRefreshLayout {
        stateLayout?.onLoading(block)
        return this
    }

    fun onContent(block: View.(Any?) -> Unit): PageRefreshLayout {
        stateLayout?.onContent(block)
        return this
    }

    fun onRefresh(block: PageRefreshLayout.() -> Unit): PageRefreshLayout {
        onRefresh = block
        return this
    }

    fun onLoadMore(block: PageRefreshLayout.() -> Unit): PageRefreshLayout {
        onLoadMore = block
        return this
    }


    /**
     * 监听多种状态, 不会拦截已有的刷新(onRefresh)和加载生命周期(onLoadMore)
     * @param onMultiStateListener OnMultiStateListener
     * @return PageRefreshLayout
     */
    fun setOnMultiStateListener(onMultiStateListener: OnMultiStateListener): PageRefreshLayout {
        setOnMultiListener(onMultiStateListener)
        return this
    }

    // </editor-fold>


    /**
     * 关闭下拉加载|上拉刷新
     * @param success Boolean 刷新/加载是否成功
     * @param hasMore 是否存在分页, 如果不存在分页当布局不满一页时会关闭Footer
     */
    fun finish(success: Boolean = true, hasMore: Boolean = false) {

        if (trigger) {
            stateChanged = true
        }

        val currentState = state

        if (success) {
            loaded = true
        }

        if (currentState == RefreshState.Refreshing) {
            if (hasMore) finishRefresh(success) else finishRefreshWithNoMoreData()
            if (realEnableRefresh) super.setEnableRefresh(true)
            if (!mEnableLoadMoreWhenContentNotFull) {
                setEnableLoadMoreWhenContentNotFull(hasMore || !mFooterNoMoreData)
            }
        } else {
            if (hasMore) finishLoadMore(success) else finishLoadMoreWithNoMoreData()
        }

        if (realEnableLoadMore) {
            if (stateEnabled && stateLayout?.status != Status.CONTENT) {
                super.setEnableLoadMore(false)
            } else {
                super.setEnableLoadMore(true)
            }
        }
    }

    /**
     * 用于网络请求的触发器, 作用于暂停/继续缺省状态变化
     * 开发者无需关心该函数
     */
    fun trigger(): Boolean {
        trigger = !trigger
        if (!trigger) stateChanged = false
        return trigger
    }

    // <editor-fold desc="缺省页">

    /**
     * 标记是否已加载, 已加载后将不再显示错误页面
     */
    var loaded = false

    /**
     * 启用缺省页
     */
    var stateEnabled = true
        set(value) {
            field = value
            if (finishInflate) {
                if (field && stateLayout == null) {
                    initStateLayout()
                } else if (!field) {
                    stateLayout?.showContent()
                }
            }
        }

    var emptyLayout = View.NO_ID
        set(value) {
            field = value
            stateLayout?.emptyLayout = value
        }
    var errorLayout = View.NO_ID
        set(value) {
            field = value
            stateLayout?.errorLayout = value
        }
    var loadingLayout = View.NO_ID
        set(value) {
            field = value
            stateLayout?.loadingLayout = value
        }

    /**
     * 设置[errorLayout]中的视图点击后会执行[StateLayout.showLoading]
     * 并且500ms内防重复点击
     */
    fun setRetryIds(@IdRes vararg ids: Int): PageRefreshLayout {
        stateLayout?.setRetryIds(*ids)
        return this
    }

    fun showEmpty(tag: Any? = null) {
        if (stateEnabled) stateLayout?.showEmpty(tag)
        finish()
    }


    /**
     * 加载成功以后不会再显示错误页面, 除非指定强制显示
     *
     * @param force 强制显示错误页面
     */
    fun showError(tag: Any? = null, force: Boolean = false) {
        if (stateEnabled && (force || !loaded)) {
            loaded = false
            stateLayout?.showError(tag)
        }
        finish(false)
    }

    /**
     * 有网则显示加载中缺省页, 无网络直接显示错误缺省页
     * @param tag 传递参数将被[onLoading]接收
     * @param refresh 是否回调[onRefresh]
     */
    fun showLoading(tag: Any? = null, refresh: Boolean = true) {
        if (stateEnabled) stateLayout?.showLoading(tag, refresh = refresh)
    }

    fun showContent(hasMore: Boolean = false, tag: Any? = null) {
        if (trigger && stateChanged) return
        if (stateEnabled) stateLayout?.showContent(tag)
        finish(hasMore = hasMore)
    }

    // </editor-fold>

    //<editor-fold desc="覆写函数">

    override fun setEnableLoadMore(enabled: Boolean): RefreshLayout {
        realEnableLoadMore = enabled
        return super.setEnableLoadMore(enabled)
    }

    override fun setEnableRefresh(enabled: Boolean): RefreshLayout {
        realEnableRefresh = enabled
        return super.setEnableRefresh(enabled)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initialize()
        finishInflate = true
    }


    override fun onLoadMore(refreshLayout: RefreshLayout) {
        if (onLoadMore == null) {
            onRefresh?.invoke(this)
        } else {
            onLoadMore?.invoke(this)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        setNoMoreData(false)
        index = startIndex
        onRefresh?.invoke(this)
    }
    //</editor-fold>

    /**
     * 替换成缺省页
     */
    private fun initStateLayout() {

        if (StateConfig.errorLayout == View.NO_ID && errorLayout == View.NO_ID &&
            StateConfig.emptyLayout == View.NO_ID && emptyLayout == View.NO_ID &&
            StateConfig.loadingLayout == View.NO_ID && loadingLayout == View.NO_ID
        ) {
            stateEnabled = false
            return
        }

        stateLayout = StateLayout(context).let {
            removeView(contentView)
            it.addView(contentView)
            it.setContentView(contentView!!)
            setRefreshContent(this)

            it.emptyLayout = emptyLayout
            it.errorLayout = errorLayout
            it.loadingLayout = loadingLayout

            it.onRefresh {
                if (realEnableRefresh) super.setEnableRefresh(false)
                if (realEnableLoadMore) super.setEnableLoadMore(false)
                notifyStateChanged(RefreshState.Refreshing)
                onRefresh(this@PageRefreshLayout)
            }
        }
    }

}
