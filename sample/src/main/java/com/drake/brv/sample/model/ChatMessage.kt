package com.drake.brv.sample.model

import android.graphics.Color
import android.text.style.URLSpan
import com.drake.brv.sample.R
import com.drake.engine.base.app
import com.drake.engine.utils.dp
import com.drake.spannable.replaceSpan
import com.drake.spannable.span.CenterImageSpan
import com.drake.spannable.span.ColorSpan

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
        val urlRule = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]"

        emojiRules.forEach { rule -> // 应用所有规则
            result = result.replaceSpan(rule.key) {
                CenterImageSpan(app, rule.value).setDrawableSize(18.dp)
            }.replaceSpan(urlRule.toRegex()) {
                URLSpan(it.value)
            }
            // 避免URL颜色被覆盖
            if (isSelf()) {
                result = result.replaceSpan(urlRule.toRegex()) {
                    ColorSpan(Color.WHITE)
                }
            }
        }
        return result
    }

    fun isSelf(): Boolean {
        return currentUserId == userId
    }
}
