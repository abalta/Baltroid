package com.baltroid.core.network.model.originals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPackage(
    @SerialName("id")
    val id: Int?,
    @SerialName("price")
    val price: Int?,
    @SerialName("price_type")
    val priceType: String?
)
