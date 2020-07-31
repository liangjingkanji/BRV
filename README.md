## BRV


<p align="center"><img src="https://i.imgur.com/S0IjjHS.jpg" alt="1600" width="25%"/></p>

<p align="center"><strong>可能是最强大的RecyclerView框架</strong></p><br>

<p align="center">
<a href="https://jitpack.io/#liangjingkanji/BRV"><img src="https://jitpack.io/v/liangjingkanji/BRV.svg"/></a>
<img src="https://img.shields.io/badge/license-MIT-green"/>
<a href="https://jq.qq.com/?_wv=1027&k=vWsXSNBJ"><img src="https://img.shields.io/badge/QQ群-752854893-blue"/></a>
</p>


<p align="center"><a href="http://github.io.liangjingkanji/brv/">使用文档</a></p>


<p align="center"><img src="https://i.imgur.com/lZXNqXE.jpg" align="center" width="30%;" /></p>



### 特点

-   简洁代码
-   功能全面
-   文档详细
-   非侵入式
-   不创建任何文件
-   刷新不闪屏
-   数据双向绑定
-   DSL作用域
-   高扩展性

### 功能

- [x] 多类型
- [x] 单一数据模型一对多
- [x] 多数据模型
- [x] 添加头布局和脚布局
- [x] 点击(防抖动)/长按事件
- [x] 分组(展开折叠/递归层次/展开置顶)
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

- [ ] 划动多选
- [ ] 无限划动



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
implementation 'com.github.liangjingkanji:BRV:1.3.7'
```



## MIT

```
Copyright (C) 2018 Drake, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

