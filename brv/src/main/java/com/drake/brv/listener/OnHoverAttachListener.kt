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

package com.drake.brv.listener

import android.view.View

/**
 * 由RecyclerView.Adapter实现该接口
 */
interface OnHoverAttachListener {
    /**
     * 当条目附着时
     * [detachHover] 该函数可以进行还原
     */
    fun attachHover(v: View)

    /**
     * 条目分离时
     * 一般用于还原[attachHover]函数
     */
    fun detachHover(v: View)
}