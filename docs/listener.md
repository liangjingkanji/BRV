## 函数

```kotlin
fun addClickable(@IdRes vararg id: Int)
// 防抖动, 500毫秒内无法重复点击

fun addFastClickable(@IdRes vararg id: Int)
// 不存在防抖动, 可以快速点击

fun addLongClickable(@IdRes vararg id: Int)
// 长按事件

fun onClick(@IdRes vararg id: Int, block: BindingViewHolder.(id: Int) -> Unit)
// 点击事件回调
// id: 即将调用addClickable先添加Id再设置监听函数

fun onLongClick(@IdRes vararg id: Int, block: BindingViewHolder.(id: Int) -> Unit)
// 长按事件回调
```



## 示例

通过使用Item的布局文件中的控件id可以设置点击事件或者长按事件

```
rv_normal.linear().setup {
    
    addType<NormalModel>(R.layout.item_multi_type_normal)
    
    addClickable(R.id.item)
    
    onClick {
        // Item设置点击事件, 就要给Item的根布局设置一个id, 这里设置的是R.id.item
    }
}.models = getData()
```



其实上面两个步骤可以整合成一个, 这里演示下同时设置多个Id添加点击事件

```kotlin
rv_normal.linear().setup {
    
    addType<NormalModel>(R.layout.item_multi_type_normal)
    
    onClick(R.id.item, R.id.btn_submit) {
        // it就是你设置的id
        when(it){ 
            R.id.item -> {
                // Item设置点击事件, 就要给Item的根布局设置一个id, 这里设置的是R.id.item
            } 
            R.id.btn_submit -> {
                // 做任何事
            }
        }
    }
}.models = getData()
```