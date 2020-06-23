package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.drake.brv.PageRefreshLayout
import com.drake.brv.sample.R
import com.drake.brv.sample.model.Model
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_preload.*

/**
 * 指定预加载, 默认反序3 开始预加载, 可设置全局变量 [PageRefreshLayout.preloadIndex]
 */
class PreloadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preload, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_pre_load.setup {
            addType<Model>(R.layout.item_multi_type_simple)
        }

        // page.preloadIndex = 4 // 自定义列表倒数第4个开始预加载, 默认为3

        page.onRefresh {
            // 模拟网络请求2秒后成功
            postDelayed({
                val data = getData()
                addData(data) {
                    index <= 4
                }
            }, 1000)
        }.showLoading() //  加载中(缺省页)
    }

    /**
     * 模拟数据
     */
    private fun getData(): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..9) {
                add(Model())
            }
        }
    }
}