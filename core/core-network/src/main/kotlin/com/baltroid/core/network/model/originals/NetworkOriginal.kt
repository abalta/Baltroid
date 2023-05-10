package com.baltroid.core.network.model.originals

import com.baltroid.core.network.model.author.NetworkAuthor
import com.baltroid.core.network.model.user.NetworkUserData
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


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
    @SerialName("package")
    val `package`: String?,
    @SerialName("sort")
    val sort: Int,
    @SerialName("status")
    val status: Boolean,
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: String,
    @SerialName("user_data")
    val userData: NetworkUserData
)

