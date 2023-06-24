package com.baltroid.ui.screens.menu.comments

import com.hitreads.core.model.Comment

data class CommentsUiState(
    val commentList: List<Comment> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)