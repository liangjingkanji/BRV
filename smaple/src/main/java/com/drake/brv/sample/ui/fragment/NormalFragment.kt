package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.drake.brv.sample.R
import com.drake.brv.sample.model.NormalModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_normal.*

class NormalFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_normal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_normal.linear().setup {
            addType<NormalModel>(R.layout.item_multi_type_normal)
            onBind { // 直接在View中绑定数据, 不使用DataBinding(推荐使用)
                findView<TextView>(R.id.tv_normal).text = getModel<NormalModel>().itemPosition.toString()
            }
        }.models = getData()
    }

    private fun getData(): MutableList<Any> {
        // 在Model中也可以绑定数据, 类似于BRVAH那种方式
        return mutableListOf(NormalModel(), NormalModel(), NormalModel(), NormalModel(), NormalModel(),
                             NormalModel(), NormalModel(), NormalModel(), NormalModel(), NormalModel(),
                             NormalModel(), NormalModel(), NormalModel(), NormalModel())
    }
}