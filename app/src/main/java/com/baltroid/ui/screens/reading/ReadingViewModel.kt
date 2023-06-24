package com.baltroid.ui.screens.reading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.usecase.LikeOriginalUseCase
import com.hitreads.core.domain.usecase.ShowEpisodeUseCase
import com.hitreads.core.domain.usecase.ShowOriginalUseCase
import com.hitreads.core.domain.usecase.UnlikeOriginalUseCase
import com.hitreads.core.ui.mapper.asEpisode
import com.hitreads.core.ui.mapper.asOriginal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingViewModel @Inject constructor(
    private val likeOriginalUseCase: LikeOriginalUseCase,
    private val unlikeOriginalUseCase: UnlikeOriginalUseCase,
    private val showOriginalUseCase: ShowOriginalUseCase,
    private val showEpisodeUseCase: ShowEpisodeUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ReadingUiState())
    val uiState = _uiState.asStateFlow()


    fun likeOriginal(id: Int) = viewModelScope.launch {
        likeOriginalUseCase(id).handle {
            onLoading {
                _uiState.update { it.copy(isLike = null, isLoading = true) }
            }
            onSuccess {
                _uiState.update { it.copy(isLike = true, isLoading = false) }
            }
            onFailure(::handleFailure)
        }
    }

    fun unlikeOriginal(id: Int) = viewModelScope.launch {
        unlikeOriginalUseCase(id).handle {
            onLoading {
                _uiState.update { it.copy(isLike = null, isLoading = true) }
            }
            onSuccess {
                _uiState.update { it.copy(isLike = false, isLoading = false) }
            }
            onFailure(::handleFailure)
        }
    }


    fun showOriginal(id: Int) = viewModelScope.launch {
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
    // 761 OriginalType.INTERACTIVE
    fun showEpisode(id: Int, type: String) = viewModelScope.launch {
        showEpisodeUseCase(id, type).handle {
            onLoading { episodeModel ->
                _uiState.update { it.copy(episode = episodeModel?.asEpisode(), isLoading = true) }
            }
            onSuccess { episodeModel ->
                _uiState.update { it.copy(episode = episodeModel.asEpisode(), isLoading = false) }
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