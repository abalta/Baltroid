package com.baltroid.core.network.model

import com.baltroid.core.network.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HitReadsResponse<T>(
    @SerialName(Constants.Fields.STATUS)
    val status: Int,
    @SerialName(Constants.Fields.MESSAGE)
    val message: String,
    @SerialName(Constants.Fields.DATA)
    val data: T?
)