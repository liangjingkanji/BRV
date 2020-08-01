## 不同数据的多类型

```kotlin
rv.linear().setup {

    addType<Model>(R.layout.item_1)
    addType<Store>(R.layout.item_2)

}.models = data
```



## 不同字段的多类型

```kotlin
rv.linear().setup {

    addType<Model>{
        // 使用年龄来作为判断返回不同的布局
        when (age) {
            23 -> {
                R.layout.item_1
            }
            else -> {
                R.layout.item_2
            }
        }
    }

}.models = data
```



## 区分类型

`itemViewType`属于onBind函数接受者`BindingViewHolder`的字段



```kotlin
rv.linear().setup {

    addType<Model>(R.layout.item_1)
    addType<Store>(R.layout.item_2)

    onBind {
		// 
        when (itemViewType) {
            R.layout.item_1 -> {
				// 类型 1
            }
            R.layout.item_2 -> {
                // 类型 1
            }
        }
    }

}.models = data
```

