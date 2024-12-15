<p>
    <a href="http://liangjingkanji.github.io/BRV/">中文</a>
    | <a href="https://github.com/liangjingkanji/BRV/blob/master/README_EN.md">English</a>
</p>

<p align="center"><img src="https://github.com/liangjingkanji/BRV/assets/21078112/443d47ef-5dc7-49a3-bab2-6a9feb678aa1" alt="1600" width="25%"/></p>

<p align="center">
    <strong>Possibly the Most Powerful RecyclerView Framework</strong>
    <br>
    <br>
    <a href="http://liangjingkanji.github.io/BRV/">Documentation</a>
    | <a href="https://github.com/liangjingkanji/document/blob/master/visit-pages.md">Access Issues?</a>
    | <a href="https://liangjingkanji.github.io/document/">Contribute</a>
    | <a href="https://github.com/liangjingkanji/BRV/releases/latest/download/brv-sample.apk">Download Demo</a>
    <br>
    <img src="https://github.com/liangjingkanji/BRV/blob/master/docs/img/code-preview.png?raw=true" width="300"/>
</p>

<br>

<p align="center">
<a href="https://jitpack.io/#liangjingkanji/BRV"><img src="https://jitpack.io/v/liangjingkanji/BRV.svg"/></a>
<img src="https://img.shields.io/badge/language-kotlin-orange.svg"/>
<img src="https://img.shields.io/badge/license-MIT-blue"/>
<a href="http://liangjingkanji.github.io/BRV/updates"><img src="https://img.shields.io/badge/changed-Changelog-brightgreen"/></a>
<a href="http://liangjingkanji.github.io/BRV/api"><img src="https://img.shields.io/badge/api-Documentation-red"/></a>
<a href="https://raw.githubusercontent.com/liangjingkanji/liangjingkanji/master/img/group-qrcode.png"><img src="https://raw.githubusercontent.com/liangjingkanji/liangjingkanji/master/img/group.svg"/></a>
<a href="https://github.com/liangjingkanji/BRV/blob/master/docs/issues.md"><img src="https://raw.githubusercontent.com/liangjingkanji/Net/master/docs/img/issues.svg"/></a>
</p>

<p align="center"><img src="https://github.com/liangjingkanji/BRV/blob/master/docs/img/preview.png?raw=true" align="center" width="30%;" /></p>

BRV is a tool for quickly building RecyclerView lists, improved through open source sharing, and will always maintain community maintenance.

<br>

<p align="center"><strong>Contributions Welcome</strong></p>
<br>

- [x] #1 in Development Efficiency
- [x] Continuous Community Maintenance
- [x] Low Code / High Extensibility
- [x] Excellent Source Code / Comments / Documentation / Examples

<br>

## Features

- [x] Quick creation of multi-type lists
- [x] One-to-one/one-to-many multi-type creation
- [x] Add header and footer layouts
- [x] Click (debounce)/long press events
- [x] Grouping (expand/collapse/recursive/expand to top/drag/swipe/multi-type/single expand mode)
- [x] Sticky headers
- [x] Quick creation of dividers/spacing
- [x] Toggle modes (e.g., edit mode)
- [x] Selection mode (multi-select/single-select/select all/deselect all/inverse selection)
- [x] Drag and drop sorting
- [x] Swipe to delete
- [x] Pull to refresh | Load more | Up fetch ([SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout))
- [x] Preloading
- [x] Data diff updates
- [x] Automatic pagination
- [x] List animations/skeleton loading
- [x] List placeholder pages ([StateLayout](https://github.com/liangjingkanji/StateLayout))
- [x] DataBinding support
- [x] ViewBinding support
- [x] Support for [FlexboxLayoutManager](https://github.com/google/flexbox-layout)
- [x] Can add [Net](https://github.com/liangjingkanji/Net) for automated network requests

## Installation

Add repository to Project's settings.gradle

```kotlin
dependencyResolutionManagement {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add dependency to Module's build.gradle

```groovy
dependencies {
    implementation 'com.github.liangjingkanji:BRV:1.6.1'
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