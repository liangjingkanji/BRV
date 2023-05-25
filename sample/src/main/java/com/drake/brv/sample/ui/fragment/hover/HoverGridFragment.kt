package com.drake.brv.sample.ui.fragment.hover

import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.drake.brv.layoutmanager.HoverGridLayoutManager
import com.drake.brv.listener.OnHoverAttachListener
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentHoverBinding
import com.drake.brv.sample.model.HoverHeaderModel
import com.drake.brv.sample.model.SimpleModel
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.setup
import com.drake.tooltip.toast


class HoverGridFragment : BaseHoverFragment<FragmentHoverBinding>(R.layout.fragment_hover) {

    override fun initView() {
        setHasOptionsMenu(true)

        val layoutManager = HoverGridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (binding.rv.bindingAdapter.isHover(position)) 2 else 1 // 具体的业务逻辑由你确定
            }
        }
        binding.rv.layoutManager = layoutManager

        binding.rv.setup {
            addType<SimpleModel>(R.layout.item_simple)
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
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            HoverHeaderModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            HoverHeaderModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel(),
            SimpleModel()
        )
    }

    override fun initData() {
    }

}
