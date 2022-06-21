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

package com.drake.brv.utils

import android.view.View
import android.view.ViewGroup
import com.drake.brv.PageRefreshLayout


/**
 * 创建一个[PageRefreshLayout]来包裹视图
 * 尽量还是建议在布局文件中声明[PageRefreshLayout], 保持项目可读性避免未知为发生
 *
 * @param loadMoreEnabled 启用上拉加载
 * @param stateEnabled 启用缺省页
 */
fun View.createPage(
    loadMoreEnabled: Boolean = true,
    stateEnabled: Boolean = true
): PageRefreshLayout {
    val pageRefreshLayout = PageRefreshLayout(context)

    val parent = parent as ViewGroup
    pageRefreshLayout.id = id
    val index = parent.indexOfChild(this)
    val layoutParams = layoutParams

    parent.removeView(this)
    pageRefreshLayout.setRefreshContent(this@createPage)
    parent.addView(pageRefreshLayout, index, layoutParams)
    pageRefreshLayout.setEnableLoadMore(loadMoreEnabled)
    pageRefreshLayout.stateEnabled = stateEnabled
    pageRefreshLayout.initialize()

    return pageRefreshLayout
}

@Deprecated("命名规范, 本方法将在未来废弃建议立即替换", ReplaceWith("createPage(loadMoreEnabled, stateEnabled)"))
fun View.page(
    loadMoreEnabled: Boolean = true,
    stateEnabled: Boolean = true
): PageRefreshLayout = createPage(loadMoreEnabled, stateEnabled)


