package com.drake.brv.sample.ui.fragment

import com.drake.brv.annotaion.ItemOrientation
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentSwipeBinding
import com.drake.brv.sample.model.SwipeModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment


class SwipeFragment : EngineFragment<FragmentSwipeBinding>(R.layout.fragment_swipe) {

    override fun initView() {
        // 侧滑删除会改变数据的内容
        binding.rv.linear().setup {
            addType<SwipeModel>(R.layout.item_swipe)
        }.models = getData()
    }

    private fun getData(): List<SwipeModel> {
        return listOf(
            SwipeModel(ItemOrientation.LEFT), // 左划
            SwipeModel(),
            SwipeModel(ItemOrientation.RIGHT), // 右划
            SwipeModel(),
            SwipeModel(ItemOrientation.NONE),  // 不支持侧滑
            SwipeModel(),
            SwipeModel(ItemOrientation.NONE), // 不支持侧滑
            SwipeModel()
        )
    }

    override fun initData() {
    }

}
