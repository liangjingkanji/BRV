
本框架在不影响RecyclerView的任何函数组件使用基础上开发. 本框架也将一直保持维护状态

<br>
<p align="center"><strong>STAR/分享可以让更多人参与到本开源项目, 点击文档右上角小铅笔可直接修订文档 ↗</strong></p>
<br>

## 创建一个简单的列表

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
}.models = getData()
```

这里出现的`BRV`关键词即本框架, `RV`即RecyclerView简称. `setup`函数也只是简化创建BindingAdapter对象

## 列表填充数据的4种方式

BRV支持4种方式, 灵活使用, 这里提及的Model就等同于数据类/JavaBean/POJO



### 1. 函数回调

在`onBind`函数中填充数据

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    onBind {
        findView<TextView>(R.id.tv_simple).text = getModel<SimpleModel>().name
    }
}.models = getData()
```





### 2. 实现接口

通过为Model实现接口`ItemBind`, 实现函数`onBind`, 在该函数中填充数据; 这种方式在很多框架中被应用, 例如BRVAH, 但是我不推荐这种视图在Model中绑定的方式, 因为Model应当只存储数据和计算逻辑, 不应包含任何UI

```kotlin
class SimpleModel(var name: String = "BRV") : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        val appName = holder.context.getString(R.string.app_name)
        holder.findView<TextView>(R.id.tv_simple).text = appName + itemPosition

        // 如果存在多种数据类型, 请使用holder.getModelOrNull<Data>()或者if来判断itemViewType类型, 避免取值类型转换错误
        val data = holder.getModel<Data>()

        when (holder.itemViewType) {
            R.layout.item_simple_text -> {
                val binding = holder.getBinding<ItemSimpleBinding>()
                val data = holder.getModel<SimpleModel>()
                binding.tv.text = data.name
            }
        }
    }
}
```

### 3. ViewBinding

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)

    onCreate {
        getBinding<ItemSimpleBinding>().tvName.text = "文本内容"
    }

    onBind {
        val binding = getBinding<ItemSimpleBinding>() // 使用ViewBinding/DataBinding都可以使用本方法
        val data = holder.getModel<SimpleModel>()
    }
}.models = getData()
```



### 4. DataBinding

通过DataBinding数据绑定形式自动填充数据, 推荐, 这是代码量最少最灵活的一种方式.

#### a. 启用DataBinding

第一步在module中的`build.gradle`文件中开启DataBinding框架

```groovy
apply plugin: "kotlin-kapt" // kapt插件用于生成dataBinding

android {
	/.../
    buildFeatures.dataBinding = true
}
```

#### b. 布局声明变量

第二步, 在Item的布局文件中声明变量, 然后绑定变量到视图控件上

```xml hl_lines="24"
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
选中行是DataBinding使用方法


#### c. 初始化全局ID


```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // 初始化BindingAdapter的默认绑定ID, 如果不使用DataBinding并不需要初始化
        BRV.modelId = BR.m
    }
}
```

> rv是一个列表. 里面的models是一个list集合, 每个元素对应一个item. dataBinding会根据你`赋值的Id`自动绑定models中元素到xml中赋值 <br>

1. 注意要先在某个布局或Item布局声明`<layout>`布局中的变量`name="m"`, `BR.m`才能被生成, 一旦声明`BRV.model = BR.m`你的所有BRV使用的item布局都得使用`name="m"`来声明数据模型, 否则会无法自动绑定 <br>
   <img src="https://i.loli.net/2021/08/14/rgX12ZSwkVMqQG3.png" width="450"/>
1. 导包注意导入你所在module的BR, 这样所有使用该Id来声明数据模型的布局都会被BRV自动绑定数据 <br>
   <img src="https://i.loli.net/2021/08/14/VhYlAp1J7ZR9rIs.png" width="350"/>
   <img src="https://i.loli.net/2021/08/14/Yh5Ge1qQIObJpDn.png" width="350"/>
1. 如果依然没有生成请`make project`(即图中绿色小锤子图标) <br>
   <img src="https://i.loli.net/2021/08/14/IEh3H8VaFM6d1LR.png" width="150"/>

1. m(m是model的简称)可以是任何其他的名称, model或者sb都可以, 比如你`name="data"`, 那么你就应该使用BR.data <br>
BR.data和Android中常见的`R.id.data`都属于Id常量, 本质上都是Int值. 你可以点击查看BR.m源码<br>

#### d. 构建列表

这种方式创建列表无需处理数据

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
}.models = getData()
```

别看文档中DataBinding方式复杂, 实际代码量最少, 同时最解耦

> 使用DataBinding可以复制或者引用我的常用自定义属性:  [DataBindingComponent.kt](https://github.com/liangjingkanji/Engine/blob/master/engine/src/main/java/com/drake/engine/databinding/DataBindingComponent.kt)<br>
> 如果你想更加了解DataBinding请阅读[DataBinding最全使用说明](https://juejin.cn/post/6844903549223059463/)

