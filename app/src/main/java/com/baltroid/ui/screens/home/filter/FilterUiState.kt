package com.baltroid.ui.screens.home.filter

import com.hitreads.core.model.Tag


data class FilterUiState(
    val tagUiModels: List<Tag> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)