package com.baltroid.ui.screens.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.baltroid.core.common.result.handle
import com.baltroid.ui.screens.home.HomeUiState
import com.baltroid.ui.screens.home.filter.FilterUiState
import com.baltroid.ui.screens.reading.ReadingUiState
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.usecase.CreateBookmarkUseCase
import com.hitreads.core.domain.usecase.CreateFavoriteUseCase
import com.hitreads.core.domain.usecase.DeleteBookmarkUseCase
import com.hitreads.core.domain.usecase.DeleteFavoriteUseCase
import com.hitreads.core.domain.usecase.GetOriginalsUseCase
import com.hitreads.core.domain.usecase.GetTagsUseCase
import com.hitreads.core.domain.usecase.IsLoggedUseCase
import com.hitreads.core.domain.usecase.ShowEpisodeUseCase
import com.hitreads.core.domain.usecase.ShowOriginalUseCase
import com.hitreads.core.model.Original
import com.hitreads.core.ui.mapper.asEpisode
import com.hitreads.core.ui.mapper.asOriginal
import com.hitreads.core.ui.mapper.asShowOriginal
import com.hitreads.core.ui.mapper.asTag
import com.hitreads.core.ui.mapper.pagingMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OriginalViewModel @Inject constructor(
    private val showOriginalUseCase: ShowOriginalUseCase,
    private val showEpisodeUseCase: ShowEpisodeUseCase,
    private val getOriginalsUseCase: GetOriginalsUseCase,
    private val isLoggedUseCase: IsLoggedUseCase,
    private val createFavoriteUseCase: CreateFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    private val createBookmarkUseCase: CreateBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val _uiStateReading = MutableStateFlow(ReadingUiState())
    val uiStateReading = _uiStateReading.asStateFlow()

    private val _uiStateHome = MutableStateFlow(HomeUiState())
    val uiStateHome = _uiStateHome.asStateFlow()

    private val _sharedUIState = MutableStateFlow<Original?>(null)
    val sharedUIState = _sharedUIState.asStateFlow()

    private val _uiStateIsLogged = MutableStateFlow(false)
    val uiStateIsLogged = _uiStateIsLogged.asStateFlow()

    private val _uiStateFilter = MutableStateFlow(FilterUiState())
    val uiStateFilter = _uiStateFilter.asStateFlow()

    val filter = mutableStateListOf<Int>()

    fun loadOriginals() = _uiStateHome.update {
        val originals = getOriginalsUseCase(
            if (filter.isEmpty()) null
            else filter.joinToString { it.toString() }
        ).pagingMap(OriginalModel::asOriginal)
            .cachedIn(viewModelScope)

        it.copy(
            originals = originals
        )
    }

    init {
        isLogged()
    }

    fun createBookmark(originalId: Int, episodeId: Int) = viewModelScope.launch {
        createBookmarkUseCase(originalId, episodeId).handle {
            onSuccess {
                _uiStateReading.update { it.copy(episode = it.episode?.copy(isBookmarked = true)) }
            }
        }
    }

    fun deleteBookmark(id: Int) = viewModelScope.launch {
        deleteBookmarkUseCase.invoke(id).handle {
            onSuccess {
                _uiStateReading.update { it.copy(episode = it.episode?.copy(isFav = false)) }
            }
        }
    }

    fun createFavorite(id: Int) = viewModelScope.launch {
        createFavoriteUseCase.invoke("episode", id).handle {
            onSuccess {
                _uiStateReading.update { it.copy(episode = it.episode?.copy(isFav = true)) }
            }
        }
    }

    fun deleteFavorite(id: Int) = viewModelScope.launch {
        createFavoriteUseCase.invoke("episode", id).handle {
            onSuccess {
                _uiStateReading.update { it.copy(episode = it.episode?.copy(isFav = false)) }
            }
        }
    }


    private fun isLogged() = viewModelScope.launch {
        isLoggedUseCase().handle {
            onSuccess {
                _uiStateIsLogged.update { _ -> it }
            }
            onFailure {
                _uiStateIsLogged.update { _ -> false }
            }
        }
    }

    fun setSharedUIState(original: Original?) {
        _sharedUIState.update { original }
    }

    fun loadFavorites() = _uiStateHome.update {
        val originals = getOriginalsUseCase(getByFav = true).pagingMap(OriginalModel::asOriginal)
            .cachedIn(viewModelScope)

        it.copy(
            favorites = originals
        )
    }

    fun showOriginal(id: Int) = viewModelScope.launch {
        showOriginalUseCase(id).handle {
            onLoading { originalModel ->
                _uiStateReading.update {
                    it.copy(
                        original = originalModel?.asShowOriginal(),
                        isLoading = true
                    )
                }
            }
            onSuccess { originalModel ->
                _uiStateReading.update {
                    it.copy(
                        original = originalModel.asShowOriginal(),
                        isLoading = false
                    )
                }
            }
            onFailure(::handleFailure)
        }
    }

    // 761 OriginalType.INTERACTIVE
    fun showEpisode(id: Int, type: String) = viewModelScope.launch {
        showEpisodeUseCase(id, type).handle {
            onLoading { episodeModel ->
                _uiStateReading.update {
                    it.copy(
                        episode = episodeModel?.asEpisode(),
                        isLoading = true
                    )
                }
            }
            onSuccess { episodeModel ->
                _uiStateReading.update {
                    it.copy(
                        episode = episodeModel.asEpisode(),
                        isLoading = false
                    )
                }
            }
            onFailure(::handleFailure)
        }
    }

    fun loadFilters() = viewModelScope.launch {
        getTagsUseCase().handle {
            onLoading { list ->
                _uiStateFilter.update { state ->
                    state.copy(tagUiModels = list?.map {
                        it.asTag()
                    }.orEmpty(), isLoading = true)
                }
            }
            onSuccess { list ->
                _uiStateFilter.update { state ->
                    state.copy(tagUiModels = list.map {
                        it.asTag()
                    }, isLoading = true)
                }
            }
            onFailure(::handleFailure)
        }
    }

    private fun handleFailure(error: Throwable) = _uiStateReading.update {
        it.copy(
            error = error,
            isLoading = false
        )
    }
}