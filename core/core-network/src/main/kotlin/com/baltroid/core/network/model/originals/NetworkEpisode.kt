package com.baltroid.core.network.model.originals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEpisode(
    @SerialName("id")
    val id: Int,
    @SerialName("episode_name")
    val episodeName: String,
    @SerialName("price")
    val price: Int,
    @SerialName("price_type")
    val priceType: String,
    @SerialName("user_purchase")
    val userPurchase: String?,
    @SerialName("asset_contents")
    val assetContent: String?
)