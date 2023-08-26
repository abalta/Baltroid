package com.hitreads.core.model

data class ShowOriginal(
    val id: Int,
    val title: String,
    val cover: String,
    val description: String,
    val isLocked: Boolean,
    val viewCount: Int,
    val commentsCount: Int,
    val updatedAt: String,
    val episodes: List<ShowEpisode>,
    val indexAuthor: IndexAuthor,
    //val comments: List<String>, //todo unknown comment model
)
