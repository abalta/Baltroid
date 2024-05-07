package com.baltroid.core.network.api

import com.baltroid.core.network.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

internal class MekikAuthInterceptor(private val tokenProvider: MekikTokenProvider) :
    Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            val requestBuilder = request.newBuilder()
            requestBuilder.addHeader(Constants.AUTHORIZATION, "${Constants.BEARER} ${tokenProvider.token}")

            val newRequest = request.newBuilder().url(request.url).build()
            return chain.proceed(newRequest)
        }
}