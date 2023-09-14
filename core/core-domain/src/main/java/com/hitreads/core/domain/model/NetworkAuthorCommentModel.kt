package com.hitreads.core.domain.model

data class NetworkAuthorCommentModel(
    val id: Int?,
    val content: String?,
    val likesCount: Int?,
    val repliesCount: Int?,
    val activeUserLike: Boolean?,
    val isReply: Boolean?,
    val replyCommentId: Int?,
    val createdAt: String?,
    val author: AuthorModel?
)