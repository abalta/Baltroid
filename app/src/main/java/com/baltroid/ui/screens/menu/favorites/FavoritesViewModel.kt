package com.baltroid.ui.screens.menu.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.baltroid.util.AUTHOR
import com.hitreads.core.domain.usecase.GetFavoriteOriginalsUseCase
import com.hitreads.core.domain.usecase.GetFavoritesUseCase
import com.hitreads.core.ui.mapper.asFavorite
import com.hitreads.core.ui.mapper.asFavoriteOriginal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getFavoritesOriginalsUseCase: GetFavoriteOriginalsUseCase
) : ViewModel() {

    private val _uiStateFavorites = MutableStateFlow(FavoritesUIState())
    val uiStateFavorites = _uiStateFavorites.asStateFlow()

    fun getFavoriteAuthors() = viewModelScope.launch {
        getFavoritesUseCase(AUTHOR, null).handle {
            onLoading {
                _uiStateFavorites.update { it.copy() }
            }
            onSuccess { authors ->
                _uiStateFavorites.update { it.copy(authors = authors.map { it.asFavorite() }) }
            }
        }
    }

    fun getFavoriteEpisodes() = viewModelScope.launch {
        getFavoritesOriginalsUseCase().handle {
            onLoading {
                _uiStateFavorites.update { it.copy(isLoading = true) }
            }
            onSuccess { originals ->
                _uiStateFavorites.update {
                    it.copy(
                        isLoading = false,
                        originals = originals.map { it.asFavoriteOriginal() })
                }
            }
            onFailure {
                _uiStateFavorites.update { it.copy(isLoading = false) }
            }
        }
    }
}