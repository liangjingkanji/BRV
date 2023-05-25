package com.drake.brv.sample.ui.fragment

import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentOneMoreTypeBinding
import com.drake.brv.sample.model.OneMoreModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment


class OneMoreTypeFragment :
    EngineFragment<FragmentOneMoreTypeBinding>(R.layout.fragment_one_more_type) {

    override fun initView() {
        binding.rv.linear().setup {
            addType<OneMoreModel> {
                when (type) {
                    0 -> R.layout.item_one_more
                    else -> R.layout.item_full_span
                }
            }
        }.models = getData()
    }

    private fun getData(): List<OneMoreModel> {
        return listOf(
            OneMoreModel(0),
            OneMoreModel(0),
            OneMoreModel(1),
            OneMoreModel(1),
            OneMoreModel(0),
            OneMoreModel(0),
            OneMoreModel(1),
            OneMoreModel(1),
            OneMoreModel(0),
            OneMoreModel(0),
            OneMoreModel(0),
            OneMoreModel(1),
            OneMoreModel(0),
            OneMoreModel(0),
            OneMoreModel(0),
            OneMoreModel(1),
            OneMoreModel(0),
            OneMoreModel(0)
        )
    }

    override fun initData() {
    }

}
