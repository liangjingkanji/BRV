package com.drake.brv.sample.ui.fragment.home

import com.drake.brv.sample.R
import com.drake.brv.sample.constants.Api
import com.drake.brv.sample.databinding.FragmentHomeListBinding
import com.drake.brv.sample.databinding.ItemHomeBannerBinding
import com.drake.brv.sample.databinding.ItemHomeGridBinding
import com.drake.brv.sample.interfaces.HomeBannerAdapter
import com.drake.brv.sample.model.GameModel
import com.drake.brv.sample.model.HomeModel
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.net.Get
import com.drake.net.utils.scope
import com.drake.statelayout.Status
import com.drake.tooltip.toast
import com.youth.banner.indicator.RoundLinesIndicator


/**
 * 演示如何使用BRV+NET(https://github.com/liangjingkanji/Net)快速构建应用首页(MVVM架构)
 *
 * 使用RecyclerView实现首页布局
 */
class HomeListFragment : EngineFragment<FragmentHomeListBinding>(R.layout.fragment_home_list) {
    override fun initView() {

        binding.rv.setup {
            addType<HomeModel.Event>(R.layout.item_home_image)

            // 当你添加两个相同类型的嵌套泛型时为防止擦除泛型, 请注意查看HomeModel.getAvailableData的处理
            addType<List<HomeModel.Banner>>(R.layout.item_home_banner)
            addType<List<HomeModel.Explore>>(R.layout.item_home_grid)
            addType<GameModel.Data>(R.layout.item_game)
            addType<String>(R.layout.item_home_title)

            addType<Status>(R.layout.layout_empty) // 如果你需要游戏列表为空时列表区域显示空缺省页

            onCreate {
                when (itemViewType) {
                    R.layout.item_home_banner -> {
                        getBinding<ItemHomeBannerBinding>().banner.setAdapter(HomeBannerAdapter())
                            .setIndicator(RoundLinesIndicator(requireContext()))
                            .setOnBannerListener { _, position ->
                                toast("点击: 轮播图(${position})")
                            }.setIntercept(false)
                    }
                    R.layout.item_home_grid -> {
                        val itemHomeGridBinding = getBinding<ItemHomeGridBinding>()
                        itemHomeGridBinding.tvMoreExplore.setOnClickListener {
                            toast("点击: 更多")
                        }
                        itemHomeGridBinding.rvExplore.setup {
                            addType<HomeModel.Explore>(R.layout.item_home_explore)
                            R.id.item.onClick {
                                toast("点击: 探索(${layoutPosition})")
                            }
                        }
                    }
                }
            }
            onBind {
                when (itemViewType) {
                    R.layout.item_home_banner -> {
                        getBinding<ItemHomeBannerBinding>().banner.setDatas(getModel<List<HomeModel.Banner>>())
                    }
                    R.layout.item_home_grid -> {
                        getBinding<ItemHomeGridBinding>().rvExplore.models = getModel<List<HomeModel.Explore>>()
                    }
                }
            }
            R.id.image.onClick {
                toast("点击: 活动页面")
            }
            R.id.item.onClick {
                toast("点击: 游戏详情(${getModel<GameModel.Data>().name})")
            }
        }
    }

    override fun initData() {
        binding.page.onRefresh {
            scope {
                val models: List<Any>
                val game = Get<GameModel>(Api.GAME) {
                    param("page", index)
                }.await()

                // 如果是第一页, 则获取首页数据
                models = if (index == 1) {
                    val home = Get<HomeModel>(Api.HOME).await()
                    home.getAvailableData(game.list)
                } else { // 否则直接获取游戏列表
                    game.list
                }
                addData(models) {
                    index < game.totalPage
                }
            }
        }.showLoading()
    }
}