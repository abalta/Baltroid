package com.baltroid.ui

import com.hitreads.core.model.Comment

data class CommentsUiState(
    val commentList: List<Comment> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)

data class CommentsLikeUiState(
    val isLike: Boolean? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)

