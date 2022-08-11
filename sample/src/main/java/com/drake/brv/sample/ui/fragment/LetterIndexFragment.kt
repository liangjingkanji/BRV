package com.drake.brv.sample.ui.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.drake.brv.sample.R
import com.drake.brv.sample.databinding.FragmentLetterIndexBinding
import com.drake.brv.sample.model.CityModel
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class LetterIndexFragment : EngineFragment<FragmentLetterIndexBinding>(R.layout.fragment_letter_index) {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override fun initView() {

        binding.rv.setup {
            addType<CityModel.CityLetter>(R.layout.item_city_letter)
            addType<CityModel.City>(R.layout.item_city)
        }.models = getData()

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
            binding.rv.models = getData().filter { v ->
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
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun getData(): MutableList<Any> {
        val models = mutableListOf<Any>()
        json.decodeFromStream<List<CityModel>>(resources.openRawResource(R.raw.city)).forEach {
            models.add(CityModel.CityLetter(it.initial)) // 转换为支持悬停的数据模型
            models.addAll(it.list)
        }
        return models
    }
}