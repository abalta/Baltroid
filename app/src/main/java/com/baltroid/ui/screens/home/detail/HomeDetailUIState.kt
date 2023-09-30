package com.baltroid.ui.screens.home.detail

import com.hitreads.core.model.IndexOriginal

data class HomeDetailUIState(
    val isLoading: Boolean = false,
    val original: IndexOriginal = IndexOriginal(),
    val originalPurchased: Boolean? = null
)