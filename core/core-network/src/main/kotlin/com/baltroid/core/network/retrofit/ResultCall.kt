package com.baltroid.core.network.retrofit

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.HttpException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

internal class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, BaltroidResult<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<BaltroidResult<T>>) =
        proxy.enqueue(ResultCallback(this, callback))

    override fun cloneImpl(): ResultCall<T> = ResultCall(proxy.clone())

    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<BaltroidResult<T>>
    ) : Callback<T> {

        @Suppress("UNCHECKED_CAST")
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result = if (response.isSuccessful) {
                BaltroidResult.success(
                    value = response.body() as T,
                    statusCode = response.code(),
                    statusMessage = response.message(),
                    url = call.request().url.toString()
                )
            } else {
                BaltroidResult.failure(
                    HttpException(
                        statusCode = response.code(),
                        statusMessage = response.message(),
                        url = call.request().url.toString()
                    )
                )
            }
            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val result = when (error) {
                is retrofit2.HttpException -> BaltroidResult.failure<T>(
                    HttpException(
                        statusCode = error.code(),
                        statusMessage = error.message(),
                        cause = error
                    )
                )

                is IOException -> BaltroidResult.failure(error)
                else -> BaltroidResult.failure(error)
            }

            callback.onResponse(proxy, Response.success(result))
        }
    }

    override fun timeout(): Timeout = proxy.timeout()
}
