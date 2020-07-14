/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.utils

object BRV {
    /*
    即item的layout布局中的<variable>标签内定义变量名称

    示例:
    <variable
        name="m"
        type="com.drake.brv.sample.mod.CheckModel" />

    则应在Application中的[onCreate]函数内设置:
    BindingAdapter.modelId = BR.m
    */
    var modelId: Int = -1
}