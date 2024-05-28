package com.baltroid.core.network.api

import com.baltroid.core.common.PreferencesHelper
import com.baltroid.core.network.util.Constants
import com.baltroid.core.network.util.Constants.ACCEPT
import com.baltroid.core.network.util.Constants.ACCEPT_LANG
import com.baltroid.core.network.util.Constants.AUTHORIZATION
import com.baltroid.core.network.util.Constants.BEARER
import okhttp3.Interceptor
import okhttp3.Response

internal class MekikAuthInterceptor(private val preferencesHelper: PreferencesHelper) :
    Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            val requestBuilder = request.newBuilder()
            requestBuilder.addHeader(ACCEPT, "application/json")
            requestBuilder.addHeader(ACCEPT_LANG, "tr")
            preferencesHelper.userAccessToken?.let {
                if (it.isNotEmpty()) {
                    requestBuilder.addHeader(AUTHORIZATION, "$BEARER $it")
                }
            }

            val newRequest = requestBuilder.url(request.url).build()
            return chain.proceed(newRequest)
        }
}