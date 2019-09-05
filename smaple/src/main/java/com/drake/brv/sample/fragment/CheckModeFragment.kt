/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv.sample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.drake.brv.bindingAdapter
import com.drake.brv.linear
import com.drake.brv.sample.R
import com.drake.brv.sample.model.CheckModel
import com.drake.brv.setup
import kotlinx.android.synthetic.main.fragment_check_mode.*


class CheckModeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_check_mode, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_check_mode.linear().setup {
            addType<CheckModel>(R.layout.item_check_mode)

            onLongClick(R.id.item) {
                toggle()
                setChecked(adapterPosition, true)
            }

            onCheckedChange { itemType, position, isChecked, isAllChecked ->
                val model = getModel<CheckModel>(position)
                model?.checked = true
                model?.notifyChange()
            }

            onToggle { itemType, position, toggleMode ->
                val model = getModel<CheckModel>(position)
                model?.visibility = toggleMode
                model?.notifyChange()
            }

            onToggleEnd {
                tv_manage.text = if (it) "取消" else "管理"
                ll_menu.visibility = if (it) View.VISIBLE else View.GONE
            }

        }.models = listOf(
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel(),
            CheckModel()
        )


        val adapter = rv_check_mode.bindingAdapter


        // 单选模式切换
        tv_single_mode.setOnClickListener {
            adapter.singleMode = !adapter.singleMode
        }


        // 反选
        tv_reverse_checked.setOnClickListener {
            adapter.checkedReverse()
        }

        // 全选
        tv_all_checked.setOnClickListener {
            adapter.checkedAll()
        }

        // 取消选择
        tv_cancel_checked.setOnClickListener {
            adapter.checkedAll(false)
        }

        tv_manage.setOnClickListener {
            adapter.toggle()
        }
    }


}
