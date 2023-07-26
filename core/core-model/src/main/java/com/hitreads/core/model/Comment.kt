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
    var replies: List<Comment>,
    val episode: String,
    val original: Original?,
    var isExpanded: Boolean = false,
)
