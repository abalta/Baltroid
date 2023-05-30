package com.baltroid.core.network.api

import com.baltroid.core.datastore.PreferencesDataStoreDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class BaltroidAuthInterceptor(private val preferencesDataStoreDataSource: PreferencesDataStoreDataSource) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        val token = runBlocking {
            preferencesDataStoreDataSource.getToken().first()
        }
        if (token.isNotEmpty()) {
            requestBuilder.addHeader(AUTHORIZATION, "Bearer $token")
        }
        val request = requestBuilder.url(originalRequest.url).build()
        return chain.proceed(request)
    }

    private companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}
