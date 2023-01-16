package com.drake.brv.sample.ui.fragment.home

import com.drake.brv.sample.R
import com.drake.brv.sample.constants.Api
import com.drake.brv.sample.databinding.FragmentGameBinding
import com.drake.brv.sample.model.GameModel
import com.drake.brv.utils.dividerSpace
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.engine.utils.dp
import com.drake.net.Get
import com.drake.net.utils.TipUtils.toast
import com.drake.net.utils.scope
import com.drake.serialize.intent.bundle
import java.util.*

class GameFragment : EngineFragment<FragmentGameBinding>(R.layout.fragment_game) {

    private val categoryId: Long by bundle()

    override fun initView() {
        binding.rv.dividerSpace(10.dp).setup {
            addType<GameModel.Data>(R.layout.item_game)
            R.id.item.onClick {
                toast("点击: 游戏详情(${getModel<GameModel.Data>().name})")
            }
        }
    }

    override fun initData() {
        binding.page.onRefresh {
            scope {
                val res = Get<GameModel>(Api.GAME) {
                    param("categoryId", categoryId)
                }.await()
                addData(res.list.shuffled(Random(categoryId))) { // shuffled() 为随机打乱列表顺序
                    itemCount < res.total
                }
            }
        }.showLoading()
    }
}