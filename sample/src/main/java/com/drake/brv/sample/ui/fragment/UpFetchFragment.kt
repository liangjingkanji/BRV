package com.drake.brv.sample.ui.fragment

import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentUpFetchBinding
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment


/**
 * 聊天室列表下拉加载历史记录示例
 */
class UpFetchFragment : EngineFragment<FragmentUpFetchBinding>(R.layout.fragment_up_fetch) {

    override fun initView() {
        binding.rv.setup {
            addType<SimpleModel>(R.layout.item_simple)
        }

        binding.page.upFetchEnabled = true

        binding.page.onRefresh {
            // 模拟网络请求2秒后成功
            postDelayed({
                val data = getData()
                addData(data) { index <= 2 }
            }, 1000)
        }.showLoading() //  加载中(缺省页)
    }

    /**
     * 模拟数据
     */
    private fun getData(): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..9) {
                add(SimpleModel())
            }
        }
    }

    override fun initData() {
    }

}