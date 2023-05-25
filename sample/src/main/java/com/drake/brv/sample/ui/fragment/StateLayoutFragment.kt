package com.drake.brv.sample.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentStateLayoutBinding
import com.drake.brv.sample.model.FullSpanModel
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment


class StateLayoutFragment :
    EngineFragment<FragmentStateLayoutBinding>(R.layout.fragment_state_layout) {

    override fun initView() {
        setHasOptionsMenu(true)
        binding.rv.linear().setup {
            addType<SimpleModel>(R.layout.item_simple)
            addType<FullSpanModel>(R.layout.item_full_span)
        }.models = getData()

    }

    private fun getData(): List<Any> {
        return listOf(
            SimpleModel(),
            FullSpanModel(),
            FullSpanModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            FullSpanModel(),
            FullSpanModel(),
            FullSpanModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel()
        )
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_state, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_loading -> binding.state.showLoading()  // 加载中
            R.id.menu_content -> binding.state.showContent() // 加载成功
            R.id.menu_error -> binding.state.showError() // 加载错误
            R.id.menu_empty -> binding.state.showEmpty() // 加载失败
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
    }

}
