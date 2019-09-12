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
import androidx.fragment.app.Fragment
import com.drake.brv.sample.R
import com.drake.brv.sample.model.MultiType1Model
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_header_footer.*


class HeaderFooterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_header_footer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_header_footer.linear().setup {
            addType<MultiType1Model>(R.layout.item_multi_type_1)
        }.models = listOf(
            MultiType1Model(),
            MultiType1Model(),
            MultiType1Model()
        )


        btn_add_header.setOnClickListener {
            val view =
                layoutInflater.inflate(R.layout.item_multi_type_1, rv_header_footer, false)
            rv_header_footer.bindingAdapter.addHeader(view)
//            rv_header_footer.bindingAdapter.removeHeader(view)
        }

        btn_add_footer.setOnClickListener {
            val view =
                layoutInflater.inflate(R.layout.item_multi_type_1, rv_header_footer, false)
            rv_header_footer.bindingAdapter.addFooter(view)
//            rv_header_footer.bindingAdapter.removeFooter(view)
        }
    }

}
