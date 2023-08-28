package com.hitreads.core.domain.model

data class FavoriteOriginalModel(
    val id: Int?,
    val title: String?,
    val description: String?,
    val authorId: Int?,
    val cover: String?,
    val banner: String?,
    val type: String?,
    val isLocked: Boolean?,
    val status: Boolean?,
    val isActual: Boolean?,
    val sort: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val subtitle: String?,
    val hashtag: String?,
    val isNew: Boolean?,
    val viewCount: Int?,
    val barcode: String?
)
