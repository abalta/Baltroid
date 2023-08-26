package com.baltroid.ui.screens.reading

import com.hitreads.core.model.ShowEpisode
import com.hitreads.core.model.ShowOriginal

data class ReadingUiState(
    val isLike: Boolean? = null,
    val original: ShowOriginal? = null,
    val episode: ShowEpisode? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)