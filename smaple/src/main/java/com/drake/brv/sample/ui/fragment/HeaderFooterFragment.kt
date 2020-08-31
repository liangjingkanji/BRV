/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.drake.brv.sample.R
import com.drake.brv.sample.model.Model
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_header_footer.*


class HeaderFooterFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_header_footer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        rv_header_footer.linear().setup {
            addType<Model>(R.layout.item_multi_type_simple)

            /**
             * BRV的数据集 = Header + Footer + Models
             * 所以本质上他们都是一组多类型而已, 我分出来只是为了方便替换Models而不影响Header和Footer
             */

            addType<Header>(R.layout.item_multi_type_header)
            addType<Footer>(R.layout.item_multi_type_footer)
        }.models = getData()
    }

    private fun getData(): List<Model> {
        return listOf(Model(), Model())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_header_footer, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val adapter = rv_header_footer.bindingAdapter
        when (item.itemId) {
            R.id.menu_add_header -> adapter.addHeader(Header(), animation = true)
            R.id.menu_remove_header -> adapter.removeHeaderAt(animation = true)  // 删除头布局
            R.id.menu_clear_header -> adapter.clearHeader(animation = true) // 清除头布局
            R.id.menu_add_footer -> adapter.addFooter(Footer(), animation = true)  // 添加脚布局
            R.id.menu_remove_footer -> adapter.removeFooterAt(animation = true)  // 删除脚布局
            R.id.menu_clear_footer -> adapter.clearFooter(animation = true)  // 清除脚布局
        }
        return super.onOptionsItemSelected(item)
    }

    class Header
    class Footer
}


