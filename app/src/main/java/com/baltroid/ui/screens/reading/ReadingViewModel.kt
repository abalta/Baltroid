package com.baltroid.ui.screens.reading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.model.CommentModel
import com.hitreads.core.domain.usecase.CreateBookmarkUseCase
import com.hitreads.core.domain.usecase.GetCommentsUseCase
import com.hitreads.core.domain.usecase.LikeOriginalUseCase
import com.hitreads.core.domain.usecase.ShowEpisodeUseCase
import com.hitreads.core.domain.usecase.ShowOriginalUseCase
import com.hitreads.core.domain.usecase.UnlikeOriginalUseCase
import com.hitreads.core.model.Original
import com.hitreads.core.ui.mapper.asEpisode
import com.hitreads.core.ui.mapper.asShowOriginal
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
    private val showEpisodeUseCase: ShowEpisodeUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val createBookmarkUseCase: CreateBookmarkUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(ReadingUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateComments = MutableStateFlow<List<CommentModel>>(listOf())
    val uiStateComments = _uiStateComments.asStateFlow()

    var interactiveOriginal: Original? = null
    var textOriginal: Original? = null

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
                _uiState.update { it.copy(original = originalModel?.asShowOriginal(), isLoading = true) }
            }
            onSuccess { originalModel ->
                _uiState.update { it.copy(original = originalModel.asShowOriginal(), isLoading = false) }
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

    fun getComments(id: Int, type: String) = viewModelScope.launch {
        getCommentsUseCase(id = id, type = type).handle {
            onSuccess { data ->
                _uiStateComments.update { data }
            }
        }
    }

    private fun handleFailure(error: Throwable) = _uiState.update {
        it.copy(
            error = error,
            isLoading = false
        )
    }

    fun createBookmark(originalId: Int, episodeInt: Int) = viewModelScope.launch {
        createBookmarkUseCase(originalId, episodeInt).handle {
            onSuccess {
                //change icon color
            }

            onFailure {

            }
        }
    }

    fun deleteBookmark() {
        //todo not implemented
    }

}