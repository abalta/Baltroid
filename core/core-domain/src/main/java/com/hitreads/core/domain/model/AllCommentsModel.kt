package com.hitreads.core.domain.model

data class AllCommentsModel(
    val id: Int?,
    val content: String?,
    val original: OriginalModel?,
    val author: AuthorModel?,
    val isReply: Boolean?,
    val likesCount: Int?,
    val repliesCount: Int?,
    val activeUserLike: Boolean?,
    val replies: List<AllCommentsModel>?,
    val replyCommentId: Int?,
    val createdAt: String?,
)
