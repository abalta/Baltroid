package com.baltroid.ui.screens.home.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.usecase.GetTagsUseCase
import com.hitreads.core.ui.mapper.asTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilterUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadFilters()
    }

    private fun loadFilters() = viewModelScope.launch {
        getTagsUseCase().handle {
            onLoading { list ->
                _uiState.update { state ->
                    state.copy(tagUiModels = list?.map {
                        it.asTag()
                    }.orEmpty(), isLoading = true)
                }
            }
            onSuccess { list ->
                _uiState.update { state ->
                    state.copy(tagUiModels = list.map {
                        it.asTag()
                    }, isLoading = true)
                }
            }
            onFailure(::handleFailure)
        }
    }

    private fun handleFailure(error: Throwable) = _uiState.update {
        it.copy(
            error = error,
            isLoading = false
        )
    }

}