package com.baltroid.ui.screens.menu.comments

import com.hitreads.core.domain.model.AllCommentsModel

data class CommentsUiState(
    val commentList: List<AllCommentsModel> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)

data class CommentsLikeUiState(
    val isLike: Boolean? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)

