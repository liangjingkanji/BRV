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

package com.drake.brv.sample.model

import com.drake.brv.annotaion.ItemOrientation
import com.drake.brv.item.ItemSwipe

data class SwipeModel(override var itemOrientationSwipe: Int = ItemOrientation.ALL) : ItemSwipe {

    fun getText(): String? = when (itemOrientationSwipe) {
        ItemOrientation.ALL -> "水平滑动删除"
        ItemOrientation.LEFT -> "左滑删除"
        ItemOrientation.RIGHT -> "右滑删除"
        else -> "禁用侧滑"
    }
}