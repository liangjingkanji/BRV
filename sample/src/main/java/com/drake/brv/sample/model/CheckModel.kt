package com.drake.brv.sample.model

import androidx.databinding.BaseObservable

data class CheckModel(var checked: Boolean = false, var visibility: Boolean = false) : BaseObservable()