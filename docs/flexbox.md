
使用Google官方库 [flexbox-layout](https://github.com/google/flexbox-layout) 来实现流式伸缩布局

依赖

```groovy
dependencies {
    implementation 'com.google.android.flexbox:flexbox:3.0.0'
}
```

创建列表

```kotlin
rv.layoutManager = FlexboxLayoutManager(activity)

rv.setup {
    addType<FlexTagModel>(R.layout.item_flex_tag)
}.models = getData()
```

<figure markdown>
  ![](https://i.loli.net/2021/08/14/KYkHmyCrDogiLsS.png){ width="250" }
  <a href="https://github.com/liangjingkanji/BRV/blob/5269ef2/sample/src/main/java/com/drake/brv/sample/ui/fragment/FlexBoxFragment.kt" target="_blank"><figcaption>示例代码</figcaption></a>
</figure>

