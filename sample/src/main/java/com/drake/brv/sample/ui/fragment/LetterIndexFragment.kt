package com.drake.brv.sample.ui.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.drake.brv.sample.R
import com.drake.brv.sample.constants.Api
import com.drake.brv.sample.databinding.FragmentLetterIndexBinding
import com.drake.brv.sample.model.CityModel
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.drake.net.Get
import com.drake.net.utils.scope

class LetterIndexFragment : EngineFragment<FragmentLetterIndexBinding>(R.layout.fragment_letter_index) {

    private val models = mutableListOf<Any>()

    override fun initView() {

        binding.rv.setup {
            addType<CityModel.CityLetter>(R.layout.item_city_letter)
            addType<CityModel.City>(R.layout.item_city)
        }

        // 索引列表
        binding.indexBar.setOnTouchingLetterChangeListener {
            val indexOf = binding.rv.models?.indexOf(CityModel.CityLetter(it)) ?: -1
            if (indexOf != -1) {
                (binding.rv.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(indexOf, 0)
            }
        }

        // 自动搜索
        binding.etSearch.doAfterTextChanged {
            val input = it!!.toString()
            binding.rv.models = models.filter { v ->
                when {
                    input.isBlank() -> true
                    v is CityModel.CityLetter -> false
                    v is CityModel.City -> v.name.contains(input, true) || v.pinyin.contains(input, true)
                    else -> true
                }
            }
        }
    }

    override fun initData() {
        binding.state.onRefresh {
            scope {
                Get<List<CityModel>>(Api.CITY).await().forEach {
                    models.add(CityModel.CityLetter(it.initial)) // 转换为支持悬停的数据模型
                    models.addAll(it.list)
                }
                binding.rv.models = models
            }
        }.showLoading()
    }
}