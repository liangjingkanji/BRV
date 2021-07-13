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

package com.drake.brv.item

/**
 * 可展开/折叠的条目
 */
interface ItemExpand {

    /** 同级别的分组的索引位置 */
    var itemGroupPosition: Int

    /** 是否已展开 */
    var itemExpand: Boolean

    /** 子列表 */
    var itemSublist: List<Any?>?
}