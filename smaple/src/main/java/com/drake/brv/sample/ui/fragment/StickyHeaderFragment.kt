package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.drake.brv.listener.OnStickyChangeListener
import com.drake.brv.sample.R
import com.drake.brv.sample.mod.Model
import com.drake.brv.sample.mod.StickyHeaderModel
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast
import kotlinx.android.synthetic.main.fragment_sticky_header.*


class StickyHeaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sticky_header, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_hover.linear(sticky = true).setup {
            addType<Model>(R.layout.item_multi_type_simple)
            addType<StickyHeaderModel>(R.layout.item_sticky_header)
            models = getData()


            // 可选项, 粘性监听器
            onStickyChangeListener = object : OnStickyChangeListener {
                override fun attachSticky(v: View) {
                    ViewCompat.setElevation(v, 10F)
                }

                override fun detachSticky(v: View) {
                    ViewCompat.setElevation(v, 0F)
                }

            }

        }

        // 点击事件
        rv_hover.bindingAdapter.onClick(R.id.item) {
            when (itemViewType) {
                R.layout.item_sticky_header -> toast("粘性头部")
                else -> toast("普通条目")
            }
        }
    }

    private fun getData(): List<Any> {
        return listOf(
            StickyHeaderModel(),
            Model(),
            Model(),
            Model(),
            StickyHeaderModel(),
            Model(),
            Model(),
            Model(),
            StickyHeaderModel(),
            Model(),
            Model(),
            Model(),
            Model(),
            Model(),
            Model(),
            Model(),
            Model(),
            Model(),
            Model(),
            Model()
        )
    }

}
