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

package com.drake.brv.reflect

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf


/**
 * 将集合所有元素复制至[TypeList], 可以让集合保留泛型信息, 避免无法判断元素类型
 */
inline fun <reified T> List<T>.copyType(): TypeList<T> {
    return TypeList(typeOf<TypeList<T>>(), this)
}

/**
 * 存储类型信息的ArrayList
 */
class TypeList<T> : ArrayList<T> {

    /** 泛型信息请包含[TypeList]本身, 例如`TypeList(typeOf<TypeList<String>>())` */
    val type: KType

    constructor(type: KType) : super() {
        this.type = type
    }

    constructor(type: KType, c: Collection<T>) : super(c) {
        this.type = type
    }
}

/**
 * 判断类型是否相同
 * 如果[other]为[TypeList]则会判断其嵌套泛型是否也相同
 */
internal fun KType.equalInstance(other: Any): Boolean {
    val javaObjectType = (classifier as KClass<*>).javaObjectType
    return if (other is TypeList<*>) {
        javaObjectType == other::class.java && this.arguments == other.type.arguments
    } else {
        javaObjectType == other::class.java
    }
}

/**
 * 判断类型是否相同或为其子类
 * 如果[other]为[TypeList]则会判断其嵌套泛型是否也相同
 */
internal fun KType.isInstance(other: Any): Boolean {
    val javaObjectType = (classifier as KClass<*>).javaObjectType
    return if (other is TypeList<*>) {
        javaObjectType.isInstance(other) && this.arguments == other.type.arguments
    } else {
        javaObjectType.isInstance(other)
    }
}
