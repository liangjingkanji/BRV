package com.drake.brv.sample.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentPagePartialStateBinding
import com.drake.brv.sample.model.FullSpanModel
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment


class PagePartialStateFragment : EngineFragment<FragmentPagePartialStateBinding>(R.layout.fragment_page_partial_state) {

    private val total = 6

    override fun initView() {
        setHasOptionsMenu(true)

        binding.rv.linear().setup {
            addType<SimpleModel>(R.layout.item_simple)
            addType<FullSpanModel>(R.layout.item_full_span)
        }

        binding.page.onRefresh {
            val runnable = { // 模拟网络请求, 创建假的数据集
                val data = getData()
                addData(data) {
                    index < total // 判断是否有更多页
                }
            }
            postDelayed(runnable, 2000)
        }.autoRefresh()
    }

    private fun getData(): List<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..9) {
                when (i) {
                    1, 2 -> add(FullSpanModel())
                    else -> add(SimpleModel())
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_state, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_loading -> binding.page.showLoading()  // 加载中
            R.id.menu_content -> binding.page.showContent() // 加载成功
            R.id.menu_error -> binding.page.showError(force = true) // 强制加载错误
            R.id.menu_empty -> binding.page.showEmpty() // 空数据
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
    }
}
