@file:Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")

package com.drake.brv.sample.component.net

import com.drake.net.NetConfig
import com.drake.net.convert.NetConverter
import com.drake.net.exception.ConvertException
import com.drake.net.exception.RequestParamsException
import com.drake.net.exception.ResponseException
import com.drake.net.exception.ServerResponseException
import com.drake.net.request.kType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.Response
import org.json.JSONObject
import java.lang.reflect.Type
import kotlin.reflect.KType

class SerializationConverter(
    val success: String = "0",
    val code: String = "errorCode",
    val message: String = "errorMsg",
) : NetConverter {

    companion object {
        val jsonDecoder = Json {
            ignoreUnknownKeys = true // 数据类可以不用声明Json的所有字段
            coerceInputValues = true // 如果Json字段是Null则使用数据类字段默认值
        }
    }

    override fun <R> onConvert(succeed: Type, response: Response): R? {
        try {
            return NetConverter.onConvert<R>(succeed, response)
        } catch (e: ConvertException) {
            val code = response.code
            when {
                code in 200..299 -> { // 请求成功
                    val bodyString = response.body?.string() ?: return null
                    val kType = response.request.kType
                        ?: throw ConvertException(response, "Request does not contain KType")
                    return try {
                        val json = JSONObject(bodyString) // 获取JSON中后端定义的错误码和错误信息
                        val srvCode = json.getString(this.code)
                        if (srvCode == success) { // 对比后端自定义错误码
                            json.getString("data").parseBody<R>(kType)
                        } else { // 错误码匹配失败, 开始写入错误异常
                            val errorMessage = json.optString(message, NetConfig.app.getString(com.drake.net.R.string.no_error_message))
                            throw ResponseException(response, errorMessage, tag = srvCode) // 将业务错误码作为tag传递
                        }
                    } catch (e: java.lang.Exception) { // 固定格式JSON分析失败直接解析JSON
                        bodyString.parseBody<R>(kType)
                    }
                }
                code in 400..499 -> throw RequestParamsException(response, code.toString()) // 请求参数错误
                code >= 500 -> throw ServerResponseException(response, code.toString()) // 服务器异常错误
                else -> throw ConvertException(response)
            }
        }
    }

    fun <R> String.parseBody(succeed: KType): R? {
        return jsonDecoder.decodeFromString(Json.serializersModule.serializer(succeed), this) as R
    }
}