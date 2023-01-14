package com.drake.brv.sample.model

@kotlinx.serialization.Serializable
data class NestedListModel(
    var title: String = "",
    var list: List<String> = listOf()
)