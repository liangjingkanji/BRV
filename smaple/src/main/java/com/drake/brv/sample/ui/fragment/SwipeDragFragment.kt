/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：drake
 * Date：7/15/20 3:20 AM
 */

package com.drake.brv.sample.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.drake.brv.annotaion.DragType
import com.drake.brv.annotaion.SwipeType
import com.drake.brv.sample.R
import com.drake.brv.sample.model.SwipeDragModel
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.android.synthetic.main.fragment_swipe_drag.*


class SwipeDragFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_swipe_drag, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /**
         * 拖拽会改变数据顺序
         *
         * 侧滑删除会改变数据的内容
         */

        rv.linear().setup {
            touchEnable = true // 支持启用和关闭

            addType<SwipeDragModel>(R.layout.item_swipe_or_drag)
        }.models = getData()
    }

    private fun getData(): List<SwipeDragModel> {
        return listOf(
            SwipeDragModel(SwipeType.RIGHT, DragType.ALL),
            SwipeDragModel(SwipeType.RIGHT, DragType.ALL),
            SwipeDragModel(SwipeType.NONE, DragType.NONE), // 不支持拖拽 | 不支持侧滑
            SwipeDragModel(SwipeType.RIGHT, DragType.NONE), // 不支持拖拽
            SwipeDragModel(SwipeType.RIGHT, DragType.ALL),
            SwipeDragModel(SwipeType.NONE, DragType.ALL), // 不支持侧滑
            SwipeDragModel(SwipeType.RIGHT, DragType.ALL),
            SwipeDragModel(SwipeType.RIGHT, DragType.ALL)
        )
    }

}
