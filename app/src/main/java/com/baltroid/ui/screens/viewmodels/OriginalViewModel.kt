package com.baltroid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.usecase.CreateBookmarkUseCase
import com.hitreads.core.domain.usecase.CreateCommentUseCase
import com.hitreads.core.domain.usecase.CreateFavoriteUseCase
import com.hitreads.core.domain.usecase.DeleteBookmarkUseCase
import com.hitreads.core.domain.usecase.DeleteFavoriteUseCase
import com.hitreads.core.domain.usecase.GetOriginalsUseCase
import com.hitreads.core.domain.usecase.GetTagsUseCase
import com.hitreads.core.domain.usecase.IsLoggedUseCase
import com.hitreads.core.domain.usecase.ProfileUseCase
import com.hitreads.core.domain.usecase.ShowEpisodeUseCase
import com.hitreads.core.domain.usecase.ShowOriginalUseCase
import com.hitreads.core.model.Original
import com.hitreads.core.model.ShowEpisode
import com.hitreads.core.ui.mapper.asEpisode
import com.hitreads.core.ui.mapper.asOriginal
import com.hitreads.core.ui.mapper.asShowOriginal
import com.hitreads.core.ui.mapper.asTag
import com.hitreads.core.ui.mapper.asTagsWithOriginals
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
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _uiStateReading = MutableStateFlow(ReadingUiState())
    val uiStateReading = _uiStateReading.asStateFlow()

    private val _uiStateHome = MutableStateFlow(HomeUiState())
    val uiStateHome = _uiStateHome.asStateFlow()

    private val _sharedUIState = MutableStateFlow<Original?>(null)
    val sharedUIState = _sharedUIState.asStateFlow()

    private val _uiStateFilter = MutableStateFlow(FilterUiState())
    val uiStateFilter = _uiStateFilter.asStateFlow()

    init {
        isLogged()
    }

    var selectedOriginal: Original? = null
    var selectedEpisode: ShowEpisode? = null

    fun loadOriginals() = viewModelScope.launch {
        getOriginalsUseCase().handle {
            onLoading {
                _uiStateHome.update { it.copy(isLoading = true) }
            }
            onSuccess { result ->
                _uiStateHome.update {
                    it.copy(
                        originals = result.map { it.asTagsWithOriginals() },
                        isLoading = false
                    )
                }
            }
            onFailure {
                _uiStateHome.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun getProfile() {
        viewModelScope.launch {
            profileUseCase().handle {
                onLoading {
                    _uiStateHome.update { it.copy(isLoading = true) }
                }
                onSuccess { profile ->
                    _uiStateHome.update { it.copy(profileModel = profile, isLoading = false) }
                }
                onFailure {
                    _uiStateHome.update { it.copy(isLoading = false) }
                }
            }
        }
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

    fun deleteFavorite(original: Original?) = viewModelScope.launch {
        original?.let {
            deleteFavoriteUseCase.invoke("originals", it.id).handle {
                onSuccess {
                    _uiStateHome.update { uiState ->
                        val newList = uiState.favorites.toMutableList()
                        newList.remove(original)
                        uiState.copy(favorites = newList)
                    }
                }
            }
        }
    }


    private fun isLogged() = viewModelScope.launch {
        isLoggedUseCase().handle {
            onSuccess { isUserLoggedIn ->
                _uiStateHome.update { it.copy(isUserLoggedIn = isUserLoggedIn) }
                loadFavorites()
                getProfile()
            }
            onFailure {
                _uiStateHome.update { it.copy(isUserLoggedIn = false) }
            }
        }
    }

    fun setSharedUIState(original: Original?) {
        _sharedUIState.update { original }
    }

    private fun loadFavorites() = viewModelScope.launch {
        val favoriteOriginals = mutableListOf<Original>()
        getOriginalsUseCase.invoke(true).handle {
            onLoading {
                _uiStateHome.update { it.copy(isLoading = true) }
            }
            onSuccess { result ->
                result.forEach {
                    it.originals?.forEach {
                        favoriteOriginals.add(it.asOriginal())
                    }
                }
                _uiStateHome.update { it.copy(favorites = favoriteOriginals, isLoading = false) }
            }
            onFailure {
                _uiStateHome.update { it.copy(isLoading = false) }
            }
        }
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

    fun createComment(id: Int, content: String, responseId: Int?) = viewModelScope.launch {
        createCommentUseCase(
            type = "original",
            id,
            content,
            responseId
        ).handle {
            onSuccess { newComment ->
                _sharedUIState.update {
                    it?.copy(commentCount = it.commentCount + 1)
                }
            }
        }
    }

    private fun handleFailure(error: Throwable) = _uiStateReading.update {
        it.copy(
            error = error,
            isLoading = false
        )
    }

    //val filter = mutableStateListOf<Int>()
    /*fun loadOriginals() = _uiStateHome.update {
        val originals = getOriginalsUseCase(
            if (filter.isEmpty()) null
            else filter.joinToString { it.toString() }
        ).pagingMap {
            it.asTagsWithOriginals()
        }
            .cachedIn(viewModelScope)
        it.copy(
            originals = originals
        )
    }*/
}