<p >
    <a href="http://liangjingkanji.github.io/BRV/">中文</a>
    | <a href="https://github.com/liangjingkanji/BRV/blob/master/README_EN.md">English</a>
</p>

<p align="center"><img src="https://i.imgur.com/S0IjjHS.jpg" alt="1600" width="25%"/></p>

<p align="center">
    <strong>Probably the most powerful RecyclerView framework</strong>
    <br>
    <br>
    <a href="https://shenbengit.github.io/BRV/">Documentation</a>
    | <a href="https://github.com/liangjingkanji/document/blob/master/visit-pages.md">Can't Access?</a>
    | <a href="https://liangjingkanji.github.io/document/">Contribute Code</a>
    | <a href="https://github.com/liangjingkanji/BRV/releases/latest/download/brv-sample.apk">Download Demo App</a>
    <br>
    <img src="https://github.com/liangjingkanji/BRV/blob/master/docs/img/code-preview.png?raw=true" width="300"/>
</p>

<br>

<p align="center">
<a href="https://jitpack.io/#liangjingkanji/BRV"><img src="https://jitpack.io/v/liangjingkanji/BRV.svg"/></a>
<img src="https://img.shields.io/badge/language-kotlin-orange.svg"/>
<img src="https://img.shields.io/badge/license-Apache-blue"/>
<a href="http://liangjingkanji.github.io/BRV/updates"><img src="https://img.shields.io/badge/changed-%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97-brightgreen"/></a>
<a href="http://liangjingkanji.github.io/BRV/api"><img src="https://img.shields.io/badge/api-%E5%87%BD%E6%95%B0%E6%96%87%E6%A1%A3-red"/></a>
<a href="https://raw.githubusercontent.com/liangjingkanji/liangjingkanji/master/img/group-qrcode.png"><img src="https://raw.githubusercontent.com/liangjingkanji/liangjingkanji/master/img/group.svg"/></a>
<a href="https://github.com/liangjingkanji/BRV/blob/master/docs/issues.md"><img src="https://raw.githubusercontent.com/liangjingkanji/Net/master/docs/img/issues.svg"/></a>
</p>


<p align="center"><img src="https://github.com/liangjingkanji/BRV/blob/master/docs/img/preview.png?raw=true" align="center" width="30%;" /></p>

BRV is a tool for quickly building an RV list, perfected by open source sharing, and will always be maintained by the community

<br>
<p align="center"><strong>Contribute Code / Report Issues</strong></p>
<br>

- [x] Development efficiency No.1
- [x] Maintained by the community forever
- [x] Low code/high extensibility
- [x] Excellent source code/comments/documentation/examples

<br>

## Features

- [x] Quickly create multi-type lists
- [x] One-to-one/one-to-many multi-type creation
- [x] Add header and footer views
- [x] Click (debounce)/long press events
- [x] Grouping (expand/collapse/recursively/nested/expand and stick/top/ drag and swipe/multi-type/single expand mode)
- [x] Sticky header
- [x] Quickly create dividers/spaces
- [x] Switch mode (e.g., switch to edit mode)


- [x] Selection mode (multi-select/single-select/select all/deselect all/invert selection)
- [x] Drag and drop sorting
- [x] Swipe to delete
- [x] Pull-down refresh (Refresh) | Pull-up load more (LoadMore) | Pull-down load more (UpFetch), implemented by [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)
- [x] Preload
- [x] Data update with diffing
- [x] Automatic pagination loading
- [x] List animation/skeleton animation
- [x] List empty state, implemented by [StateLayout](https://github.com/liangjingkanji/StateLayout)
- [x] Support DataBinding
- [x] Support ViewBinding
- [x] Add [FlexboxLayoutManager](https://github.com/google/flexbox-layout) to automatically wrap flexbox layout
- [x] Add [Net](https://github.com/liangjingkanji/Net) (powerful coroutine network request) to automate network requests

## Installation

Add the remote repository to the root build.gradle file

```groovy
dependencyResolutionManagement {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

Then, add the framework dependency to the module's build.gradle file:

```groovy
dependencies {
    implementation 'com.github.liangjingkanji:BRV:1.5.0'
}
```

## License

```
MIT License

Copyright (c) 2023 劉強東

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```