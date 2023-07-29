package com.baltroid.ui.screens.menu.favorites

import com.hitreads.core.model.Favorite

data class FavoritesUIState(
    val episodes: List<Favorite> = emptyList(),
    val authors: List<Favorite> = emptyList()
)
