package com.baltroid.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("status")
    val status: String?,
    @SerialName("duration")
    val duration: Int?,
    @SerialName("video_url")
    val url: String?
)