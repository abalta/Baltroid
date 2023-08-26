package com.baltroid.core.network.model.originals

import com.baltroid.core.network.model.author.IndexNetworkAuthor
import com.baltroid.core.network.model.user.IndexUserData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IndexNetworkOriginal(
    @SerialName("author")
    val author: IndexNetworkAuthor?,
    @SerialName("banner")
    val banner: String?,
    @SerialName("cover")
    val cover: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("is_actual")
    val isActual: Boolean?,
    @SerialName("is_locked")
    val isLocked: Boolean?,
    @SerialName("like_count")
    val likeCount: Int?,
    @SerialName("comment_count")
    val commentCount: Int?,
    @SerialName("view_count")
    val viewCount: Int?,
    @SerialName("package")
    val `package`: IndexNetworkPackage?,
    @SerialName("sort")
    val sort: Int?,
    @SerialName("status")
    val status: Boolean?,
    @SerialName("title")
    val title: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("user_data")
    val userData: IndexUserData?,
    @SerialName("subtitle")
    var subtitle: String?,
    @SerialName("tags")
    val tags: List<IndexNetworkTag>?,
    @SerialName("episode_count")
    val episodeCount: Int?,
    @SerialName("hashtag")
    val hashtag: String?,
    @SerialName("is_new")
    val isNew: Boolean?,
    @SerialName("barcode")
    val barcode: String?,
    @SerialName("continue_reading_episode")
    val continueReadingEpisode: IndexNetworkContinueReadingEpisode?
)

