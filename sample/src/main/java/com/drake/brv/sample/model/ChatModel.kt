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
    suspend fun getHistory(page: Int): MutableList<ChatMessage> = withIO {
        if (page == 1) {
            mutableListOf(
                ChatMessage("开源的初衷是为了共同创造更好的工具方便大家", 1),
                ChatMessage("更多的star会让更多人参与其中 [星星]\nhttps://github.com/liangjingkanji/", 1),
                ChatMessage(
                    "没空", 0
                ),
            )
        } else {
            mutableListOf(
                ChatMessage("消息序列: ${System.nanoTime().toString().last()}", 1),
                ChatMessage("消息序列: ${System.nanoTime().toString().last()}", 0),
                ChatMessage("消息序列: ${System.nanoTime().toString().last()}", 1),
                ChatMessage("消息序列: ${System.nanoTime().toString().last()}", 1),
                ChatMessage("消息序列: ${System.nanoTime().toString().last()}", 0),
                ChatMessage("消息序列: ${System.nanoTime().toString().last()}", 1),
            )
        }.apply { reverse() }
    }
}