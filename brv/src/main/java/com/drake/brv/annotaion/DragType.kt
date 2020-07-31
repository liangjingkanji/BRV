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

package com.drake.brv.annotaion

import androidx.annotation.IntDef
import androidx.recyclerview.widget.ItemTouchHelper

@IntDef(DragType.LEFT, DragType.RIGHT, DragType.UP, DragType.DOWN, DragType.NONE, DragType.ALL)
annotation class DragType {

    companion object {
        const val LEFT = ItemTouchHelper.LEFT
        const val ALL = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        const val RIGHT = ItemTouchHelper.RIGHT
        const val LEFT_RIGHT = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        const val UP = ItemTouchHelper.UP
        const val DOWN = ItemTouchHelper.DOWN
        const val UP_DOWN = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        const val NONE = 0
    }

}