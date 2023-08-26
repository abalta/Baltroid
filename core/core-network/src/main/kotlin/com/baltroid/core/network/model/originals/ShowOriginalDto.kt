package com.baltroid.core.network.model.originals

import com.baltroid.core.network.model.author.IndexNetworkAuthor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowOriginalDto(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("cover")
    val cover: String,
    @SerialName("description")
    val description: String,
    @SerialName("is_locked")
    val isLocked: Boolean,
    @SerialName("view_count")
    val viewCount: Int,
    @SerialName("comments_count")
    val commentsCount: Int,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("episodes")
    val episodes: List<IndexNetworkContinueReadingEpisode>,
    @SerialName("author")
    val author: IndexNetworkAuthor,
    @SerialName("comments")
    val comments: List<String?> = emptyList() //todo comment model
)
