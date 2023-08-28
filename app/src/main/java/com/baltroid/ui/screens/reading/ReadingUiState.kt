package com.baltroid.ui.screens.reading

import com.hitreads.core.model.ShowEpisode

data class ReadingUiState(
    val episode: ShowEpisode? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)