<p align="center"><img src="https://i.imgur.com/S0IjjHS.jpg" alt="1600" width="25%"/></p>

<p align="center">
    <strong>可能是最强大的RecyclerView框架</strong>
    <br>
    <br>
    <a href="http://liangjingkanji.github.io/BRV/">使用文档</a>
    | <a href="https://coding-pages-bucket-3558162-8706000-16641-587681-1252757332.cos-website.ap-shanghai.myqcloud.com/">备用访问</a>
    | <a href="https://raw.githubusercontent.com/liangjingkanji/BRV/master/sample/release/sample-release.apk">下载体验</a>
    <br>
    <img src="https://i.imgur.com/G7WYYXb.jpg" width="350"/>
</p>


<br>

<p align="center">
<a href="https://jitpack.io/#liangjingkanji/BRV"><img src="https://jitpack.io/v/liangjingkanji/BRV.svg"/></a>
<img src="https://img.shields.io/badge/language-kotlin-orange.svg"/>
<img src="https://img.shields.io/badge/license-Apache-blue"/>
<a href="http://liangjingkanji.github.io/BRV/updates"><img src="https://img.shields.io/badge/changed-%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97-brightgreen"/></a>
<a href="http://liangjingkanji.github.io/BRV/api"><img src="https://img.shields.io/badge/api-%E5%87%BD%E6%95%B0%E6%96%87%E6%A1%A3-red"/></a>
<a href="https://jq.qq.com/?_wv=1027&k=vWsXSNBJ"><img src="https://img.shields.io/badge/QQ群-752854893-blue"/></a>
<a href="https://github.com/liangjingkanji/BRV/blob/master/docs/issues.md"><img src="https://raw.githubusercontent.com/liangjingkanji/Net/master/docs/img/issues.svg"/></a>
</p>


<p align="center"><img src="https://user-images.githubusercontent.com/21078112/159120990-dc056729-fe5b-44ed-bddc-c0a656400709.jpg" align="center" width="30%;" /></p>

本框架在不影响RecyclerView的任何函数组件使用基础上开发. 本项目也将一直保持维护状态

<br>
<p align="center"><strong>欢迎贡献代码/问题</strong></p>
<br>

- 开发速度No.1
- 高内聚低耦合
- 刷新不闪屏
- 双向数据绑定
- 无Adapter
- 自动化

<br>

## 功能

- 快速创建多类型列表
- 一对多/多对多创建多类型布局
- 添加头布局和脚布局
- 点击(防抖动)/长按事件
- 分组(展开折叠/递归层次/展开置顶/拖拽/侧滑/多类型/单一展开模式)
- 悬停
- 快速创建分割线/间隔(支持官方全部的`LayoutManager`)
- 切换模式(例如切换编辑模式)
- 选择模式(多选/单选/全选/取消全选/反选)
- 拖拽位置
- 侧滑删除
- 下拉刷新(Refresh) | 上拉加载(LoadMore) | 下拉加载(UpFetch), 由[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)实现
- 预加载(Preload)
- 列表缺省页, 由[StateLayout](https://github.com/liangjingkanji/StateLayout)实现
- 自动分页加载列表数据
- 可添加[FlexboxLayoutManager](https://github.com/google/flexbox-layout)实现Flexbox伸缩布局
- 可添加[Net](https://github.com/liangjingkanji/Net)(基于协程实现自动化的并发网络请求)实现自动化网络请求


## 安装

添加远程仓库根据创建项目的 Android Studio 版本有所不同

Android Studio Arctic Fox以下创建的项目 在项目根目录的 build.gradle 添加仓库

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

Android Studio Arctic Fox以上创建的项目 在项目根目录的 settings.gradle 添加仓库

```kotlin
dependencyResolutionManagement {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

然后在 module 的 build.gradle 添加依赖框架

```groovy
dependencies {
    //...
    implementation 'com.github.liangjingkanji:BRV:1.3.62'
}
```

项目根目录中 gradle.properties 添加

```
android.enableJetifier=true
android.useAndroidX=true
```

## License

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

