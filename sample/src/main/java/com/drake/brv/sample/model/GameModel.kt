package com.drake.brv.sample.model

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.StrikethroughSpan
import com.drake.brv.sample.R
import com.drake.engine.base.app
import com.drake.engine.utils.dp
import com.drake.spannable.addSpan
import com.drake.spannable.setSpan
import com.drake.spannable.span.CenterImageSpan
import com.drake.spannable.span.ColorSpan
import com.drake.spannable.span.HighlightSpan
import com.drake.spannable.span.MarginSpan
import java.text.NumberFormat

@kotlinx.serialization.Serializable
data class GameModel(
    var total: Int = 0,
    var totalPage: Int = 0,
    var list: List<Data> = listOf()
) {

    @kotlinx.serialization.Serializable
    data class Data(
        var id: Int = 0,
        var img: String = "", // 游戏图片
        var name: String = "", // 游戏名称
        var label: List<String> = listOf(), // 标签集合
        var price: String = "", // 当前价格
        var initialPrice: String = "", // 初始价格
        var grade: Int = 0, // 评分
        var commend: Int = 0, // 1 推荐
        var discount: Double = 0.0, // 折扣百分比
        var endTime: Long = 0 // 折扣结束时间
    ) {

        /**
         * 当前价格
         */
        fun getPriceDesc(): CharSequence {
            return "¥${price} " addSpan "¥${initialPrice}".setSpan(
                listOf(
                    AbsoluteSizeSpan(10.dp),
                    HighlightSpan("#999999", Typeface.DEFAULT),
                    StrikethroughSpan()
                )
            )
        }

        /**
         * 标签富文本
         */
        fun getLabelDesc(): CharSequence {
            val result = SpannableStringBuilder()
            label.forEach {
                result addSpan it.setSpan(
                    listOf(
                        // 标签背景
                        CenterImageSpan(app, R.drawable.bg_game_label)
                            .setDrawableSize(-1)
                            .setPaddingHorizontal(6.dp)
                            .setTextVisibility(),
                        // 标签颜色
                        when (it) {
                            "史低" -> ColorSpan("#469690")
                            else -> ColorSpan("#eb4d50")
                        }
                    )
                ).addSpan(" ", MarginSpan(4.dp))
            }
            return result
        }

        /**
         * 折扣百分比
         */
        fun getDiscountDesc(): String {
            return NumberFormat.getPercentInstance().format(discount) + "折扣"
        }

        /**
         * 剩余时间
         */
        fun getRemainDate(): String {
            val time = (System.currentTimeMillis() - endTime) / 1000
            return if (time > 24 * 60 * 60) {
                "剩余${time / (24 * 60 * 60)}天"
            } else {
                "剩余${time / (60 * 60)}小时"
            }
        }

    }
}