
BRV对于伸缩布局的实现可以自行添加Google开源库 [flexbox-layout](https://github.com/google/flexbox-layout)

添加依赖

```groovy
dependencies {
    implementation 'com.google.android:flexbox:3.0.0'
}
```



然后创建列表


<img src="https://i.imgur.com/vyRLzrS.png" width="250"/>

```kotlin
rv.layoutManager = FlexboxLayoutManager(activity)

rv.setup {
    addType<FlexTagModel>(R.layout.item_flex_tag)
}.models = getData()
```


