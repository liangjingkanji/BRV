package com.drake.brv.sample.model

import androidx.databinding.BaseObservable

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
    fun fetchHistory(): MutableList<ChatMessage> {
        return mutableListOf(
            ChatMessage("Can you give me a star? [星星]\nhttps://github.com/liangjingkanji/", 1),
            ChatMessage("Give you [星星]", 0),
            ChatMessage("WTF?", 1),
        )
    }
}