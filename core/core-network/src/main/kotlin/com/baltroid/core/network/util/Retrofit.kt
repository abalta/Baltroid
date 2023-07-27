package com.baltroid.core.network.util

import android.content.Context
import com.baltroid.core.datastore.PreferencesDataStoreDataSource
import com.baltroid.core.network.api.BaltroidAuthInterceptor
import com.baltroid.core.network.retrofit.ResultAdapterFactory
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

internal fun retrofit(
    preferencesDataStoreDataSource: PreferencesDataStoreDataSource,
    json: Json = defaultJson,
    context: Context,
): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.API_URL)
    .client(loggingOkHttpClient(preferencesDataStoreDataSource, context))
    .addConverterFactory(json.asConverterFactory(MIMETYPE_JSON))
    .addCallAdapterFactory(ResultAdapterFactory())
    .build()

private fun loggingOkHttpClient(
    dataStore: PreferencesDataStoreDataSource,
    context: Context
): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(ChuckerInterceptor(context))
        .addInterceptor(BaltroidAuthInterceptor(dataStore))
        .build()
}

