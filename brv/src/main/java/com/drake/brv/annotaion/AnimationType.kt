/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Drake
 * Date：5/5/20 9:12 PM
 */

package com.drake.brv.annotaion

import androidx.annotation.IntDef
import com.drake.brv.annotaion.AnimationType.Companion.ALPHA
import com.drake.brv.annotaion.AnimationType.Companion.SCALE
import com.drake.brv.annotaion.AnimationType.Companion.SLIDE_BOTTOM
import com.drake.brv.annotaion.AnimationType.Companion.SLIDE_LEFT
import com.drake.brv.annotaion.AnimationType.Companion.SLIDE_RIGHT


@IntDef(ALPHA, SCALE, SLIDE_BOTTOM, SLIDE_LEFT, SLIDE_RIGHT)
@Retention(AnnotationRetention.SOURCE)
annotation class AnimationType {
    companion object {

        const val ALPHA = 0x00000001

        const val SCALE = 0x00000002

        const val SLIDE_BOTTOM = 0x00000003

        const val SLIDE_LEFT = 0x00000004

        const val SLIDE_RIGHT = 0x00000005
    }
}
