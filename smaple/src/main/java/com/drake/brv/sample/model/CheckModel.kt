/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：9/11/19 6:49 PM
 */

package com.drake.brv.sample.model

import androidx.databinding.BaseObservable

data class CheckModel(var checked: Boolean = false, var visibility: Boolean = false) : BaseObservable()