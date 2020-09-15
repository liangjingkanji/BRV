[brv](../../index.md) / [com.drake.brv](../index.md) / [PageRefreshLayout](./index.md)

# PageRefreshLayout

`open class PageRefreshLayout : SmartRefreshLayout, OnRefreshLoadMoreListener`

扩展SmartRefreshLayout的功能

功能:

* 下拉刷新u
* 上拉加载
* 拉取
* 预加载
* 预拉取
* 自动分页
* 添加数据
* 缺省状态页

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `PageRefreshLayout(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`)`<br>`PageRefreshLayout(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`, attrs: `[`AttributeSet`](https://developer.android.com/reference/android/util/AttributeSet.html)`?)` |

### Properties

| Name | Summary |
|---|---|
| [contentView](content-view.md) | `var contentView: `[`View`](https://developer.android.com/reference/android/view/View.html)`?` |
| [emptyLayout](empty-layout.md) | `var emptyLayout: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [errorLayout](error-layout.md) | `var errorLayout: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [index](--index--.md) | `var index: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [loaded](loaded.md) | 标记是否已加载, 已加载后将不再显示错误页面`var loaded: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [loadingLayout](loading-layout.md) | `var loadingLayout: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [onBindViewHolderListener](on-bind-view-holder-listener.md) | `var onBindViewHolderListener: `[`OnBindViewHolderListener`](../../com.drake.brv.listener/-on-bind-view-holder-listener/index.md) |
| [preloadIndex](preload-index.md) | 指定列表位置(倒序索引)显示自动预加载`var preloadIndex: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [stateEnabled](state-enabled.md) | 启用缺省页`var stateEnabled: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [stateLayout](state-layout.md) | `var stateLayout: StateLayout?` |
| [upFetchEnabled](up-fetch-enabled.md) | `var upFetchEnabled: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [addData](add-data.md) | 自动分页自动加载数据, 自动判断当前属于下拉刷新还是上拉加载更多`fun addData(data: `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?>?, adapter: `[`BindingAdapter`](../-binding-adapter/index.md)`? = null, isEmpty: () -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = { data.isNullOrEmpty() }, hasMore: `[`BindingAdapter`](../-binding-adapter/index.md)`.() -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = { true }): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [finish](finish.md) | 关闭下拉加载|上拉刷新`fun finish(success: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true, hasMore: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onAttachedToWindow](on-attached-to-window.md) | `open fun onAttachedToWindow(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onEmpty](on-empty.md) | `fun onEmpty(block: `[`View`](https://developer.android.com/reference/android/view/View.html)`.(`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`PageRefreshLayout`](./index.md) |
| [onError](on-error.md) | `fun onError(block: `[`View`](https://developer.android.com/reference/android/view/View.html)`.(`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`PageRefreshLayout`](./index.md) |
| [onFinishInflate](on-finish-inflate.md) | `open fun onFinishInflate(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onLoading](on-loading.md) | `fun onLoading(block: `[`View`](https://developer.android.com/reference/android/view/View.html)`.(`[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`PageRefreshLayout`](./index.md) |
| [onLoadMore](on-load-more.md) | `fun onLoadMore(block: `[`PageRefreshLayout`](./index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`PageRefreshLayout`](./index.md)<br>`open fun onLoadMore(refreshLayout: RefreshLayout): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onRefresh](on-refresh.md) | `fun onRefresh(block: `[`PageRefreshLayout`](./index.md)`.() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`PageRefreshLayout`](./index.md)<br>`open fun onRefresh(refreshLayout: RefreshLayout): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [refresh](refresh.md) | 触发刷新 (不包含下拉动画)`fun refresh(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setEnableLoadMore](set-enable-load-more.md) | `open fun setEnableLoadMore(enabled: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): RefreshLayout` |
| [setEnableRefresh](set-enable-refresh.md) | `open fun setEnableRefresh(enabled: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): RefreshLayout` |
| [setOnMultiStateListener](set-on-multi-state-listener.md) | 监听多种状态, 不会拦截已有的刷新(onRefresh)和加载生命周期(onLoadMore)`fun setOnMultiStateListener(onMultiStateListener: `[`OnMultiStateListener`](../../com.drake.brv.listener/-on-multi-state-listener/index.md)`): `[`PageRefreshLayout`](./index.md) |
| [setRetryIds](set-retry-ids.md) | 设置[errorLayout](error-layout.md)中的视图点击后会执行[StateLayout.showLoading](#) 并且500ms内防重复点击`fun setRetryIds(vararg ids: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`PageRefreshLayout`](./index.md) |
| [showContent](show-content.md) | `fun showContent(hasMore: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [showEmpty](show-empty.md) | `fun showEmpty(tag: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`? = null): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [showError](show-error.md) | 加载成功以后不会再显示错误页面, 除非指定强制显示`fun showError(tag: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`? = null, force: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = false): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [showLoading](show-loading.md) | `fun showLoading(tag: `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`? = null, refresh: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [trigger](trigger.md) | 用于网络请求的触发器, 作用于暂停/继续缺省状态变化 开发者无需关心该函数`fun trigger(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Companion Object Properties

| Name | Summary |
|---|---|
| [preloadIndex](preload-index.md) | `var preloadIndex: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [startIndex](start-index.md) | `var startIndex: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
