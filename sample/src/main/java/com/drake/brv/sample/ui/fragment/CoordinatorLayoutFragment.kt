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

        // `CoordinatorLayout+ViewPager`使用缺省页要求缺省页的XML根布局为 NestedScrollView, 否则显示缺省页后无法正常滑动
    }

    override fun initData() {
    }
}