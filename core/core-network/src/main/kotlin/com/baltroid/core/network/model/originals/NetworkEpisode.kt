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
    @SerialName("sort")
    val sort: Int?,
    @SerialName("asset_contents")
    val assetContent: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("favorites_count")
    val favoritesCount: Int?,
    @SerialName("original_id")
    val originalId: Int?,
    @SerialName("likes_count")
    val likesCount: Int?,
    @SerialName("season_id")
    val seasonId: Int?,
    @SerialName("is_liked")
    val isLiked: Boolean?
)