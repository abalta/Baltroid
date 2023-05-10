package com.baltroid.presentation.screens.home

import androidx.paging.PagingData
import com.hitreads.core.model.Original
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUiState(
    val originals: Flow<PagingData<Original>> = emptyFlow(),
    val loadStates: Map<Int, Boolean> = emptyMap(),
    val error: Throwable? = null,
)

internal val HomeUiState.isLoading: Boolean get() = loadStates.values.any { it }
