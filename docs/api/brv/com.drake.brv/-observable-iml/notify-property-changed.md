[brv](../../index.md) / [com.drake.brv](../index.md) / [ObservableIml](index.md) / [notifyPropertyChanged](./notify-property-changed.md)

# notifyPropertyChanged

`open fun notifyPropertyChanged(fieldId: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Notifies listeners that a specific property has changed. The getter for the property
that changes should be marked with [Bindable](#) to generate a field in
`BR` to be used as `fieldId`.

### Parameters

`fieldId` - The generated BR id for the Bindable field.