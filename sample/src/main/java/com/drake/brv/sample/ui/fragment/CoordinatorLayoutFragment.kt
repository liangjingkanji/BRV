package com.drake.brv.sample.ui.fragment

import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentCoordinatorLayoutBinding
import com.drake.engine.adapter.FragmentPagerAdapter
import com.drake.engine.base.EngineFragment

class CoordinatorLayoutFragment :
    EngineFragment<FragmentCoordinatorLayoutBinding>(R.layout.fragment_coordinator_layout) {
    override fun initView() {
        val fragments = listOf(PreloadFragment(), PreloadFragment())
        val titles = listOf("简介", "评论")
        binding.vp.adapter = FragmentPagerAdapter(fragments, titles)
        binding.tab.setupWithViewPager(binding.vp)
    }

    override fun initData() {
    }
}