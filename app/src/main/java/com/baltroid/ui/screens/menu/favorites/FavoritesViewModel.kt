package com.baltroid.ui.screens.menu.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.usecase.GetFavoritesUseCase
import com.hitreads.core.ui.mapper.asFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _uiStateFavorites = MutableStateFlow(FavoritesUIState())
    val uiStateFavorites = _uiStateFavorites.asStateFlow()

    fun getFavoriteAuthors() = viewModelScope.launch {
        getFavoritesUseCase("author", null).handle {
            onSuccess { authors ->
                _uiStateFavorites.update { it.copy(authors = authors.map { it.asFavorite() }) }
            }
        }
    }

    fun getFavoriteEpisodes() = viewModelScope.launch {
        getFavoritesUseCase("episode", null).handle {
            onSuccess { episodes ->
                _uiStateFavorites.update { it.copy(episodes = episodes.map { it.asFavorite() }) }
            }
        }
    }
}