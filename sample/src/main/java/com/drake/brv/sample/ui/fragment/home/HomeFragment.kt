package com.drake.brv.sample.ui.fragment.home

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.drake.brv.sample.R
import com.drake.brv.sample.constants.Api
import com.drake.brv.sample.databinding.FragmentHomeBinding
import com.drake.brv.sample.interfaces.HomeBannerAdapter
import com.drake.brv.sample.model.HomeModel
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.engine.adapter.FragmentPagerAdapter
import com.drake.engine.base.EngineFragment
import com.drake.net.Get
import com.drake.net.utils.scope
import com.youth.banner.indicator.RoundLinesIndicator

class HomeFragment : EngineFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun initView() {
        // 轮播图
        binding.banner.setAdapter(HomeBannerAdapter())
            .setIntercept(false)
            .setIndicator(RoundLinesIndicator(requireContext()))

        // 探索
        binding.rvExplore.setup {
            addType<HomeModel.Explore>(R.layout.item_home_explore)
        }
        // 标签
        binding.rvTab.setup {
            singleMode = true
            addType<HomeModel.Tab>(R.layout.item_home_tab)
            onChecked { position, checked, _ ->
                val model = getModel<HomeModel.Tab>(position)
                model.checked = checked
                model.notifyChange()
            }
            R.id.item.onClick {
                setChecked(layoutPosition, true)
                binding.vp.setCurrentItem(layoutPosition, false)
            }
        }

        // 左右滑动页面自动切换标签
        binding.vp.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                binding.rvTab.bindingAdapter.setChecked(position, true)
            }
        })
    }

    override fun initData() {
        binding.page.onRefresh {
            scope {
                val res = Get<HomeModel>(Api.HOME).await()
                binding.m = res
                if (res.explore.isEmpty()) {
                    binding.llExplore.visibility = View.GONE
                } else {
                    binding.rvExplore.models = res.explore
                }
                binding.rvTab.models = res.tabs
                binding.rvTab.bindingAdapter.setChecked(0, true)
                binding.banner.setDatas(res.banner)
                binding.vp.adapter = FragmentPagerAdapter(res.tabs.map { GameFragment() })
            }
        }.showLoading()
    }
}