[brv](../../index.md) / [com.drake.brv.binding](../index.md) / [ObservableIml](./index.md)

# ObservableIml

`interface ObservableIml : Observable`

A convenience class that implements [android.databinding.Observable](#) interface and provides
[.notifyPropertyChanged](#) and [.notifyChange](#) methods.

### Properties

| Name | Summary |
|---|---|
| [registry](registry.md) | `abstract val registry: PropertyChangeRegistry` |

### Functions

| Name | Summary |
|---|---|
| [addOnPropertyChangedCallback](add-on-property-changed-callback.md) | `open fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [notifyChange](notify-change.md) | Notifies listeners that all properties of this instance have changed.`open fun notifyChange(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [notifyPropertyChanged](notify-property-changed.md) | Notifies listeners that a specific property has changed. The getter for the property that changes should be marked with [Bindable](#) to generate a field in `BR` to be used as `fieldId`.`open fun notifyPropertyChanged(fieldId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [removeOnPropertyChangedCallback](remove-on-property-changed-callback.md) | `open fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
