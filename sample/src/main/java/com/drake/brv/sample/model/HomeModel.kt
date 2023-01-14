package com.drake.brv.sample.model

import androidx.databinding.BaseObservable

@kotlinx.serialization.Serializable
data class HomeModel(
    var banner: MutableList<Banner> = mutableListOf(),
    var explore: List<Explore> = listOf(),
    var event: Event = Event(),
    var tabs: List<Tab> = listOf()
) {
    @kotlinx.serialization.Serializable
    data class Banner(
        var id: Int = 0,
        var image: String = "",
    )

    @kotlinx.serialization.Serializable
    data class Explore(
        var id: Int = 0,
        var image: String = "",
    )

    @kotlinx.serialization.Serializable
    data class Event(
        var id: Int = 0,
        var image: String = "",
    )

    @kotlinx.serialization.Serializable
    data class Tab(
        var id: Int = 0,
        var title: String = "",
        var count: Int = 0
    ) : BaseObservable() {
        var checked = false
    }
}