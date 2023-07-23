package com.baltroid.core.network.model.originals

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCommentOriginal(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("author_id")
    val authorId: Int,
    @SerialName("cover")
    val cover: String,
    @SerialName("banner")
    val banner: String,
    @SerialName("type")
    val type: Int,
    @SerialName("is_locked")
    val isLocked: Boolean,
    @SerialName("status")
    val status: Boolean,
    @SerialName("is_actual")
    val isActual: Boolean,
    @SerialName("sort")
    val sort: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("subtitle")
    val subtitle: String,
    @SerialName("hashtag")
    val hashtag: String?,
    @SerialName("is_new")
    val isNew: Boolean,
    @SerialName("view_count")
    val viewCount: Int,
)
