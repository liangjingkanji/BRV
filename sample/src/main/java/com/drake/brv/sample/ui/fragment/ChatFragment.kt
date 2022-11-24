package com.drake.brv.sample.ui.fragment

import android.annotation.SuppressLint
import android.view.View
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentChatBinding
import com.drake.brv.sample.model.ChatMessage
import com.drake.brv.sample.model.ChatModel
import com.drake.brv.utils.addModels
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.net.utils.scope
import com.drake.softinput.hideSoftInput
import com.drake.softinput.setWindowSoftInput

class ChatFragment : EngineFragment<FragmentChatBinding>(R.layout.fragment_chat) {

    private val model = ChatModel()

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        binding.v = this
        binding.m = model

        // 键盘弹出平滑动画
        setWindowSoftInput(float = binding.llInput, setPadding = true)

        binding.rv.setup {
            addType<ChatMessage> {
                if (isMine()) {
                    R.layout.item_msg_right // 我发的消息
                } else {
                    R.layout.item_msg_left // 对方发的消息
                }
            }
        }
        binding.rv.setOnTouchListener { v, _ ->
            v.clearFocus() // 清除文字选中状态
            hideSoftInput() // 隐藏键盘
            false
        }
    }

    override fun initData() {
        binding.page.onRefresh {
            scope {
                val data = model.fetchHistory(index) // 模拟拉取历史记录
                addData(data) {
                    itemCount < 1
                }
            }
        }.refresh()
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btnSend -> {
                binding.rv.addModels(model.getMessages(), index = 0) // 添加一条消息
                binding.rv.scrollToPosition(0) // 保证最新一条消息显示
            }
            binding.rv -> {
                hideSoftInput() // 隐藏键盘
            }
        }
    }
}