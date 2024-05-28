package com.baltroid.core.network.util

import com.baltroid.apps.core.network.BuildConfig
import com.baltroid.core.common.PreferencesHelper
import com.baltroid.core.network.api.MekikAuthInterceptor
import com.baltroid.core.network.retrofit.ResultAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

internal fun retrofit(
    tokenProvider: PreferencesHelper,
    json: Json = defaultJson
): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.API_URL)
    .client(authorizedOkHttpClient(tokenProvider))
    .addConverterFactory(json.asConverterFactory(MIMETYPE_JSON))
    .addCallAdapterFactory(ResultAdapterFactory())
    .build()

private fun authorizedOkHttpClient(tokenProvider: PreferencesHelper): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(MekikAuthInterceptor(tokenProvider))
        .addInterceptor(loggingOkHttpClient())
        .build()

private fun loggingOkHttpClient(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level =
        if (BuildConfig.BUILD_TYPE.contains("debug"))
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    return logging
}