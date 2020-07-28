/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.drake.brv.sample.R
import com.drake.brv.sample.model.CheckModel
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_check_mode.*


class CheckModeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check_mode, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_check_mode.linear().setup {

            addType<CheckModel>(R.layout.item_check_mode)

            // 长按进入编辑模式
            onLongClick(R.id.item) {
                toggle()
                setChecked(adapterPosition, true)
            }

            onClick(R.id.cb, R.id.item) {
                // 如果当前未处于选择模式下 点击无效
                if (!toggleMode && it == R.id.item) {
                    return@onClick
                }
                var checked = (getModel() as CheckModel).checked
                if (it == R.id.item) checked = !checked
                setChecked(adapterPosition, checked)
            }

            // 监听选择
            onChecked { position, isChecked, isAllChecked ->

                val model = getModel<CheckModel>(position)
                model.checked = isChecked
                model.notifyChange()

                // 刷新已选择计数器
                tv_checked_count.text = "已选择 ${checkedCount}/${modelCount}"

                // 全选按钮进行更新
                tv_all_checked.isChecked = isAllChecked
                tv_all_checked.isEnabled = !isAllChecked
            }

            // 监听切换模式
            onToggle { position, toggleMode, end ->
                val model = getModel<CheckModel>(position)
                model.visibility = toggleMode
                model.notifyChange()

                if (end) {
                    // 管理按钮
                    tv_manage.text = if (toggleMode) "取消" else "管理"

                    // 显示和隐藏编辑菜单
                    ll_menu.visibility = if (toggleMode) View.VISIBLE else View.GONE

                    // 显示/隐藏计数器
                    tv_checked_count.visibility = if (toggleMode) View.VISIBLE else View.GONE
                    tv_checked_count.text = "已选择 ${checkedCount}/${modelCount}"

                    // 如果取消管理模式则取消全部已选择
                    if (toggleMode) checkedAll(false)
                }
            }

        }.models = getData()

        initOperation()
    }

    /**
     * 初始化操作按钮
     */
    private fun initOperation() {
        val adapter = rv_check_mode.bindingAdapter

        // 单选模式切换
        tv_single_mode.setOnClickListener {
            adapter.singleMode = !adapter.singleMode

            // 单选模式不应该支持全选
            tv_all_checked.isEnabled = !adapter.singleMode
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

        // 切换选择模式
        tv_manage.setOnClickListener {
            adapter.toggle()
        }
    }

    private fun getData(): List<CheckModel> {
        return mutableListOf<CheckModel>().apply {
            for (i in 0..9) add(CheckModel())
        }
    }


}
