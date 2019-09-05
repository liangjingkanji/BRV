/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：BRV
 * Author：Nathan
 * Date：8/24/19 2:35 AM
 */

package com.drake.brv.animation

import android.animation.ObjectAnimator
import android.view.View


class SlideRightItemAnimation : BaseItemAnimation {

    override fun onItemEnterAnimation(view: View) {
        ObjectAnimator.ofFloat(view, "translationX", view.rootView.width.toFloat(), 0F)
            .setDuration(300)
            .start()
    }
}
