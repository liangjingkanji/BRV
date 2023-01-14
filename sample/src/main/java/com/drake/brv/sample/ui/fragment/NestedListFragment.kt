package com.drake.brv.sample.ui.fragment

import com.drake.brv.sample.R
import com.drake.brv.sample.component.net.SerializationConverter
import com.drake.brv.sample.databinding.FragmentNestedListBinding
import com.drake.brv.sample.databinding.ItemNestedHorizontalRvBinding
import com.drake.brv.sample.model.NestedListModel
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import kotlinx.serialization.json.decodeFromStream

class NestedListFragment : EngineFragment<FragmentNestedListBinding>(R.layout.fragment_nested_list) {
    override fun initView() {
        binding.rv.setup {
            addType<NestedListModel>(R.layout.item_nested_horizontal_rv)
            onCreate {
                getBinding<ItemNestedHorizontalRvBinding>().rv.setup {
                    addType<String>(R.layout.item_simple_horizontal)
                }
            }
            onBind {
                val model = getModel<NestedListModel>()
                getBinding<ItemNestedHorizontalRvBinding>().rv.models = model.list
            }
        }
    }

    override fun initData() {
        binding.rv.models = SerializationConverter.jsonDecoder.decodeFromStream<List<NestedListModel>>(resources.openRawResource(R.raw.list_nested))
    }
}