package com.drake.brv.sample.model

@kotlinx.serialization.Serializable
data class GameModel(
    var total: Int = 0,
    var list: List<Data> = listOf()
) {

    @kotlinx.serialization.Serializable
    data class Data(
        var id: Int = 0,
        var img: String = "",
        var name: String = "",
        var label: List<String> = listOf(),
        var price: String = "",
        var initialPrice: String = "",
        var grade: Int = 0,
        var discount: Double = 0.0,
        var endTime: Int = 0
    )
}