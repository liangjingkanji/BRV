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

    /** 渲染过后的消息 */
    fun getRichMessage(): CharSequence {
        return content.replaceSpan("[星星]") {
            CenterImageSpan(app, R.drawable.ic_msg_star).setDrawableSize(18.dp)
        }
    }

    fun isMine(): Boolean {
        return currentUserId == userId
    }
}
