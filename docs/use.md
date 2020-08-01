## 创建一个简单的列表

```kotlin
rv_simple.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
}.models = getData()
```



## 列表填充数据

BRV支持三种方式, 灵活使用; 这里提及的Model就等同于数据类/JavaBean/POJO



### 函数回调

在`onBind`函数中填充数据

```kotlin
rv_simple.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    onBind {
        findView<TextView>(R.id.tv_simple).text = getModel<SimpleModel>().name
    }
}.models = getData()
```





### 实现接口

通过为Model实现接口`ItemBind`, 实现函数`onBind`, 在该函数中填充数据; 这种方式在很多框架中被应用, 例如BRVAH, 但是我不推荐这种视图在Model中绑定的方式, 因为Model应当只存储数据和计算逻辑, 不应包含任何UI

```kotlin
class SimpleModel(var name: String = "BRV") : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val appName = holder.context.getString(R.string.app_name)
        holder.findView<TextView>(R.id.tv_simple).text = appName + itemPosition
    }
}
```





### DataBinding

通过DataBinding数据绑定形式自动填充数据, 推荐, 这是代码量最少最灵活的一种方式



第一步启用DataBinding, 在module中的build.gradle文件中

```groovy
android {
	/.../
    dataBinding {
        enabled = true
    }
}
```



第二部应当在Application中注册一个全局的ID(记住在`AndroidManifest`注册该Application)

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化BindingAdapter的默认绑定ID
        BRV.modelId = BR.m
    }
}
```



第三步, 在Item的布局文件中创建变量, 然后绑定变量到视图控件上

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="m"
            type="com.drake.brv.sample.model.SimpleModel" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <FrameLayout
            android:id="@+id/item"
            android:layout_width="match_parent"
            android:layout_height="100dp"
            android:layout_margin="16dp"
            android:background="@drawable/bg_card"
            android:foreground="?selectableItemBackgroundBorderless">

            <TextView
				android:id="@+id/tv_simple"
                android:text="@{String.valueOf(m.name)}"
                android:gravity="center"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />

        </FrameLayout>

    </LinearLayout>
</layout>
```



这种方式创建列表无需处理数据

```kotlin
rv_simple.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
}.models = getData()
```



别看文档中第三种方式复杂, 实际第三种方式代码量最少, 同时最解耦

