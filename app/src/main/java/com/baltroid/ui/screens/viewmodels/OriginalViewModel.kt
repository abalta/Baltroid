package com.baltroid.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.baltroid.ui.screens.home.HomeUiState
import com.baltroid.ui.screens.reading.ReadingUiState
import com.hitreads.core.domain.usecase.CreateCommentUseCase
import com.hitreads.core.domain.usecase.CreateFavoriteUseCase
import com.hitreads.core.domain.usecase.DeleteFavoriteUseCase
import com.hitreads.core.domain.usecase.EndReadingEpisodeUseCase
import com.hitreads.core.domain.usecase.GetFavoriteOriginalsUseCase
import com.hitreads.core.domain.usecase.GetOriginalsUseCase
import com.hitreads.core.domain.usecase.IsLoggedUseCase
import com.hitreads.core.domain.usecase.ProfileUseCase
import com.hitreads.core.domain.usecase.ShowEpisodeUseCase
import com.hitreads.core.domain.usecase.ShowOriginalUseCase
import com.hitreads.core.domain.usecase.StartReadingEpisodeUseCase
import com.hitreads.core.model.IndexOriginal
import com.hitreads.core.model.ShowEpisode
import com.hitreads.core.ui.mapper.asFavoriteOriginal
import com.hitreads.core.ui.mapper.asIndexOriginal
import com.hitreads.core.ui.mapper.asShowEpisode
import com.hitreads.core.ui.mapper.asShowOriginal
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
    private val createCommentUseCase: CreateCommentUseCase,
    private val profileUseCase: ProfileUseCase,
    private val startReadingEpisodeUseCase: StartReadingEpisodeUseCase,
    private val endReadingEpisodeUseCase: EndReadingEpisodeUseCase,
    private val getFavoriteOriginalsUseCase: GetFavoriteOriginalsUseCase,
    //private val getTagsUseCase: GetTagsUseCase,
    //private val createBookmarkUseCase: CreateBookmarkUseCase,
    //private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val _uiStateReading = MutableStateFlow(ReadingUiState())
    val uiStateReading = _uiStateReading.asStateFlow()

    private val _uiStateHome = MutableStateFlow(HomeUiState())
    val uiStateHome = _uiStateHome.asStateFlow()

    private val _sharedUIState = MutableStateFlow<IndexOriginal?>(null)
    val sharedUIState = _sharedUIState.asStateFlow()

    init {
        loadOriginals()
        isLogged()
    }

    var selectedIndexOriginal: IndexOriginal? = null
    var selectedEpisode: ShowEpisode? = null

    private fun loadOriginals() = viewModelScope.launch {
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

    private fun loadContinueReading() = viewModelScope.launch {
        val continueReading = mutableListOf<IndexOriginal>()
        getOriginalsUseCase.invoke(continueReading = true).handle {
            onLoading {
                _uiStateHome.update { it.copy(isLoading = true) }
            }
            onSuccess { result ->
                result.forEach {
                    it.indexOriginalModels?.forEach {
                        continueReading.add(it.asIndexOriginal())
                    }
                }
                _uiStateHome.update {
                    it.copy(
                        continueReading = continueReading,
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

    fun getFavoriteOriginals() = viewModelScope.launch {
        getFavoriteOriginalsUseCase.invoke().handle {
            onSuccess { favorites ->
                _uiStateHome.update {
                    it.copy(favorites = favorites.map { it.asFavoriteOriginal() })
                }
            }
        }
    }

    /*fun createFavorite(id: Int) = viewModelScope.launch {
        createFavoriteUseCase.invoke("episode", id).handle {
            onSuccess {
                _uiStateReading.update { it.copy(episode = it.episode?.copy(isFav = true)) }
            }
        }
    }*/

    fun deleteFavorite(id: Int) = viewModelScope.launch {
        deleteFavoriteUseCase.invoke("originals", id).handle {
            onSuccess {
                _sharedUIState.update { original ->
                    original?.indexUserData?.copy(isLike = false)
                        ?.let { it1 -> original.copy(indexUserData = it1) }
                }
            }
        }
    }

    /* fun deleteFavorite(original: Original?) = viewModelScope.launch {
         original?.let {
             deleteFavoriteUseCase.invoke("originals", it.id).handle {
                 onSuccess {
                     _uiStateHome.update { uiState ->
                         val newList = uiState.favorites.toMutableList()
                         newList.remove(original)
                         uiState.copy(favorites = newList)
                     }
                 }
                 onFailure {
                 }
             }
         }
     }*/

    fun startReadingEpisode(episodeId: Int) {
        viewModelScope.launch {
            startReadingEpisodeUseCase(episodeId).handle {
                onLoading {

                }
                onSuccess {

                }
                onFailure {

                }
            }
        }
    }

    fun endReadingEpisode(episodeId: Int) {
        viewModelScope.launch {
            endReadingEpisodeUseCase(episodeId).handle {
                onLoading {

                }
                onSuccess {

                }
                onFailure {

                }
            }
        }
    }


    private fun isLogged() = viewModelScope.launch {
        isLoggedUseCase().handle {
            onSuccess { isUserLoggedIn ->
                _uiStateHome.update { it.copy(isUserLoggedIn = isUserLoggedIn) }
                getFavoriteOriginals()
                getProfile()
                loadContinueReading()
            }
            onFailure {
                _uiStateHome.update { it.copy(isUserLoggedIn = false) }
            }
        }
    }

    fun setSharedUIState(indexOriginal: IndexOriginal?) {
        _sharedUIState.update { indexOriginal }
    }

    /*private fun loadFavorites() = viewModelScope.launch {
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
    }*/

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
            onFailure {
                _uiStateHome.update { it.copy(isLoading = false) }
            }
        }
    }

    // 761 OriginalType.INTERACTIVE
    fun showEpisode(id: Int, type: String) = viewModelScope.launch {
        showEpisodeUseCase(id, type).handle {
            onLoading { episodeModel ->
                _uiStateReading.update {
                    it.copy(
                        episode = episodeModel?.asShowEpisode(),
                        isLoading = true
                    )
                }
            }
            onSuccess { episodeModel ->
                _uiStateReading.update {
                    it.copy(
                        episode = episodeModel.asShowEpisode(),
                        isLoading = false
                    )
                }
            }
            onFailure {
                _uiStateReading.update { it.copy(isLoading = false) }
            }
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

    /*private val _uiStateFilter = MutableStateFlow(FilterUiState())
    val uiStateFilter = _uiStateFilter.asStateFlow()*/

    /*fun createBookmark(originalId: Int, episodeId: Int) = viewModelScope.launch {
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
    }*/

    /*fun loadFilters() = viewModelScope.launch {
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
        }
    }*/

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