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

import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.listener.OnMultiListener

open class OnMultiStateListener : OnMultiListener {
    override fun onFooterMoving(
            footer: RefreshFooter?,
            isDragging: Boolean,
            percent: Float,
            offset: Int,
            footerHeight: Int,
            maxDragHeight: Int
    ) {
    }

    override fun onHeaderStartAnimator(
            header: RefreshHeader?,
            headerHeight: Int,
            maxDragHeight: Int
    ) {
    }

    override fun onFooterReleased(footer: RefreshFooter?, footerHeight: Int, maxDragHeight: Int) {
    }

    override fun onStateChanged(
            refreshLayout: RefreshLayout,
            oldState: RefreshState,
            newState: RefreshState
    ) {
    }

    override fun onHeaderMoving(
            header: RefreshHeader?,
            isDragging: Boolean,
            percent: Float,
            offset: Int,
            headerHeight: Int,
            maxDragHeight: Int
    ) {
    }

    override fun onFooterFinish(footer: RefreshFooter?, success: Boolean) {
    }

    override fun onFooterStartAnimator(
            footer: RefreshFooter?,
            footerHeight: Int,
            maxDragHeight: Int
    ) {
    }

    override fun onHeaderReleased(header: RefreshHeader?, headerHeight: Int, maxDragHeight: Int) {
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
    }

    override fun onHeaderFinish(header: RefreshHeader?, success: Boolean) {
    }
}