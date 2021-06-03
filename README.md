## BRV


<p align="center"><img src="https://i.imgur.com/S0IjjHS.jpg" alt="1600" width="25%"/></p>

<p align="center">
    <strong>可能是最强大的RecyclerView框架</strong>
    <br>
    <br>
    <a href="http://liangjingkanji.github.io/BRV/">使用文档</a> | <a href="https://coding-pages-bucket-3558162-8706000-16641-587681-1252757332.cos-website.ap-shanghai.myqcloud.com/">备用访问</a>
    <br>
    <img src="https://i.imgur.com/G7WYYXb.jpg" width="350"/>
</p>


<br>

<p align="center">
<a href="https://jitpack.io/#liangjingkanji/BRV"><img src="https://jitpack.io/v/liangjingkanji/BRV.svg"/></a>
<img src="https://img.shields.io/badge/language-kotlin-orange.svg"/>
<img src="https://img.shields.io/badge/license-Apache-blue"/>
<a href="http://liangjingkanji.github.io/BRV/updates"><img src="https://img.shields.io/badge/updates-%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97-brightgreen"/></a>
<a href="http://liangjingkanji.github.io/BRV/api"><img src="https://img.shields.io/badge/api-%E5%87%BD%E6%95%B0%E6%96%87%E6%A1%A3-red"/></a>
<a href="https://jq.qq.com/?_wv=1027&k=vWsXSNBJ"><img src="https://img.shields.io/badge/QQ群-752854893-blue"/></a>
</p>


<p align="center"><img src="https://i.imgur.com/lZXNqXE.jpg" align="center" width="30%;" /></p>

本框架在不影响RecyclerView的任何函数组件使用基础上开发. 本项目也将一直保持维护状态

<br>
<p align="center"><strong>欢迎贡献代码/问题</strong></p>
<br>

-   优雅的函数设计
-   详细的使用文档
-   简单的示例代码
-   高内聚低耦合
-   刷新不闪屏
-   双向数据绑定(DataBinding)

<br>

## 功能

- 多类型
- 单一数据模型一对多
- 多数据模型
- 添加头布局和脚布局
- 点击(防抖动)/长按事件
- 分组(展开折叠/递归层次/展开置顶/单一展开模式)
- 悬停
- 分割线/均布间隔(支持官方全部的`LayoutManager`)
- 切换模式
- 选择模式(多选/单选/全选/取消全选/反选)
- 拖拽位置
- 侧滑删除
- 下拉刷新 | 上拉加载, 扩展SmartRefreshLayout即兼容其所有功能
- 预拉取索引(UpFetch) | 预加载索引(Preload)
- 缺省页
- 自动分页加载
- 扩展伸缩布局 ([FlexboxLayoutManager](https://github.com/google/flexbox-layout))
- 扩展自动化网络请求 ([Net](https://github.com/liangjingkanji/Net)), 该网络请求基于协程实现自动化的并发网络请求


## 安装

在项目根目录的 build.gradle 添加仓库

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```



在 module 的 build.gradle 添加依赖

```groovy
implementation 'com.github.liangjingkanji:BRV:1.3.22'
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

