/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：9/11/19 6:49 PM
 */

package com.drake.brv.sample.mod

import com.drake.brv.model.Item

class StickyHeaderModel : Item {

    override fun isSticky(): Boolean {
        return true
    }
}