package com.baltroid.ui.screens.reading

import com.hitreads.core.model.Episode
import com.hitreads.core.model.Original

data class ReadingUiState(
    val isLike: Boolean? = null,
    val original: Original? = null,
    val episode: Episode? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)