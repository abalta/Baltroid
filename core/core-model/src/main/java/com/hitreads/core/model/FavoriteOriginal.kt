package com.hitreads.core.model

data class FavoriteOriginal(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val authorId: Int = -1,
    val cover: String = "",
    val banner: String = "",
    val type: String = "",
    val isLocked: Boolean = false,
    val status: Boolean = false,
    val isActual: Boolean = false,
    val sort: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = "",
    val subtitle: String = "",
    val hashtag: String = "",
    val isNew: Boolean = false,
    val viewCount: Int = 0,
    val barcode: String = ""
)
