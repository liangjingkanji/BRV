/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.sample.model

import com.drake.brv.item.ItemPosition

data class StaggeredModel(var width: Int = 400,
                          var height: Int = 600,
                          override var itemPosition: Int = 0) : ItemPosition