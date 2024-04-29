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
import android.util.AttributeSet
import android.view.View
import android.view.View.OnLayoutChangeListener
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.drake.brv.listener.OnBindViewHolderListener
import com.drake.brv.listener.OnMultiStateListener
import com.drake.brv.utils.bindingAdapter
import com.drake.statelayout.StateChangedHandler
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
 * 扩展[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)的功能
 *
 * ## 功能
 * 1. 下拉刷新
 * 2. 上拉加载
 * 3. 拉取
 * 4. 预加载
 * 5. 预拉取
 * 6. 自动分页
 * 7. 添加数据
 * 8. 缺省状态页
 */
@Suppress("UNUSED_PARAMETER")
open class PageRefreshLayout : SmartRefreshLayout, OnRefreshLoadMoreListener {

    companion object {
        /** 全局分页初始索引 */
        var startIndex = 1

        /** 全局预加载索引 */
        var preloadIndex = 3

        /**
         * 是否显示空缺省页时启用下拉刷新
         */
        var refreshEnableWhenEmpty = true

        /**
         * 是否显示错误缺省页时启用下拉刷新
         */
        var refreshEnableWhenError = true
    }

    /** 分页索引 */
    var index = startIndex

    /** 缺省页视图 */
    var stateLayout: StateLayout? = null

    /**
     * 缺省页ID, 默认不配置会自动创建StateLayout并且包裹PageRefreshLayout的RefreshContent
     */
    var stateLayoutId: Int = View.NO_ID

    /** 手动指定列表, 用于获取[BindingAdapter]来让[addData]方法处理自动分页, 如果[PageRefreshLayout]直接嵌套[RecyclerView]本方法没必要调用 */
    var rv: RecyclerView? = null

    /**
     * 列表ID, 会根据ID自动赋值[rv]
     */
    var recyclerViewId: Int = View.NO_ID

