/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.brv.sample.vm

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

/**
 * 此处只是演示创建DataBinding属性, 使用object而非包级函数可以避免命令空间混乱
 *
 * 实际项目推荐参考以下文件
 * https://github.com/liangjingkanji/Engine/blob/master/engine/src/main/java/com/drake/engine/databinding/DataBindingComponent.kt
 */
object RenderBindingComponent {

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageRes(image: ImageView, @DrawableRes drawable: Int) {
        image.setImageResource(drawable)
    }
}