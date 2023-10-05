package com.baltroid.ui.screens.reading

import com.hitreads.core.model.Comment
import com.hitreads.core.model.ShowEpisode

data class ReadingUiState(
    val episode: ShowEpisode? = null,
    val allComments: List<Comment> = emptyList(),
    val commentsByMe: List<Comment> = emptyList(),
    val commentsLikedByMe: List<Comment> = emptyList(),
    val optionPurchased: Boolean? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)