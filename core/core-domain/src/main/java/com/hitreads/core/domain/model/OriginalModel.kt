package com.hitreads.core.domain.model

data class OriginalModel(
    val author: AuthorModel,
    val banner: String,
    val cover: String,
    val description: String,
    val id: Int,
    val isActual: Boolean,
    val isLocked: Boolean,
    val likeCount: Int,
    val `package`: String?,
    val sort: Int,
    val status: Boolean,
    val title: String,
    val type: String,
    val userData: UserDataModel
)
