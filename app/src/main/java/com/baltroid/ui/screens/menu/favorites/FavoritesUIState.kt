package com.baltroid.ui.screens.menu.favorites

import com.hitreads.core.model.Favorite
import com.hitreads.core.model.FavoriteOriginal

data class FavoritesUIState(
    val originals: List<FavoriteOriginal> = emptyList(),
    val authors: List<Favorite> = emptyList(),
    val isLoading: Boolean = false
)
