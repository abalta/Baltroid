package com.baltroid.ui.screens.reading

data class ReadingUiState(
    val isLike: Unit? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)