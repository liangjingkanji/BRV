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
                R.id.menu_add_header -> {

                    adapter.addHeader(R.layout.item_multi_type_1)

                    /* (adapter.models as ArrayList).add(index, Model())
                     adapter.notifyItemInserted(index)*/


                } // 添加头布局
//                R.id.menu_remove_header -> adapter.removeHeader()  // 删除头布局
                R.id.menu_clear_header -> adapter.clearHeader(true) // 清除头布局
                R.id.menu_add_footer -> adapter.addFooter(R.layout.item_multi_type_1)  // 添加脚布局
//                R.id.menu_remove_footer -> adapter.removeFooter()  // 删除脚布局
                R.id.menu_clear_footer -> adapter.clearFooter()  // 清除脚布局
            }
            true
        }

    }


    fun getHeaderOrFooter(): View {
        return LayoutInflater.from(activity).inflate(
            R.layout.item_multi_type_1,
            rv_header_footer,
            false
        )
    }


}


