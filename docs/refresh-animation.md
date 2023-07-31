BRV使用[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)来实现下拉刷新/上拉加载, 其可以自定义刷新动画

## 关闭上拉加载动画

关闭动画不影响预加载功能

全局配置

```kotlin
SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
    layout.setFooterHeight(0F)
    val classicsFooter = ClassicsFooter(context)
    classicsFooter.visibility = View.GONE
    classicsFooter
}
```

单例配置, 修改xml布局

```xml hl_lines="5 15"
<com.drake.brv.PageRefreshLayout
    android:id="@+id/page"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:srlFooterHeight="0dp">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />

    <com.scwang.smart.refresh.footer.ClassicsFooter
        android:layout_width="match_parent"
        android:layout_height="0dp" />

</com.drake.brv.PageRefreshLayout>
```


## 自定义刷新动画

- [SmartRefreshLayout自定义动画文档](https://github.com/scwang90/SmartRefreshLayout/blob/master/art/md_custom.md)

SmartRefresh自带7个动画依赖库, 可以参考其源码

```groovy
implementation  'io.github.scwang90:refresh-header-classics:2.0.5'    //经典刷新头
implementation  'io.github.scwang90:refresh-header-radar:2.0.5'       //雷达刷新头
implementation  'io.github.scwang90:refresh-header-falsify:2.0.5'     //虚拟刷新头
implementation  'io.github.scwang90:refresh-header-material:2.0.5'    //谷歌刷新头
implementation  'io.github.scwang90:refresh-header-two-level:2.0.5'   //二级刷新头
implementation  'io.github.scwang90:refresh-footer-ball:2.0.5'        //球脉冲加载
implementation  'io.github.scwang90:refresh-footer-classics:2.0.5'    //经典加载
```

想了解更多请浏览[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)官网

|个人首页|微博列表|
|:---:|:---:|
|![](https://scwang90.github.io/assets/refresh-layout/gif_practive_weibo_new.gif)|![](https://scwang90.github.io/assets/refresh-layout/gif_practive_feedlist_new.gif)|

|餐饮美食|个人中心|
|:---:|:---:|
|![](https://scwang90.github.io/assets/refresh-layout/gif_practive_repast_new.gif)|![](https://scwang90.github.io/assets/refresh-layout/gif_practive_profile.gif)|

|Delivery|DropBox|
|:---:|:---:|
|![](https://scwang90.github.io/assets/refresh-layout/gif_Delivery.gif)|![](https://scwang90.github.io/assets/refresh-layout/gif_Dropbox.gif)|
|[Refresh-your-delivery](https://dribbble.com/shots/2753803-Refresh-your-delivery)|[Dropbox-Refresh](https://dribbble.com/shots/3470499-DropBox-Refresh)|

|BezierRadar|BezierCircle|
|:---:|:---:|
|![](https://scwang90.github.io/assets/refresh-layout/gif_BezierRadar.gif)|![](https://scwang90.github.io/assets/refresh-layout/gif_BezierCircle.gif)|
|[Pull To Refresh](https://dribbble.com/shots/1936194-Pull-To-Refresh)|[Pull Down To Refresh](https://dribbble.com/shots/1797373-Pull-Down-To-Refresh)|

|FlyRefresh|Classics|
|:---:|:---:|
|![](https://scwang90.github.io/assets/refresh-layout/gif_FlyRefresh.gif)|![](https://scwang90.github.io/assets/refresh-layout/gif_Classics.gif)|
|[FlyRefresh](https://github.com/race604/FlyRefresh)|[ClassicsHeader](#1)|

|Phoenix|Taurus|
|:---:|:---:|
|![](https://scwang90.github.io/assets/refresh-layout/gif_Phoenix.gif)|![](https://scwang90.github.io/assets/refresh-layout/gif_Taurus.gif)|
|[Yalantis/Phoenix](https://github.com/Yalantis/Phoenix)|[Yalantis/Taurus](https://github.com/Yalantis/Taurus)

|BattleCity|HitBlock|
|:---:|:---:|
|![](https://scwang90.github.io/assets/refresh-layout/gif_BattleCity.gif)|![](https://scwang90.github.io/assets/refresh-layout/gif_HitBlock.gif)|
|[FunGame/BattleCity](https://github.com/Hitomis/FunGameRefresh)|[FunGame/HitBlock](https://github.com/Hitomis/FunGameRefresh)

|WaveSwipe|Material|
|:---:|:---:|
|![](https://scwang90.github.io/assets/refresh-layout/gif_WaveSwipe.gif)|![](https://scwang90.github.io/assets/refresh-layout/gif_Material.gif)|
|[WaveSwipeRefreshLayout](https://github.com/recruit-lifestyle/WaveSwipeRefreshLayout)|[MaterialHeader](https://developer.android.com/reference/android/support/v4/widget/SwipeRefreshLayout.html)

|StoreHouse|WaterDrop|
|:---:|:---:|
|![](https://scwang90.github.io/assets/refresh-layout/gif_StoreHouse.gif)|![](https://scwang90.github.io/assets/refresh-layout/gif_WaterDrop.gif)|
|[CRefreshLayout](https://github.com/cloay/CRefreshLayout)|[WaterDrop](https://github.com/THEONE10211024/WaterDropListView)