package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.drake.brv.sample.R
import com.drake.brv.sample.mod.GroupModel
import com.drake.brv.sample.mod.Model
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_group.*


class GroupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_group.setup {
            addType<GroupModel>(R.layout.item_group_title)
            addType<Model>(R.layout.item_multi_type_simple)

            onClick(R.id.item) {
                when (it) {
                    R.layout.item_group_title -> switch()
                }
            }
        }.models = getData()

    }


    private fun getData(): MutableList<GroupModel> {
        return mutableListOf<GroupModel>().apply {
            for (i in 0..4) {
                add(GroupModel())
            }
        }
    }

}
