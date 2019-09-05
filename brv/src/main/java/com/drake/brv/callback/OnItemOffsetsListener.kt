/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv.callback

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface OnItemOffsetsListener {

    fun onItemOffset(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State): Boolean
}
