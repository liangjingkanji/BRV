package com.drake.brv.sample.model

interface BaseInterfaceModel {
    var text: String
}

data class InterfaceModel1(override var text: String) : BaseInterfaceModel

data class InterfaceModel2(val otherData: Int, override var text: String) : BaseInterfaceModel

data class InterfaceModel3(val otherText: String) : BaseInterfaceModel {
    override var text: String = otherText
}