package com.drake.brv.sample.ui.fragment.hover

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.annotaion.DividerOrientation
import com.drake.brv.listener.OnHoverAttachListener
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentHoverBinding
import com.drake.brv.sample.model.Group2Model
import com.drake.brv.sample.model.Group3Model
import com.drake.brv.sample.model.HoverHeaderModel
import com.drake.brv.utils.divider
import com.drake.brv.utils.grid
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.tooltip.toast


class HoverGridDividerFragment : BaseHoverFragment<FragmentHoverBinding>(R.layout.fragment_hover) {

    override fun initView() {
        setHasOptionsMenu(true)

        binding.rv.linear().setup {

            onCreate {
                if (itemViewType == R.layout.item_rv) { // 构建嵌套网格列表
                    findView<RecyclerView>(R.id.rv).divider { // 构建间距
                        setDivider(20)
                        includeVisible = true
                        orientation = DividerOrientation.GRID
                    }.grid(2).setup {
                        addType<Group3Model>(R.layout.item_group_none_margin)
                    }
                }
            }
            onBind {
                if (itemViewType == R.layout.item_rv) { // 为嵌套的网格列表赋值数据
                    findView<RecyclerView>(R.id.rv).models =
                        getModel<Group2Model>().getItemSublist()
                }
            }
            addType<Group2Model>(R.layout.item_rv)
            addType<HoverHeaderModel>(R.layout.item_hover_header)

            // 点击事件
            onClick(R.id.item) {
                when (itemViewType) {
                    R.layout.item_hover_header -> toast("悬停条目")
                    else -> toast("普通条目")
                }
            }

            // 可选项, 粘性监听器
            onHoverAttachListener = object : OnHoverAttachListener {
                override fun attachHover(v: View) {
                    ViewCompat.setElevation(v, 10F) // 悬停时显示阴影
                }

                override fun detachHover(v: View) {
                    ViewCompat.setElevation(v, 0F) // 非悬停时隐藏阴影
                }
            }
        }.models = getData()
    }

    private fun getData(): List<Any> {
        return listOf(
            HoverHeaderModel(),
            Group2Model(),
            HoverHeaderModel(),
            Group2Model(),
            HoverHeaderModel(),
            Group2Model(),
            HoverHeaderModel(),
            Group2Model(),
        )
    }

    override fun initData() {
    }

}
