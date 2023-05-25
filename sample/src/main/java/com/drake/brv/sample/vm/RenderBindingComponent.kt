package com.drake.brv.sample.vm

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

/**
 * 此处只是演示创建DataBinding属性, 使用object而非包级函数可以避免命令空间混乱
 *
 * 实际项目推荐参考以下文件
 * https://github.com/liangjingkanji/Engine/blob/master/engine/src/main/java/com/drake/engine/databinding/DataBindingComponent.kt
 */
object RenderBindingComponent {

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageRes(image: ImageView, @DrawableRes drawable: Int) {
        image.setImageResource(drawable)
    }
}