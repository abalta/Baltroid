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
    val episodes: List<ShowEpisodeModel>,
    val author: AuthorModel,
    //val comments: List<String>, //todo unknown comment model
)
