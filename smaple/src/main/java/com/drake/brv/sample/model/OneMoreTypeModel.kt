/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：9/11/19 6:49 PM
 */

package com.drake.brv.sample.model

import com.drake.brv.item.ItemPosition

data class OneMoreTypeModel(var type: Int, override var itemPosition: Int = 0) : ItemPosition