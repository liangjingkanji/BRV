要求配和Google的开源项目[flexbox-layout](https://github.com/google/flexbox-layout)

添加依赖

```groovy
dependencies {
    implementation 'com.google.android:flexbox:2.0.1'
}
```



然后创建列表

```kotlin
rv.layoutManager = FlexboxLayoutManager(activity)

rv.setup {
    addType<FlexTagModel>(R.layout.item_flex_tag)
}.models = getData()
```



<img src="https://i.imgur.com/ppWt6gH.png" alt="image-20200801192305799" width="50%" />