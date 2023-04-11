@file:OptIn(ExperimentalStdlibApi::class)

package com.drake.brv.reflect

import java.lang.reflect.ParameterizedType
import kotlin.reflect.KType
import kotlin.reflect.javaType
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
internal fun KType.isInstance(other: Any): Boolean {
    val thisClass = if (javaType is ParameterizedType) {
        (javaType as ParameterizedType).rawType as Class<*>
    } else {
        javaType as Class<*>
    }
    return if (other is TypeList<*>) {
        thisClass.isAssignableFrom(other.javaClass) && this.arguments == other.type.arguments
    } else {
        thisClass.isInstance(other)
    }
}

/**
 * 判断类型是否相同或为其子类
 * 如果[other]为[TypeList]则会判断其嵌套泛型是否也相同
 */
internal fun KType.isAssignableFrom(other: Any): Boolean {
    val thisClass = if (javaType is ParameterizedType) {
        (javaType as ParameterizedType).rawType as Class<*>
    } else {
        javaType as Class<*>
    }
    return if (other is TypeList<*>) {
        thisClass.isAssignableFrom(other.javaClass) && this.arguments == other.type.arguments
    } else {
        thisClass.isAssignableFrom(other.javaClass)
    }
}
