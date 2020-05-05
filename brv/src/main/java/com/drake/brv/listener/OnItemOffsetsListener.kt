/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：5/5/20 9:12 PM
 */

package com.drake.brv.listener

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface OnItemOffsetsListener {

    fun onItemOffset(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State): Boolean
}
