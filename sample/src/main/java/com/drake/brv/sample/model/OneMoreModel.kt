package com.drake.brv.sample.model

import com.drake.brv.item.ItemPosition

data class OneMoreModel(var type: Int, override var itemPosition: Int = 0) : ItemPosition