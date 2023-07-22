package com.hitreads.core.domain.model

data class BookmarkModel(
    val id: Int,
    val user: String,
    val episode: EpisodeModel?,
    val original: OriginalModel?,
    val content: String,
    val cover: String
)
