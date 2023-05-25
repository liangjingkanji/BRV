package com.drake.brv.sample.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentSkeletonBinding
import com.drake.brv.sample.interfaces.LeastAnimationStateChangedHandler
import com.drake.brv.sample.model.FullSpanModel
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.tooltip.toast

class SkeletonFragment : EngineFragment<FragmentSkeletonBinding>(R.layout.fragment_skeleton) {

    private val total = 3

    override fun initView() {
        setHasOptionsMenu(true)

        binding.rv.linear().setup {
            addType<SimpleModel>(R.layout.item_simple)
            addType<FullSpanModel>(R.layout.item_full_span)
        }

        // 该处理者可以保证骨骼动图显示最短时间(避免网络请求过快导致骨骼动画快速消失屏幕闪烁), 如果不需要可以不配置
        // 推荐在Application中全局配置, 而不是每次都配置
        binding.page.stateChangedHandler = LeastAnimationStateChangedHandler()

        binding.page.onRefresh {

            val runnable = { // 模拟网络请求, 创建假的数据集
                val data = getData()
                addData(data) {
                    index < total // 判断是否有更多页
                }
            }
            postDelayed(runnable, 500)

            toast("右上角菜单可以操作刷新结果, 默认2s结束")
        }.showLoading()
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
        inflater.inflate(R.menu.menu_skeleton, menu)
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