package com.hitreads.core.domain.model

data class BookmarkModel(
    val id: Int,
    val user: String,
    val episode: String,
    val original: OriginalModel?,
    val content: String,
    val cover: String
)
