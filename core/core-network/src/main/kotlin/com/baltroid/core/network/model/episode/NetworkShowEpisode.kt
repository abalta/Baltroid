package com.baltroid.core.network.model.episode

import com.baltroid.core.network.model.originals.IndexNetworkOriginal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkShowEpisode(
    @SerialName("id")
    val id: Int?,
    @SerialName("episode_name")
    val episodeName: String?,
    @SerialName("price")
    val price: Int?,
    @SerialName("is_readable")
    val isReadable: Boolean?,
    @SerialName("episode_sort")
    val episodeSort: Int?,
    @SerialName("price_type")
    val priceType: String?,
    @SerialName("sort")
    val sort: Int?,
    @SerialName("asset_contents")
    val assetContents: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("original_id")
    val originalId: Int?,
    @SerialName("season_id")
    val seasonId: Int?,
    @SerialName("is_locked")
    val isLocked: Boolean?,
    @SerialName("next_episode")
    val nextEpisodeId: Int?,
    @SerialName("is_last_episode")
    val isLastEpisode: Boolean?,
    @SerialName("original")
    val original: IndexNetworkOriginal?,
    @SerialName("bundle_assets")
    val bundleAssets: List<InteractiveNetworkBundleAsset>?
)