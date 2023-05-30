package com.baltroid.core.network.model.originals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkSeason(
    @SerialName("id")
    val id: Int,
    @SerialName("season_name")
    val seasonName: String,
    @SerialName("episodes")
    val episodes: List<NetworkEpisode>
)