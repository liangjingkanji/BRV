package com.drake.brv.sample.model

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.databinding.BaseObservable
import com.drake.brv.sample.R
import com.drake.engine.base.app
import com.drake.spannable.addSpan
import com.drake.spannable.replaceSpan
import com.drake.spannable.setSpan
import com.drake.spannable.span.CenterImageSpan
import kotlinx.serialization.Serializable


@Serializable
data class CommentModel(var total: Int, var list: List<Data>) {

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
        var commentCount: Long = 0, // 子评论数量
        var comments: List<Comment> = listOf() // 子评论
    ) : BaseObservable() {

        /**
         * 当Vip达到6级需要显示红色用户名
         */
        fun getNameColor(): Int {
            return if (vipLevel == 6) Color.parseColor("#ed6f98") else Color.parseColor("#62666d")
        }

        /** 显示更多子评论 */
        fun comments(): List<CharSequence> {
            val data = mutableListOf<CharSequence>()
            data.addAll(comments.map { it.getSpannable() })

            // 如果小于等于三条评论
            if (commentCount <= 3) return data

            val span = SpannableStringBuilder()

            // 如果UP参与评论
            if (isAuthor) {
                span.addSpan("UP主等人 ", ForegroundColorSpan(Color.parseColor("#979ba1")))
            }

            // 如果超过三条子评论
            val more = span.addSpan("共${commentCount}条回复", ForegroundColorSpan(Color.parseColor("#3b87bf")))
                .addSpan("image", CenterImageSpan(app, R.drawable.ic_comment_arrow_right))
            data.add(more)
            return data
        }

        /** 点赞 */
        fun favoriteAction() {
            // 一般情况下这里应当请求网络, 然后更新点赞数量
            if (favorite) {
                favoriteCount--
            } else {
                favoriteCount++
            }
            favorite = !favorite
            notifyChange()
        }

        @Serializable
        data class Comment(
            var nickname: String = "",
            var content: String = ""
        ) {

            /**
             * 因为需要换行所以使用Spannable来填充一个textView
             * 使用依赖库快速渲染评论内容 https://github.com/liangjingkanji/spannable
             */
            fun getSpannable(): CharSequence {
                return nickname.setSpan(ForegroundColorSpan(Color.parseColor("#3b87bf"))).addSpan(" : $content").replaceSpan("@[^@]+?(?=\\s|\$)".toRegex()) {
                    ForegroundColorSpan(Color.parseColor("#3b87bf"))
                }
            }
        }
    }
}