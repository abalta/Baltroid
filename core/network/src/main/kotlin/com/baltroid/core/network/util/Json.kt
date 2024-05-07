package com.baltroid.core.network.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
internal val defaultJson = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}
