package com.baltroid.ui

import com.hitreads.core.model.Episode
import com.hitreads.core.model.ShowOriginal

data class ReadingUiState(
    val isLike: Boolean? = null,
    val original: ShowOriginal? = null,
    val episode: Episode? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)