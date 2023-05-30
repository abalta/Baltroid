package com.baltroid.ui.screens.reading

import com.hitreads.core.model.Original

data class ReadingUiState(
    val isLike: Unit? = null,
    val original: Original? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)