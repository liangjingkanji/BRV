/*
 * Copyright (C) 2018 Drake, https://github.com/liangjingkanji
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

import android.text.format.DateUtils
import androidx.databinding.BaseObservable
import kotlinx.serialization.Serializable

@Serializable
data class DouyinCommentReplyModel(var total: Int, var list: List<Data>) {
    @Serializable
    class Data(
        var nickname: String = "", // 发布者名称
        var avatar: String = "", // 发布者头像
        var content: String = "", // 评论内容
        var createDate: Long = 0L, // 评论时间
        var vipLevel: Int = 0, // 会员等级
        var favoriteCount: Int = 0, // 点赞数量
        var favorite: Boolean = false, // 点赞
        var annoying: Boolean = false, // 踩
        var sb: Boolean = false, // UP觉得很傻逼
        var isAuthor: Boolean = false, // UP评论
    ) : BaseObservable() {

        var loadedCommentSize = 0 // 已经加载子评论数量
        var totalCommentSize = 0 // 全部子评论数量
        var index = 0 // 当前子评论索引

        var loading = false // 是否处于加载更多回复中
            set(value) {
                field = value
                notifyChange()
            }

        /** 展开文字动作 */
        fun getExpandDesc(): String {
            return if (isHasMore()) {
                "展开${totalCommentSize - loadedCommentSize}条回复"
            } else {
                "收起回复"
            }
        }

        /** 是否显示展开操作 */
        fun showExpand(): Boolean {
            return index == loadedCommentSize - 1
        }

        /** 是否存在更多子评论 */
        fun isHasMore(): Boolean {
            return index == loadedCommentSize - 1 && loadedCommentSize < totalCommentSize
        }

        /** 评论时间 */
        fun getLastTime(): CharSequence? {
            return DateUtils.getRelativeTimeSpanString(createDate)
        }
    }
}