package com.drake.brv.sample.model

import com.drake.brv.sample.R
import com.drake.engine.base.app
import com.drake.engine.utils.dp
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.CenterImageSpan

private const val currentUserId = 0 // 假设这是当前用户ID

data class ChatMessage(
    val content: CharSequence,
    val userId: Int,
    val avatar: String = "https://avatars.githubusercontent.com/u/21078112?v=4"
) {

    /** 字符串自动替换为图标规则 */
    private val emojiRules = mapOf(
        "[星星]" to R.drawable.ic_msg_star,
    )

    /** 渲染过后的消息 */
    fun getRichMessage(): CharSequence {
        var result: CharSequence = content
        emojiRules.forEach { rule -> // 应用所有规则
            result = result.replaceSpan(rule.key) {
                CenterImageSpan(app, rule.value).setDrawableSize(18.dp)
            }
        }
        return result
    }

    fun isMine(): Boolean {
        return currentUserId == userId
    }
}
