package com.drake.brv.sample.model

import androidx.databinding.BaseObservable

data class SwipeSelectModel(
    var checked: Boolean = false,
    var showCheckbox: Boolean = false
) : BaseObservable()
