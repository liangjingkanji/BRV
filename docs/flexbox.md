
BRV对于伸缩布局的实现可以自行添加Google开源库 [flexbox-layout](https://github.com/google/flexbox-layout)

添加依赖

```groovy
dependencies {
    implementation 'com.google.android:flexbox:2.0.1'
}
```



然后创建列表


<img src="https://i.imgur.com/DJcEZ0j.png" width="50%"/>

```kotlin
rv.layoutManager = FlexboxLayoutManager(activity)

rv.setup {
    addType<FlexTagModel>(R.layout.item_flex_tag)
}.models = getData()
```


