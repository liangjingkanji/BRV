package com.drake.brv.sample.model

import androidx.databinding.BaseObservable
import com.drake.net.utils.withIO

class ChatModel : BaseObservable() {

    /** 消息输入框内容 */
    var input: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    /** 是否可以发送消息 */
    fun isSendEnable(): Boolean {
        return input.isNotEmpty()
    }

    /** 当前输入的消息数据类 */
    fun getMessages(): List<ChatMessage> {
        val messages = listOf(ChatMessage(input, 0))
        input = ""
        return messages
    }

    /** 模拟拉取历史消息记录 */
    suspend fun fetchHistory(page: Int): MutableList<ChatMessage> = withIO {
        if (page == 1) {
            mutableListOf(
                ChatMessage("Can you give me a star? [星星]\nhttps://github.com/liangjingkanji/", 1),
                ChatMessage("Give you [星星]", 0),
                ChatMessage("WTF?", 1),
            )
        } else {
            mutableListOf(
                ChatMessage("消息" + System.currentTimeMillis(), 1),
                ChatMessage("消息" + System.currentTimeMillis(), 0),
                ChatMessage("消息" + System.currentTimeMillis(), 1),
            )
        }
    }
}