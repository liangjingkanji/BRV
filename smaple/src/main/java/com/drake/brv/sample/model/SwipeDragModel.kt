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

import com.drake.brv.annotaion.DragType
import com.drake.brv.annotaion.SwipeType
import com.drake.brv.item.ItemTouchable

data class SwipeDragModel(override var itemSwipe: Int, override var itemDrag: Int) : ItemTouchable {

    val txt: String
        get() {

            var temp = ""

            if (itemDrag == DragType.NONE) {
                temp += "不支持拖拽"
            }

            if (itemDrag == DragType.NONE && itemSwipe == SwipeType.NONE)
                temp += " | "

            if (itemSwipe == SwipeType.NONE)
                temp += "不支持侧滑"

            if (temp.isNotEmpty()) {
                temp = "当前条目$temp"
            }

            return temp
        }
}