package com.hitreads.core.domain.model

data class ShowOriginalModel(
    val id: Int,
    val title: String,
    val cover: String,
    val description: String,
    val isLocked: Boolean,
    val viewCount: Int,
    val commentsCount: Int,
    val updatedAt: String,
    val episodes: List<IndexContinueReadingEpisodeModel>,
    val author: IndexAuthorModel,
    val comments: List<String?>
)
