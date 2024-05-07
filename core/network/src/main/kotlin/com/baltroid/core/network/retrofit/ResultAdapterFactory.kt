package com.baltroid.core.network.retrofit

import com.baltroid.core.common.BaltroidResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ResultAdapterFactory : CallAdapter.Factory() {
    @Suppress("ReturnCount")
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawReturnType: Class<*> = getRawType(returnType)
        if (rawReturnType == Call::class.java && returnType is ParameterizedType) {
            val callInnerType: Type = getParameterUpperBound(0, returnType)
            if (getRawType(callInnerType) == BaltroidResult::class.java) {
                if (callInnerType is ParameterizedType) {
                    val resultInnerType = getParameterUpperBound(0, callInnerType)
                    return ResultCallAdapter<Any?>(resultInnerType)
                }
                return ResultCallAdapter<Nothing>(Nothing::class.java)
            }
        }

        return null
    }
}

private class ResultCallAdapter<T>(private val type: Type) :
    CallAdapter<T, Call<BaltroidResult<T>>> {
    override fun responseType(): Type = type
    override fun adapt(call: Call<T>): Call<BaltroidResult<T>> = ResultCall(call)
}