/*
 * MIT License
 *
 * Copyright (c) 2023 劉強東 https://github.com/liangjingkanji
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.drake.brv.listener

import android.os.SystemClock
import android.view.View
import com.drake.brv.utils.BRV


internal fun View.setOnDebounceClickListener(interval: Long = 500, block: View.() -> Unit) {
    setOnClickListener(OnDebounceClickListener(interval, block))
}

private class OnDebounceClickListener(
    private val interval: Long = 500,
    private var block: View.() -> Unit
) : View.OnClickListener {

    private var _lastDebounceClickTime: Long = 0
    private var lastDebounceClickTime: Long
        get() = if (BRV.debounceGlobalEnabled) BRV.lastDebounceClickTime else _lastDebounceClickTime
        set(value) = if (BRV.debounceGlobalEnabled) BRV.lastDebounceClickTime = value else _lastDebounceClickTime = value

    override fun onClick(v: View) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastDebounceClickTime > interval) {
            lastDebounceClickTime = currentTime
            block(v)
        }
    }
}