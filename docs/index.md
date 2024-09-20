BRV为快速构建RV列表工具, 以开源分享来完善, 将一直保持社区维护

<br>
<p align="center"><strong>STAR/分享可以让更多人参与到本开源项目</strong></p>
<br>

!!! note "前言"
    1. 阅读文档, 快速了解
    2. 阅读示例, 快速运用
    3. 阅读源码, 熟练并拓展

## 简单示例

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
}.models = getData()
```
文档提及的`rv`为RecyclerView简称, Model即`数据类/bean/pojo`

<br>

## 四种使用方式
BRV可自由扩展, 几乎支持市面上所有写法, 请按场景酌情考虑
!!! bug "编码规范"
    某些开发者无视建议写垃圾代码和本框架无关


### onBind - 简单

在`onBind`函数中填充数据, 适用于简单代码(去繁就简)

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    onBind {
        findView<TextView>(R.id.tv_simple).text = getModel<SimpleModel>().name
    }
}.models = getData()
```



### 接口实现 - 解耦 BRVAH

为Model实现接口`ItemBind`, 在`onBind`中赋值数据, 适用于复杂数据但又不想使用双向数据绑定

!!! warning "耦合"
    不推荐, 会导致业务逻辑和视图代码耦合, 不便迭代 <br>
    但这种方式在很多框架中被使用, 例如`BRVAH`, 可以方便从其他框架迁移到BRV

```kotlin
class SimpleModel(var name: String = "BRV") : ItemBind {

    override fun onBind(holder: BindingAdapter.BindingViewHolder) {
        holder.findView<TextView>(R.id.tv_simple).text = holder.layoutPosition.toString()
    }
}
```

### ViewBinding - 查找视图

要求开启ViewBinding, 上面接口实现方式也可以通过`getBinding()`使用

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
    onBind {
        val binding = getBinding<ItemSimpleBinding>() // ViewBinding/DataBinding都支持
        val data = holder.getModel<SimpleModel>()
    }
}.models = getData()
```



### DataBinding - 数据绑定

通过DataBinding实现数据绑定, 可以自动填充数据, 适用于任何场景

!!! success "推荐"
    这是最简洁优雅/最安全的一种绑定数据方式

#### 1. 启用DataBinding

首先在module中的`build.gradle`中开启DataBinding

```groovy
apply plugin: "kotlin-kapt" // 必要

android {
	/.../
    buildFeatures.dataBinding = true
}
```

#### 2. 布局声明变量

第二步, 在Item的布局文件中声明变量, 然后绑定变量到视图控件上

```xml hl_lines="24"
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="m"
            type="com.drake.brv.sample.model.SimpleModel" />
    </data>

    <TextView
        android:id="@+id/tv_simple"
        android:text="@{String.valueOf(m.name)}"
        android:gravity="center"
        android:layout_width="match_parent"
        android:layout_height="100dp" />
</layout>
```
选中行是DataBinding使用方法


#### 3. 自动绑定

在Application中初始化, DataBinding会根据`modelId`自动绑定`models`到xml中
```kotlin
BRV.modelId = BR.m
```

1. 先在某个布局中声明`<layout>`布局中的变量`m`(推荐命名), `BR.m`才能被生成 <br>一旦声明`BRV.model = BR.m`所有BRV使用的item布局使用`name="m"`来声明才能自动绑定
   <img src="https://i.loli.net/2021/08/14/rgX12ZSwkVMqQG3.png" width="450"/>
1. 导入主Module的BR, 这样所有使用该Id来声明数据模型的布局都会被BRV自动绑定数据 <br>
   <img src="https://i.loli.net/2021/08/14/VhYlAp1J7ZR9rIs.png" width="350"/>
   <img src="https://i.loli.net/2021/08/14/Yh5Ge1qQIObJpDn.png" width="350"/>
1. 如没有生成可`Make Project`(小锤子) <br>
   <img src="https://i.loli.net/2021/08/14/IEh3H8VaFM6d1LR.png" width="150"/>

#### 4. 构建列表

```kotlin
rv.linear().setup {
    addType<SimpleModel>(R.layout.item_simple)
}.models = getData()
```

!!! warning "组件化项目"
    多Module组件化项目要求主Module及使用DataBinding的Module都开启DataBinding及Kapt,
    否则抛出NoClassDefFoundError找不到BR类

!!! quote "DataBinding"
    使用DataBinding可以复制或者引用我的常用自定义属性:  [DataBindingComponent.kt](https://github.com/liangjingkanji/Engine/blob/master/engine/src/main/java/com/drake/engine/databinding/DataBindingComponent.kt) <br>
    如果想更了解DataBinding可以阅读一篇文章: [DataBinding最全使用说明](https://juejin.cn/post/6844903549223059463/)

---
[下载Apk](https://github.com/liangjingkanji/BRV/releases/latest/download/brv-sample.apk){ .md-button }
[下载源码](https://github.com/liangjingkanji/BRV.git){ .md-button }
[示例代码](https://github1s.com/liangjingkanji/BRV/blob/HEAD/sample/src/main/java/com/drake/brv/sample/ui/fragment/SimpleFragment.kt){ .md-button }
