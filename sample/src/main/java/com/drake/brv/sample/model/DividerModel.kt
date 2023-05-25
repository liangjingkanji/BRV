package com.drake.brv.sample.model

import com.drake.brv.item.ItemPosition

data class DividerModel(var height: Int = 600, override var itemPosition: Int = 0) : ItemPosition