package com.baltroid.ui.screens.home.filter

import com.hitreads.core.model.IndexTag


data class FilterUiState(
    val indexTagUiModels: List<IndexTag> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)