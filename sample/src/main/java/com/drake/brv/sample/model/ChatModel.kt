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
    fun getMessage(): List<ChatMessage> {
        val messages = listOf(ChatMessage(input, 0))
        input = ""
        return messages
    }

    /** 模拟拉取历史消息记录 */
    fun fetchHistory(): MutableList<ChatMessage> {
        return mutableListOf<ChatMessage>(
            ChatMessage("Hello, I'm Tom. I'm a chatbot.", 1),
            ChatMessage("What's your name?", 1),
            ChatMessage("My name is Mike.", 0),
        )
    }
}