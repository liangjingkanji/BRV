package com.drake.brv.sample.model

import com.drake.brv.item.ItemHover
import kotlinx.serialization.Serializable

@Serializable
data class CityModel(
    var initial: String = "",
    var list: List<City> = listOf()
) {
    @Serializable
    data class City(
        var code: String = "",
        var name: String = "",
        var pinyin: String = "",
        var label: String = ""
    )


    // 悬停要求实现接口
    // data class 会自动重写equals方法, 方便匹配索引查询
    data class CityLetter(val letter: String, override var itemHover: Boolean = true) : ItemHover
}