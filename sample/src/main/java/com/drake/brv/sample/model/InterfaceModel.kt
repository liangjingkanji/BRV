package com.drake.brv.sample.model

/**
 *
 * Project Name : BRV
 * Package Name : com.drake.brv.sample.model
 * Create Time  : 2021-10-08 21:03
 * Create By    : @author xIao
 * Version      : 1.0.0
 *
 **/

interface BaseInterfaceModel {
    var text: String
}

data class InterfaceModel1(override var text: String) : BaseInterfaceModel

data class InterfaceModel2(val otherData: Int, override var text: String) : BaseInterfaceModel

data class InterfaceModel3(val otherText: String) : BaseInterfaceModel {
    override var text: String = otherText
}