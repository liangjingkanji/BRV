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
import com.drake.brv.item.ItemExpand
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class DouyinCommentModel(var total: Int, var list: List<Data>) {
    @Serializable
    data class Data(
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
        var commentCount: Int = 0, // 子评论数量
    ) : BaseObservable(), ItemExpand {

        var loading = false // 是否处于加载更多回复中
            set(value) {
                field = value
                notifyChange()
            }

        /** 评论时间 */
        fun getLastTime(): CharSequence? {
            return DateUtils.getRelativeTimeSpanString(createDate)
        }

        override var itemGroupPosition: Int = 0
        override var itemExpand: Boolean = false

        @Transient
        var comments: MutableList<DouyinCommentReplyModel.Data> = mutableListOf()

        override fun getItemSublist(): List<Any> {
            return comments
        }

        /** 展开文字动作 */
        fun getExpandDesc(): String {
            return "展开${commentCount}条回复"
        }

        fun addComment(data: List<DouyinCommentReplyModel.Data>) {
            comments.addAll(data.take(commentCount).shuffled())
            val size = comments.size
            comments.forEachIndexed { index, m ->
                m.loadedCommentSize = size
                m.totalCommentSize = commentCount
                m.index = index
                m.notifyChange()
            }
        }
    }
}