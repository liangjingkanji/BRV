package com.drake.brv.sample.ui.fragment

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentDifferDataChangeBinding
import com.drake.brv.sample.model.DiffModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setDifferModels
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import kotlin.concurrent.thread

/** 对比数据, 根据数据变化更新列表 */
class DifferDataChangeFragment : EngineFragment<FragmentDifferDataChangeBinding>(R.layout.fragment_differ_data_change) {

    override fun initView() {
        setHasOptionsMenu(true)

        binding.rv.linear().setup {
            addType<DiffModel>(R.layout.item_simple_text)
            onBind {
                findView<TextView>(R.id.tv_simple).text = getModel<DiffModel>().content
            }

            // 如果要求刷新不白屏请参考以下代码逻辑
            // itemDifferCallback = object : ItemDifferCallback {
            //
            //     override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            //         return (oldItem as DiffModel).id == (newItem as DiffModel).id
            //     }
            //
            //     override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            //         return (oldItem as DiffModel).content == (newItem as DiffModel).content
            //     }
            //
            //     override fun getChangePayload(oldItem: Any, newItem: Any): Any? {
            //         return true
            //     }
            // }

        }.models = getRandomData(true)

    }

    override fun initData() {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_differ_data_change, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 主线程刷新
            R.id.menu_random_data -> binding.rv.setDifferModels(getRandomData(), false)
            R.id.menu_random_data_async -> thread { // 异步线程对比刷新(避免大量数据对比阻塞主线程)
                binding.rv.setDifferModels(getRandomData()) {
                    // 刷新完成
                    Log.d("BRV", "刷新完成")
                }
            }
        }
        return true
    }

    /**
     * 返回随机的集合
     * @param order 是否按顺序显示
     */
    private fun getRandomData(order: Boolean = false): MutableList<Any> {
        return mutableListOf<Any>().apply {
            for (i in 0..9) {
                val id = i.toString()
                if (i == 3) {
                    add(DiffModel(id, System.currentTimeMillis().toString()))
                } else {
                    add(DiffModel(id, id))
                }
            }
            if (!order) shuffle()
        }
    }
}