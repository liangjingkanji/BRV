package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.drake.brv.item.ItemExpand
import com.drake.brv.sample.R
import com.drake.brv.sample.model.GroupModel
import com.drake.brv.sample.model.Model
import com.drake.brv.sample.model.NestedGroupModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.tooltip.toast
import kotlinx.android.synthetic.main.fragment_group.*


class GroupFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_group.linear().setup {

            // 任何条目都需要添加类型到BindingAdapter中
            addType<GroupModel>(R.layout.item_group_title)
            addType<NestedGroupModel>(R.layout.item_nested_group_title)
            addType<Model>(R.layout.item_multi_type_simple)
            addFastClickable(R.id.item)

            onClick {
                when (itemViewType) {
                    R.layout.item_nested_group_title, R.layout.item_group_title -> {
                        val changeCount = if (getModel<ItemExpand>().itemExpand) "折叠 ${expandOrCollapse()} 条" else "展开 ${expandOrCollapse()} 条"
                        toast(changeCount)
                    }
                }
            }

        }.models = getData()
    }


    private fun getData(): MutableList<GroupModel> {
        return mutableListOf<GroupModel>().apply {
            for (i in 0..4) {

                // 第二个分组存在嵌套分组
                if (i == 0) {
                    val nestedGroupModel = GroupModel().apply {
                        itemSublist = listOf(NestedGroupModel(), NestedGroupModel(), NestedGroupModel())
                    }
                    add(nestedGroupModel)
                    continue
                }

                add(GroupModel())
            }
        }
    }

}
