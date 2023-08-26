package com.hitreads.core.domain.model

data class CommentModel(
    val activeUserLike: Boolean,
    val author: IndexAuthorModel,
    val content: String,
    val createdAt: String,
    val id: Int,
    val isReply: Boolean,
    val likesCount: Int,
    val repliesCount: Int,
    val replyCommentId: Int,
    val replies: List<AllCommentsModel>,
    val original: IndexOriginalModel?
)
