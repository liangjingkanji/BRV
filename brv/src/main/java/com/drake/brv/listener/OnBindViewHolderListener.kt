package com.drake.brv.listener

import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.BindingAdapter

interface OnBindViewHolderListener {
    fun onBindViewHolder(rv: RecyclerView, adapter: BindingAdapter, holder: BindingAdapter.BindingViewHolder, position: Int)
}