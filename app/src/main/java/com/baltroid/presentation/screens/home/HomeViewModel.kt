package com.baltroid.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.usecase.GetOriginalsUseCase
import com.hitreads.core.ui.mapper.asOriginal
import com.hitreads.core.ui.mapper.pagingMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getOriginalsUseCase: GetOriginalsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateFavorites = MutableStateFlow(HomeUiState())
    val uiStateFavorites = _uiStateFavorites.asStateFlow()

    init {
        loadOriginals()
        loadFavoriteOriginals()
    }

    private fun loadOriginals() = _uiState.update {
        val originals = getOriginalsUseCase().pagingMap(OriginalModel::asOriginal)
            .cachedIn(viewModelScope)

        it.copy(
            originals = originals
        )
    }

    fun loadFilteredOriginals(filter: String) = _uiState.update {
        val originals = getOriginalsUseCase(filter = filter).pagingMap(OriginalModel::asOriginal)
            .cachedIn(viewModelScope)
        it.copy(
            originals = originals
        )
    }

    private fun loadFavoriteOriginals() = _uiState.update {
        val originals = getOriginalsUseCase(getByFav = true).pagingMap(OriginalModel::asOriginal)
            .cachedIn(viewModelScope)

        it.copy(
            originals = originals
        )
    }

}