package com.baltroid.ui.screens.reading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.usecase.GetOriginalsUseCase
import com.hitreads.core.domain.usecase.LikeOriginalUseCase
import com.hitreads.core.domain.usecase.ShowOriginalUseCase
import com.hitreads.core.ui.mapper.asOriginal
import com.hitreads.core.ui.mapper.pagingMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val likeOriginalUseCase: LikeOriginalUseCase,
    private val showOriginalUseCase: ShowOriginalUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ReadingUiState())
    val uiState = _uiState.asStateFlow()

    private fun likeOriginal(id: Int) = viewModelScope.launch {
        likeOriginalUseCase(id).handle {
            onLoading {
                _uiState.update { it.copy(isLike = null, isLoading = true) }
            }
            onSuccess {
                _uiState.update { it.copy(isLike = null, isLoading = false) }
            }
            onFailure(::handleFailure)
        }
    }

    private fun showOriginal(id: Int) = viewModelScope.launch {
        showOriginalUseCase(id).handle {
            onLoading { originalModel ->
                _uiState.update { it.copy(original = originalModel?.asOriginal(), isLoading = true) }
            }
            onSuccess { originalModel ->
                _uiState.update { it.copy(original = originalModel.asOriginal(), isLoading = false) }
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