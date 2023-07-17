package com.baltroid.core.network.model.originals

import com.baltroid.core.network.model.author.NetworkAuthor
import com.baltroid.core.network.model.user.NetworkUserData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkOriginal(
    @SerialName("author")
    val author: NetworkAuthor,
    @SerialName("banner")
    val banner: String,
    @SerialName("cover")
    val cover: String,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: Int,
    @SerialName("is_actual")
    val isActual: Boolean,
    @SerialName("is_locked")
    val isLocked: Boolean,
    @SerialName("like_count")
    val likeCount: Int,
    @SerialName("comment_count")
    val commentCount: Int,
    @SerialName("view_count")
    val viewCount: Int,
    @SerialName("package")
    val `package`: NetworkPackage?,
    @SerialName("sort")
    val sort: Int,
    @SerialName("status")
    val status: Boolean,
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: String,
    @SerialName("user_data")
    val userData: NetworkUserData,
    @SerialName("subtitle")
    var subtitle: String?,
    @SerialName("tags")
    val tags: List<NetworkTag>,
    @SerialName("episode_count")
    val episodeCount: Int,
    @SerialName("hashtag")
    val hashtag: String,
    @SerialName("seasons")
    val seasons: List<NetworkSeason>?,
    @SerialName("is_new")
    val isNew: Boolean,
    var dataCount: Int = 0
)

