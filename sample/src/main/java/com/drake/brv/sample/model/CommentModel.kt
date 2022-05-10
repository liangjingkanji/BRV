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
data class CommentModel(
    var name: String = "", // 发布者名称
    var avatar: String = "", // 发布者头像
    var content: String = "", // 评论内容
    var date: String = "", // 评论时间
    var vip: Int = 0, // 会员等级
    var upCount: Int = 0, // 点赞数量
    var up: Boolean = false, // 点赞
    var down: Boolean = false, // 踩
    var sb: Boolean = false, // UP觉得很傻逼
    var commentUp: Boolean = false, // UP评论
    var commentCount: Long = 0, // 子评论数量
    var comment: List<Comment> = listOf() // 子评论
) : BaseObservable() {

    /**
     * 当Vip达到6级需要显示红色用户名
     */
    fun getNameColor(): Int {
        return if (vip == 6) Color.parseColor("#ed6f98") else Color.parseColor("#62666d")
    }

    /** 显示更多子评论 */
    fun comment(): List<CharSequence> {
        val data = mutableListOf<CharSequence>()
        data.addAll(comment.map { it.getSpannable() })

        // 如果小于等于三条评论
        if (commentCount <= 3) return data

        val span = SpannableStringBuilder()

        // 如果UP参与评论
        if (commentUp) {
            span.addSpan("UP主等人 ", ForegroundColorSpan(Color.parseColor("#979ba1")))
        }

        // 如果超过三条子评论
        val more = span.addSpan("共${commentCount}条回复", ForegroundColorSpan(Color.parseColor("#3b87bf")))
            .addSpan("image", CenterImageSpan(app, R.drawable.ic_comment_arrow_right))
        data.add(more)
        return data
    }

    /** 点赞 */
    fun like() {
        // 一般情况下这里应当请求网络, 然后更新点赞数量
        if (up) {
            upCount--
        } else {
            upCount++
        }
        up = !up
        notifyChange()
    }

    @Serializable
    data class Comment(
        var name: String = "",
        var content: String = ""
    ) {

        /**
         * 因为需要换行所以使用Spannable来填充一个textView
         * 使用依赖库快速渲染评论内容 https://github.com/liangjingkanji/spannable
         */
        fun getSpannable(): CharSequence {
            return name.setSpan(ForegroundColorSpan(Color.parseColor("#3b87bf"))).addSpan(": $content").replaceSpan("@[^@]+?(?=\\s|\$)".toRegex()) {
                ForegroundColorSpan(Color.parseColor("#3b87bf"))
            }
        }
    }
}