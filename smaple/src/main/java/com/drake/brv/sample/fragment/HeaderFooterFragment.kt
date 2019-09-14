/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：9/11/19 6:49 PM
 */

package com.drake.brv.sample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.drake.brv.sample.R
import com.drake.brv.sample.model.Model
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_header_footer.*


class HeaderFooterFragment : Fragment() {


    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_header_footer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_header_footer.linear().setup {
            addType<Model>(R.layout.item_multi_type_1)

            /**
             * BRV的数据集 = Header + Footer + Models
             * 所以本质上他们都是一组多类型而已, 我分出来只是为了方便替换Models而不影响Header和Footer
             */

            addType<Header>(R.layout.item_multi_type_1)
            addType<Footer>(R.layout.item_multi_type_1)
        }.models = listOf(
            Model(),
            Model()
        )

        initToolbar()
    }

    var index = 0


    private fun initToolbar() {

        val adapter = rv_header_footer.bindingAdapter

        toolbar = activity!!.findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_header_footer)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add_header -> adapter.addHeader(Header(), animation = true)
                R.id.menu_remove_header -> adapter.removeHeaderAt(animation = true)  // 删除头布局
                R.id.menu_clear_header -> adapter.clearHeader() // 清除头布局
                R.id.menu_add_footer -> adapter.addFooter(Footer())  // 添加脚布局
                R.id.menu_remove_footer -> adapter.removeFooterAt()  // 删除脚布局
                R.id.menu_clear_footer -> adapter.clearFooter(true)  // 清除脚布局
            }
            true
        }

    }

    class Header
    class Footer


}


