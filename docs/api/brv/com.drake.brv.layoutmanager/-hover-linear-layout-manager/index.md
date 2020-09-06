[brv](../../index.md) / [com.drake.brv.layoutmanager](../index.md) / [HoverLinearLayoutManager](./index.md)

# HoverLinearLayoutManager

`open class HoverLinearLayoutManager : LinearLayoutManager`

Created by jay on 2017/12/4 上午10:57

**Link**
https://github.com/Doist/RecyclerViewExtensions

### Types

| Name | Summary |
|---|---|
| [SavedState](-saved-state/index.md) | `open class SavedState : `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html) |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `HoverLinearLayoutManager(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`!)`<br>`HoverLinearLayoutManager(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`!, orientation: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, reverseLayout: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`)`<br>`HoverLinearLayoutManager(context: `[`Context`](https://developer.android.com/reference/android/content/Context.html)`!, attrs: `[`AttributeSet`](https://developer.android.com/reference/android/util/AttributeSet.html)`!, defStyleAttr: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, defStyleRes: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)` |

### Functions

| Name | Summary |
|---|---|
| [canScrollHorizontally](can-scroll-horizontally.md) | `open fun canScrollHorizontally(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [canScrollVertically](can-scroll-vertically.md) | `open fun canScrollVertically(): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [computeHorizontalScrollExtent](compute-horizontal-scroll-extent.md) | `open fun computeHorizontalScrollExtent(state: State): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [computeHorizontalScrollOffset](compute-horizontal-scroll-offset.md) | `open fun computeHorizontalScrollOffset(state: State): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [computeHorizontalScrollRange](compute-horizontal-scroll-range.md) | `open fun computeHorizontalScrollRange(state: State): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [computeScrollVectorForPosition](compute-scroll-vector-for-position.md) | `open fun computeScrollVectorForPosition(targetPosition: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`PointF`](https://developer.android.com/reference/android/graphics/PointF.html)`?` |
| [computeVerticalScrollExtent](compute-vertical-scroll-extent.md) | `open fun computeVerticalScrollExtent(state: State): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [computeVerticalScrollOffset](compute-vertical-scroll-offset.md) | `open fun computeVerticalScrollOffset(state: State): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [computeVerticalScrollRange](compute-vertical-scroll-range.md) | `open fun computeVerticalScrollRange(state: State): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [isHover](is-hover.md) | Returns true if `view` is the current hover header.`open fun isHover(view: `[`View`](https://developer.android.com/reference/android/view/View.html)`!): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [onAdapterChanged](on-adapter-changed.md) | `open fun onAdapterChanged(oldAdapter: Adapter<ViewHolder!>?, newAdapter: Adapter<ViewHolder!>?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onAttachedToWindow](on-attached-to-window.md) | `open fun onAttachedToWindow(view: RecyclerView!): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onFocusSearchFailed](on-focus-search-failed.md) | `open fun onFocusSearchFailed(focused: `[`View`](https://developer.android.com/reference/android/view/View.html)`, focusDirection: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, recycler: Recycler, state: State): `[`View`](https://developer.android.com/reference/android/view/View.html)`?` |
| [onLayoutChildren](on-layout-children.md) | `open fun onLayoutChildren(recycler: Recycler!, state: State!): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onRestoreInstanceState](on-restore-instance-state.md) | `open fun onRestoreInstanceState(state: `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)`!): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [onSaveInstanceState](on-save-instance-state.md) | `open fun onSaveInstanceState(): `[`Parcelable`](https://developer.android.com/reference/android/os/Parcelable.html)`?` |
| [scrollHorizontallyBy](scroll-horizontally-by.md) | `open fun scrollHorizontallyBy(dx: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, recycler: Recycler!, state: State!): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [scrollToPosition](scroll-to-position.md) | `open fun scrollToPosition(position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [scrollToPositionWithOffset](scroll-to-position-with-offset.md) | `open fun scrollToPositionWithOffset(position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, offset: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [scrollVerticallyBy](scroll-vertically-by.md) | `open fun scrollVerticallyBy(dy: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, recycler: Recycler!, state: State!): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [setHoverTranslationX](set-hover-translation-x.md) | Offsets the horizontal location of the hover header relative to the its default position.`open fun setHoverTranslationX(translationX: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setHoverTranslationY](set-hover-translation-y.md) | Offsets the vertical location of the hover header relative to the its default position.`open fun setHoverTranslationY(translationY: `[`Float`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [setScrollEnabled](set-scroll-enabled.md) | `open fun setScrollEnabled(enabled: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`HoverLinearLayoutManager`](./index.md)`!` |
| [smoothScrollToPosition](smooth-scroll-to-position.md) | `open fun smoothScrollToPosition(recyclerView: RecyclerView!, state: State!, position: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
