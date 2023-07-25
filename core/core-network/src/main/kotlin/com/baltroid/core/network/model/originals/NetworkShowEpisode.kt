package com.baltroid.core.network.model.originals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkShowEpisode(
    @SerialName("id")
    val id: Int?,
    @SerialName("original_id")
    val originalId: Int?,
    @SerialName("season_id")
    val seasonId: Int?,
    @SerialName("episode_name")
    val episodeName: String?,
    @SerialName("asset_contents")
    val assetContent: String?,
    @SerialName("price")
    val price: Int?,
    @SerialName("price_type")
    val priceType: String?,
    @SerialName("sort")
    val sort: Int?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?
)
