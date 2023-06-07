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
    <img src="https://i.imgur.com/G7WYYXb.jpg" width="350"/>
</p>

<br>

<p align="center">
<a href="https://jitpack.io/#liangjingkanji/BRV"><img src="https://jitpack.io/v/liangjingkanji/BRV.svg"/></a>
<img src="https://img.shields.io/badge/language-kotlin-orange.svg"/>
<img src="https://img.shields.io/badge/license-Apache-blue"/>
<a href="http://liangjingkanji.github.io/BRV/updates"><img src="https://img.shields.io/badge/changed-%E6%9B%B4%E6%96%B0%E6%97%A5%E5%BF%97-brightgreen"/></a>
<a href="http://liangjingkanji.github.io/BRV/api"><img src="https://img.shields.io/badge/api-%E5%87%BD%E6%95%B0%E6%96%87%E6%A1%A3-red"/></a>
<img src="https://raw.githubusercontent.com/liangjingkanji/liangjingkanji/master/img/group.svg"/>
<a href="https://github.com/liangjingkanji/BRV/blob/master/docs/issues.md"><img src="https://raw.githubusercontent.com/liangjingkanji/Net/master/docs/img/issues.svg"/></a>
</p>


<p align="center"><img src="https://github.com/liangjingkanji/BRV/blob/master/docs/image/preview.png?raw=true" align="center" width="30%;" /></p>

This framework is developed without affecting any basic functions/components of RecyclerView. This project promises to be maintained by the community forever.

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

Add the remote repository to the root build.gradle file, depending on the Android Studio version of your project creation:

For projects created in Android Studio Arctic Fox and below, add the repository to the root build.gradle file:

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

For projects created in Android Studio Arctic Fox and above, add the repository to the settings.gradle file:

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
    //...
    implementation 'com.github.liangjingkanji:BRV:1.4.1'
}
```

Add the following lines to the gradle.properties file in the project root directory:

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