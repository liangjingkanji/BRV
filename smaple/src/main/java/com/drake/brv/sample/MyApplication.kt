/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv.sample

import android.app.Application
import com.drake.brv.BindingAdapter

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        BindingAdapter.modelId = BR.m
    }
}