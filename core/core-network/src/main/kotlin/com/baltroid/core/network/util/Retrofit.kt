package com.baltroid.core.network.util

import com.baltroid.core.network.retrofit.ResultAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

internal fun retrofit(
    json: Json = defaultJson
): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.API_URL)
    .client(loggingOkHttpClient())
    .addConverterFactory(json.asConverterFactory(MIMETYPE_JSON))
    .addCallAdapterFactory(ResultAdapterFactory())
    .build()

private fun loggingOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
}

