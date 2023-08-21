package com.baltroid.ui.screens.home

import com.hitreads.core.model.Original
import com.hitreads.core.model.TagsWithOriginals

data class HomeUiState(
    val originals: List<TagsWithOriginals> = emptyList(),
    val favorites: List<Original> = emptyList(),
    val loadStates: Map<Int, Boolean> = emptyMap(),
    val error: Throwable? = null,
)

internal val HomeUiState.isLoading: Boolean get() = loadStates.values.any { it }
