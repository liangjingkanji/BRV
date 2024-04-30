package com.drake.brv.sample.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentPullRefreshBinding
import com.drake.brv.sample.model.FullSpanModel
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.net.utils.TipUtils.toast


class PullRefreshFragment : EngineFragment<FragmentPullRefreshBinding>(R.layout.fragment_pull_refresh) {

    private val total = 2

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

                // addData(data, binding.rv.bindingAdapter, isEmpty = {
                //     true // 此处判断是否存在下一页
                // }, hasMore = {
                //     false // 此处判断是否显示空布局
                // })
            }
            postDelayed(runnable, 2000)

            toast("右上角菜单可以操作刷新结果, 默认2s结束")
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
        inflater.inflate(R.menu.menu_refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_loading -> binding.page.showLoading()  // 加载中
            R.id.menu_pull_refresh -> binding.page.autoRefresh() // 下拉刷新
            R.id.menu_refresh -> binding.page.refresh() // 静默刷新
            R.id.menu_content -> binding.page.showContent() // 加载成功
            R.id.menu_error -> binding.page.showError(force = true) // 强制加载错误
            R.id.menu_empty -> binding.page.showEmpty() // 空数据
            R.id.menu_refresh_success -> binding.page.finish() // 刷新成功
            R.id.menu_refresh_fail -> binding.page.finish(false) // 刷新失败
            R.id.menu_no_load_more -> binding.page.finishLoadMoreWithNoMoreData() // 没有更多数据
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
    }
}
