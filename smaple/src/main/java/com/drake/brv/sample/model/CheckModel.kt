/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：9/5/19 6:36 PM
 */

package com.drake.brv.sample.model

import androidx.databinding.BaseObservable
import com.drake.brv.model.ItemModel

data class CheckModel(var checked: Boolean = false, var visibility: Boolean = false) :
    BaseObservable(), ItemModel {

}