package com.hitreads.core.model

data class Comment(
    val id: Int,
    val imgUrl: String,
    val content: String,
    val repliesCount: Int,
    val authorName: String,
    val hashtag: String,
    val createdAt: String,
    val isLiked: Boolean,
    val isReply: Boolean,
    val replies: List<Comment>,
    val episode: String,
    val original: Original?,
    val isExpanded: Boolean = false,
)