    /** 变更为下拉加载更多, 上拉刷新 */
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
            } else {
                setEnableNestedScroll(false)
                setScrollBoundaryDecider(SimpleBoundaryDecider())
            }
            if (finishInflate) {
                reverseContentView()
            }
        }

    /** 监听onBindViewHolder事件 */
    var onBindViewHolderListener = object : OnBindViewHolderListener {
        override fun onBindViewHolder(
            rv: RecyclerView,
            adapter: BindingAdapter,
            holder: BindingAdapter.BindingViewHolder,
            position: Int,
        ) {
            if (mEnableLoadMore && !mFooterNoMoreData &&
                rv.scrollState != SCROLL_STATE_IDLE &&
                preloadIndex != -1 &&
                (adapter.itemCount - preloadIndex <= position)
            ) {
                post {
                    if (state == RefreshState.None) {
                        notifyStateChanged(RefreshState.Loading)
                        onLoadMore(this@PageRefreshLayout)
                    }
                }
            }
        }
    }

    /** 内容视图 */
    private var refreshContent: View? = null
    private var stateChanged = false
    private var finishInflate = false
    private var trigger = false
    private var realEnableLoadMore = false
    private var realEnableRefresh = false
    private var onRefresh: (PageRefreshLayout.() -> Unit)? = null
    private var onLoadMore: (PageRefreshLayout.() -> Unit)? = null

    // <editor-fold desc="构造函数">

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PageRefreshLayout)

        try {
            upFetchEnabled =
                attributes.getBoolean(R.styleable.PageRefreshLayout_page_upFetchEnabled, upFetchEnabled)
            stateEnabled =
                attributes.getBoolean(R.styleable.PageRefreshLayout_stateEnabled, stateEnabled)
            stateLayoutId =
                attributes.getResourceId(R.styleable.PageRefreshLayout_page_state, stateLayoutId)
            recyclerViewId =
                attributes.getResourceId(R.styleable.PageRefreshLayout_page_rv, recyclerViewId)


            mEnableLoadMoreWhenContentNotFull = false
            mEnableLoadMoreWhenContentNotFull = attributes.getBoolean(
                R.styleable.SmartRefreshLayout_srlEnableLoadMoreWhenContentNotFull,
                mEnableLoadMoreWhenContentNotFull
            )

            emptyLayout =
                attributes.getResourceId(R.styleable.PageRefreshLayout_empty_layout, emptyLayout)
            errorLayout =
                attributes.getResourceId(R.styleable.PageRefreshLayout_error_layout, errorLayout)
            loadingLayout =
                attributes.getResourceId(R.styleable.PageRefreshLayout_loading_layout, loadingLayout)
        } finally {
            attributes.recycle()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        reverseContentView()
    }

    /** 反转内容视图y轴 */
    private fun reverseContentView() {
        val scaleValue = if (upFetchEnabled) -1F else 1F
        layout.scaleY = scaleValue
        mRefreshContent.view.scaleY = scaleValue
        refreshFooter?.view?.scaleY = scaleValue
    }

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    internal fun initialize() {
        rv = findViewById(recyclerViewId)
        setOnRefreshLoadMoreListener(this)
        realEnableLoadMore = mEnableLoadMore
        realEnableRefresh = mEnableRefresh
        if (refreshContent == null) {
            for (i in 0 until childCount) {
                val view = getChildAt(i)
                if (view !is RefreshComponent) {
                    refreshContent = view
                    break
                }
            }
        } else return

        if (stateEnabled) {
            initializeState()
        }

        val rv = if (this.rv == null) refreshContent else this.rv
        if (rv is RecyclerView) {
            rv.addOnLayoutChangeListener(
                OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
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

    /** 初次调用等效于[showLoading]. 当加载完毕以后, 再次调用等效[refresh] */
    fun refreshing(tag: Any? = null) {
        if (loaded) {
            refresh()
        } else {
            showLoading(tag)
        }
    }

    /**
     * 自动分页自动加载数据, 自动判断当前属于下拉刷新还是上拉加载更多
     *
     * ## 实现
     * 当[getState]等于[RefreshState.Refreshing]或者[index]等于[startIndex]会判断为下拉刷新
     * [index]初始值为[startIndex], 每次调用本方法会将[index]递增, 下拉刷新会将[index]重置为[startIndex]
     *
     * ## 注意事项
     * 请勿每次给[data]赋值同一个集合对象, 因为为了保证rv持有数据集合为一个对象, 覆盖数据会先[clear]再[addAll]新的数据集合
     *
     * 本方法只是简化分页列表数据赋值, 如果出现特别的需求请尝试自己更新rv数据集(即不使用本方法), 比如使用[BindingAdapter.models]
     *
     * @param data 数据集
     * @param adapter 假设PageRefreshLayout不能直接包裹RecyclerView, 请指定此参数. 但更推荐在布局中使用app:page_rv来指定列表
     * @param hasMore 在函数参数中返回布尔类型来判断是否还存在下一页数据, 默认值true表示始终存在
     * @param isEmpty 返回true表示数据为空, 将显示缺省页 -> 空布局, 默认以[data.isNullOrEmpty()]则为空
     */
    fun addData(
        data: List<Any?>?,
        adapter: BindingAdapter? = null,
        isEmpty: () -> Boolean = { data.isNullOrEmpty() },
        hasMore: BindingAdapter.() -> Boolean = { true },
    ) {
        val refreshContent = this.refreshContent
        val rv = this.rv
        val adapterTemp = when {
            adapter != null -> adapter
            rv != null -> rv.bindingAdapter
            refreshContent is RecyclerView -> refreshContent.bindingAdapter
            else -> throw UnsupportedOperationException("Use parameter [adapter] on [addData] function or PageRefreshLayout direct wrap RecyclerView")
        }

        val isRefreshState = state == RefreshState.Refreshing || index == startIndex

        if (isRefreshState) {
            val models = adapterTemp.models
            if (models == null) {
                adapterTemp.models = data
            } else {
                if (models is MutableList) {
                    val size = models.size
                    models.clear()
                    adapterTemp.checkedPosition.clear()
                    if (data.isNullOrEmpty()) {
                        adapterTemp.notifyItemRangeRemoved(adapterTemp.headerCount, size)
                    } else {
                        adapterTemp.addModels(data)
                    }
                }
            }
            if (isEmpty()) {
                showEmpty()
                return
            }
        } else {
            adapterTemp.addModels(data)
        }

        val hasMoreResult = adapterTemp.hasMore()
        index += 1

        if (isRefreshState) showContent(hasMoreResult) else finish(true, hasMoreResult)
    }

    // </editor-fold>


    // <editor-fold desc="生命周期">

    /**
     * 当错误缺省页显示时回调
     * @see showError
     * @see StateConfig.onError
     */
    fun onError(block: View.(Any?) -> Unit): PageRefreshLayout {
        stateLayout?.onError(block)
        return this
    }

    /**
     * 当空缺省页显示时回调
     * @see showEmpty
     * @see StateConfig.onEmpty
     */
    fun onEmpty(block: View.(Any?) -> Unit): PageRefreshLayout {
        stateLayout?.onEmpty(block)
        return this
    }

    /**
     * 当加载中缺省页显示时回调
     * @see showLoading
     * @see StateConfig.onLoading
     */
    fun onLoading(block: View.(Any?) -> Unit): PageRefreshLayout {
        stateLayout?.onLoading(block)
        return this
    }

    /**
     * 当[showContent]时会回调该函数参数, 一般将网络请求等异步操作放入其中
     * @see showContent
     * @see StateConfig.onContent
     */
    fun onContent(block: View.(Any?) -> Unit): PageRefreshLayout {
        stateLayout?.onContent(block)
        return this
    }

    /**
     * 下拉刷新回调
     * 如果不设置[onLoadMore], 上拉加载也会执行此方法
     *
     * 触发方式有以下三种:
     * @see showLoading 加载中缺省页
     * @see autoRefresh 下拉刷新
     * @see refresh 静默刷新
     */
    fun onRefresh(block: PageRefreshLayout.() -> Unit): PageRefreshLayout {
        onRefresh = block
        return this
    }

    /**
     * 上拉加载回调
     * 如果不执行该方法, 上拉加载会自动触发[onRefresh]
     */
    fun onLoadMore(block: PageRefreshLayout.() -> Unit): PageRefreshLayout {
        onLoadMore = block
        return this
    }


    /**
     * 监听多种状态, 不会拦截已有的刷新(onRefresh)和加载生命周期(onLoadMore)
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
    fun finish(success: Boolean = true, hasMore: Boolean = true) {
        if (trigger) {
            stateChanged = true
        }
        val currentState = state
        if (success) loaded = true
        val stateLayout = stateLayout
        if (realEnableRefresh) {
            when {
                stateLayout == null -> super.setEnableRefresh(true)
                (stateLayout.status == Status.EMPTY && !refreshEnableWhenEmpty) ||
                        (stateLayout.status == Status.ERROR && !refreshEnableWhenError) -> {
                    super.setEnableRefresh(false)
                }

                else -> super.setEnableRefresh(true)
            }
        }
        if (currentState == RefreshState.Refreshing) {
            if (hasMore) finishRefresh(success) else finishRefreshWithNoMoreData()
        } else {
            if (hasMore) finishLoadMore(success) else finishLoadMoreWithNoMoreData()
        }
        // fix: 页面未创建完成立即finish, 会导致下拉动画无法回弹以及刷新无效
        if (isRefreshing) {
            notifyStateChanged(RefreshState.None)
        }
    }

    /**
     * 复写函数实现以下
     * 1. 当不满一屏幕的时候默认不显示上拉加载更多
     * 2. 当下拉刷新未成功完成不显示上拉加载
     */
    override fun finishRefresh(
        delayed: Int,
        success: Boolean,
        noMoreData: Boolean?
    ): RefreshLayout {
        super.finishRefresh(delayed, success, noMoreData)
        if (!mEnableLoadMoreWhenContentNotFull) {
            setEnableLoadMoreWhenContentNotFull(noMoreData == false || !mFooterNoMoreData)
        }
        if (realEnableLoadMore) {
            if (stateEnabled && stateLayout?.status != Status.CONTENT) {
                super.setEnableLoadMore(false)
            } else {
                super.setEnableLoadMore(true)
            }
        }
        return this
    }

    override fun setNoMoreData(noMoreData: Boolean): RefreshLayout {
        if (mRefreshFooter != null && mRefreshContent != null) {
            super.setNoMoreData(noMoreData)
        }
        return this
    }

    /**
     * 复写函数实现以下
     * 当下拉刷新未成功完成不显示上拉加载
     */
    override fun finishLoadMore(
        delayed: Int,
        success: Boolean,
        noMoreData: Boolean
    ): RefreshLayout {
        super.finishLoadMore(delayed, success, noMoreData)
        if (realEnableLoadMore) {
            if (stateEnabled && stateLayout?.status != Status.CONTENT) {
                super.setEnableLoadMore(false)
            } else {
                super.setEnableLoadMore(true)
            }
        }
        return this
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
                    initializeState()
                } else if (!field) {
                    stateLayout?.showContent()
                }
            }
        }

    /** 空页面布局 */
    var emptyLayout = View.NO_ID
        set(value) {
            field = value
            stateLayout?.emptyLayout = value
        }

    /** 错误页面布局 */
    var errorLayout = View.NO_ID
        set(value) {
            field = value
            stateLayout?.errorLayout = value
        }

    /** 加载中页面布局 */
    var loadingLayout = View.NO_ID
        set(value) {
            field = value
            stateLayout?.loadingLayout = value
        }

    /** 处理缺省页状态变更 */
    var stateChangedHandler: StateChangedHandler
        get() = stateLayout!!.stateChangedHandler
        set(value) {
            stateLayout!!.stateChangedHandler = value
        }

    /**
     * 是否显示空缺省页时启用下拉刷新
     */
    var refreshEnableWhenEmpty = Companion.refreshEnableWhenEmpty

    /**
     * 是否显示错误缺省页时启用下拉刷新
     */
    var refreshEnableWhenError = Companion.refreshEnableWhenError

    /**
     * 设置[errorLayout]中的视图点击后会执行[StateLayout.showLoading]
     * 并且500ms内防重复点击
     */
    fun setRetryIds(@IdRes vararg ids: Int): PageRefreshLayout {
        stateLayout?.setRetryIds(*ids)
        return this
    }

    /**
     * 显示空缺省页
     * @param tag 传递参数将被[onEmpty]接受
     */
    fun showEmpty(tag: Any? = null) {
        if (stateEnabled) stateLayout?.showEmpty(tag)
        finish(hasMore = false)
    }


    /**
     * 加载成功以后不会再显示错误页面, 除非指定强制显示
     *
     * @param tag 传递参数将被[onError]接收
     * @param force 强制显示错误页面, 因为如果页面已经显示过内容那么再次[showError]是无效的(避免覆盖已加载数据)
     */
    fun showError(tag: Any? = null, force: Boolean = false) {
        if (stateEnabled && (force || !loaded || stateLayout?.status != Status.CONTENT)) {
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

    /**
     * 显示内容页
     * @param hasMore 否能上拉加载更多
     * @param tag 传递参数将被[onContent]接收
     */
    fun showContent(hasMore: Boolean = true, tag: Any? = null) {
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
        initialize()
        super.onFinishInflate()
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
        if (realEnableLoadMore) super.setEnableLoadMore(false)
        index = startIndex
        onRefresh?.invoke(this)
    }
    //</editor-fold>

    /**
     * 初始化缺省页
     */
    private fun initializeState() {
        if (StateConfig.errorLayout == View.NO_ID && errorLayout == View.NO_ID &&
            StateConfig.emptyLayout == View.NO_ID && emptyLayout == View.NO_ID &&
            StateConfig.loadingLayout == View.NO_ID && loadingLayout == View.NO_ID
        ) {
            stateEnabled = false
            return
        }

        if (stateLayout == null) {
            stateLayout = if (stateLayoutId == View.NO_ID) {
                StateLayout(context).also { state ->
                    removeView(refreshContent)
                    state.addView(refreshContent)
                    state.setContent(refreshContent!!)
                    setRefreshContent(state)
                }
            } else {
                findViewById(stateLayoutId)
            }
        }
        stateLayout?.let { state ->
            state.emptyLayout = emptyLayout
            state.errorLayout = errorLayout
            state.loadingLayout = loadingLayout
            state.onRefresh {
                if (realEnableRefresh) super.setEnableRefresh(false)
                notifyStateChanged(RefreshState.Refreshing)
                onRefresh(this@PageRefreshLayout)
            }
        }
    }

}
