package com.baltroid.core.network.util

import com.baltroid.core.network.retrofit.ResultAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit

internal fun retrofit(
    json: Json = defaultJson
): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.API_URL)
    .addConverterFactory(json.asConverterFactory(MIMETYPE_JSON))
    .addCallAdapterFactory(ResultAdapterFactory())
    .build()
