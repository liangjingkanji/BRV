package com.drake.brv.sample.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentHeaderFooterBinding
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import java.util.Random


class HeaderFooterFragment :
    EngineFragment<FragmentHeaderFooterBinding>(R.layout.fragment_header_footer) {

    override fun initView() {
        setHasOptionsMenu(true)

        binding.rv.linear().setup {
            addType<SimpleModel>(R.layout.item_simple)

            /**
             * BRV的数据集 = Header + Footer + Models
             * 所以本质上他们都是多类型而已, 区分出来只是为了修改Models时不影响Header和Footer
             */

            addType<Header>(R.layout.item_header)
            addType<Footer>(R.layout.item_footer)
        }.models = getData()
    }

    private fun getData(): List<SimpleModel> {
        return listOf(SimpleModel(), SimpleModel())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_header_footer, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val adapter = binding.rv.bindingAdapter
        when (item.itemId) {
            R.id.menu_add_header -> adapter.addHeader(Header(), animation = true)
            R.id.menu_remove_header -> adapter.removeHeaderAt(animation = true)  // 删除头布局
            R.id.menu_clear_header -> adapter.clearHeader(animation = true) // 清除头布局
            R.id.menu_add_footer -> adapter.addFooter(Footer(), animation = true)  // 添加脚布局
            R.id.menu_remove_footer -> adapter.removeFooterAt(animation = true)  // 删除脚布局
            R.id.menu_clear_footer -> adapter.clearFooter(animation = true)  // 清除脚布局
            R.id.menu_add_item -> adapter.addModels(randomModelList(), true)
            R.id.menu_delete_item -> {
                if (adapter.modelCount > 0) {
                    adapter.mutable.removeLast()
                    adapter.notifyItemRemoved(adapter.headerCount + adapter.modelCount)
                }
            }
            R.id.menu_clear_item -> {
                val count = adapter.modelCount
                adapter.mutable.clear()
                adapter.notifyItemRangeRemoved(adapter.headerCount, count)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun randomModelList(): List<SimpleModel> {
        val random = Random()
        val num = random.nextInt(3)
        val result = arrayListOf<SimpleModel>()
        for (i in 0..num) {
            result.add(SimpleModel())
        }
        return result
    }

    class Header

    class Footer

    override fun initData() {
    }
}


