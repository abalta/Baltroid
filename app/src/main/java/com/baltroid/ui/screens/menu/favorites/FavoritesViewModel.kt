package com.baltroid.ui.screens.menu.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.HttpException
import com.baltroid.core.common.result.handle
import com.baltroid.util.AUTHOR
import com.baltroid.util.ORIGINALS
import com.hitreads.core.domain.usecase.DeleteFavoriteUseCase
import com.hitreads.core.domain.usecase.GetFavoriteOriginalsUseCase
import com.hitreads.core.domain.usecase.GetFavoritesUseCase
import com.hitreads.core.domain.usecase.LogOutUseCase
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
    private val getFavoritesOriginalsUseCase: GetFavoriteOriginalsUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _uiStateFavorites = MutableStateFlow(FavoritesUIState())
    val uiStateFavorites = _uiStateFavorites.asStateFlow()

    var isSessionExpired by mutableStateOf(false)

    fun getFavoriteAuthors() = viewModelScope.launch {
        getFavoritesUseCase(AUTHOR, null).handle {
            onLoading {
                _uiStateFavorites.update { it.copy() }
            }
            onSuccess { authors ->
                _uiStateFavorites.update { it.copy(authors = authors.map { it.asFavorite() }) }
            }
            onFailure {
                checkSession(it)
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
                checkSession(it)
                _uiStateFavorites.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteFavoriteOriginal(id: Int) = viewModelScope.launch {
        deleteFavoriteUseCase.invoke(ORIGINALS, id).handle {
            onLoading {
                _uiStateFavorites.update { it.copy(isLoading = true) }
            }
            onSuccess {
                _uiStateFavorites.update { it.copy(isLoading = false) }
                _uiStateFavorites.update {
                    val newFavorites = it.originals.toMutableList()
                    newFavorites.removeIf { it.id == id }
                    it.copy(
                        originals = newFavorites
                    )
                }
            }
            onFailure {
                checkSession(it)
                _uiStateFavorites.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteFavoriteAuthor(id: Int) = viewModelScope.launch {
        deleteFavoriteUseCase.invoke(AUTHOR, id).handle {
            onLoading {
                _uiStateFavorites.update { it.copy(isLoading = true) }
            }
            onSuccess {
                _uiStateFavorites.update { it.copy(isLoading = false) }
                _uiStateFavorites.update {
                    val newAuthors = it.authors.toMutableList()
                    newAuthors.removeIf { it.id == id }
                    it.copy(
                        authors = newAuthors
                    )
                }
            }
            onFailure {
                checkSession(it)
                _uiStateFavorites.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun checkSession(it: Throwable) {
        try {
            if ((it as HttpException).statusCode == 401) {
                viewModelScope.launch {
                    logOutUseCase.invoke()
                    isSessionExpired = true
                }
            }
        } catch (e: Exception) {/* no-op */
        }
    }
}