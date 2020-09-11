## BRV


<p align="center"><img src="https://i.imgur.com/S0IjjHS.jpg" alt="1600" width="25%"/></p>

<p align="center">
    <strong>可能是最强大的RecyclerView框架</strong>
    <br>
    <br>
    <a href="http://liangjingkanji.github.io/BRV/">使用文档</a>
    <br>
    <img src="https://i.imgur.com/G7WYYXb.jpg" width="50%"/>
</p>

<br>

<p align="center">
<a href="https://jitpack.io/#liangjingkanji/BRV"><img src="https://jitpack.io/v/liangjingkanji/BRV.svg"/></a>
<img src="https://img.shields.io/badge/language-kotlin-orange.svg"/>
<img src="https://img.shields.io/badge/license-Apache-blue"/>
<a href="https://jq.qq.com/?_wv=1027&k=vWsXSNBJ"><img src="https://img.shields.io/badge/QQ群-752854893-blue"/></a>
</p>


<p align="center"><img src="https://i.imgur.com/lZXNqXE.jpg" align="center" width="30%;" /></p>


### 特点

-   简洁代码
-   功能全面
-   非侵入式
-   不创建任何文件
-   刷新不闪屏
-   数据双向绑定(DataBinding)
-   DSL作用域
-   高扩展性
-   文档详细
-   Demo简单

### 功能

- [x] 多类型
- [x] 单一数据模型一对多
- [x] 多数据模型
- [x] 添加头布局和脚布局
- [x] 点击(防抖动)/长按事件
- [x] 分组(展开折叠/递归层次/展开置顶/单一展开模式)
- [x] 悬停
- [x] 分割线/均布间隔(支持官方全部的`LayoutManager`)
- [x] 切换模式
- [x] 选择模式(多选/单选/全选/取消全选/反选)
- [x] 拖拽位置
- [x] 侧滑删除
- [x] 下拉刷新 | 上拉加载, 扩展SmartRefreshLayout即兼容其所有功能
- [x] 预拉取索引(UpFetch) | 预加载索引(Preload)
- [x] 缺省页
- [x] 自动分页加载
- [x] 伸缩布局 ([FlexboxLayoutManager](https://github.com/google/flexbox-layout))
- [x] 可扩展自动化网络请求 ([Net](https://github.com/liangjingkanji/Net)), 该网络请求基于协程实现自动化的并发网络请求




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
implementation 'com.github.liangjingkanji:BRV:1.3.11'
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

