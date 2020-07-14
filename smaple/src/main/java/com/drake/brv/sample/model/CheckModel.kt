/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.sample.model

import androidx.databinding.BaseObservable

data class CheckModel(var checked: Boolean = false, var visibility: Boolean = false) : BaseObservable()