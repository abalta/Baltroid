package com.baltroid.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.baltroid.ui.screens.home.HomeUiState
import com.baltroid.ui.screens.home.detail.HomeDetailUIState
import com.baltroid.ui.screens.reading.ReadingUiState
import com.baltroid.util.ORIGINALS
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
) : ViewModel() {

    private val _uiStateHome = MutableStateFlow(HomeUiState())
    val uiStateHome = _uiStateHome.asStateFlow()

    private val _uiStateDetail: MutableStateFlow<HomeDetailUIState> =
        MutableStateFlow(HomeDetailUIState())
    val uiStateDetail = _uiStateDetail.asStateFlow()

    private val _uiStateReading = MutableStateFlow(ReadingUiState())
    val uiStateReading = _uiStateReading.asStateFlow()

    var selectedOriginalId: Int? = null
    var selectedEpisodeId: Int? = null

    fun selectedEpisode(): ShowEpisode? = _uiStateDetail.value.original.episodes.firstOrNull {
        it.id == selectedEpisodeId
    }

    fun loadOriginals() = viewModelScope.launch {
        getOriginalsUseCase(null, null).handle {
            onLoading {
                _uiStateHome.update { it.copy(isLoading = true) }
            }
            onSuccess { result ->
                _uiStateHome.update { uiState ->
                    uiState.copy(
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
        getOriginalsUseCase.invoke(getByFav = null, continueReading = true).handle {
            onLoading {
                _uiStateHome.update { it.copy(isLoading = true) }
            }
            onSuccess { result ->
                result.forEach {
                    it.indexOriginalModels?.forEach { indexOriginalModel ->
                        continueReading.add(indexOriginalModel.asIndexOriginal())
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

    private fun getFavoriteOriginals() = viewModelScope.launch {
        getFavoriteOriginalsUseCase().handle {
            onLoading {
                _uiStateHome.update { it.copy(isLoading = true) }
            }
            onSuccess { favorites ->
                _uiStateHome.update { uiState ->
                    uiState.copy(
                        favorites = favorites.map { it.asFavoriteOriginal() },
                        isLoading = false
                    )
                }
            }
            onFailure {
                _uiStateHome.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteFavoriteHome(id: Int) {
        viewModelScope.launch {
            deleteFavoriteUseCase.invoke(ORIGINALS, id).handle {
                onLoading {
                    _uiStateHome.update { it.copy(isLoading = true) }
                }
                onSuccess {
                    _uiStateHome.update { uiState ->
                        val newFavorites = uiState.favorites.toMutableList()
                        newFavorites.removeIf { it.id == id }
                        uiState.copy(favorites = newFavorites, isLoading = false)
                    }
                }
                onFailure { _uiStateHome.update { it.copy(isLoading = false) } }
            }
        }
    }

    fun createFavorite(id: Int) = viewModelScope.launch {
        createFavoriteUseCase.invoke(ORIGINALS, id).handle {
            onLoading {
                _uiStateReading.update { it.copy(isLoading = true) }
            }
            onSuccess {
                _uiStateReading.update { it.copy(isLoading = false) }
                _uiStateDetail.update {
                    it.copy(
                        original = it.original.copy(
                            indexUserData = it.original.indexUserData.copy(
                                isFav = true
                            )
                        )
                    )
                }
            }
            onFailure {
                _uiStateReading.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteFavorite(id: Int) = viewModelScope.launch {
        deleteFavoriteUseCase.invoke(ORIGINALS, id).handle {
            onLoading {
                _uiStateReading.update { it.copy(isLoading = true) }
            }
            onSuccess {
                _uiStateReading.update { it.copy(isLoading = false) }
                _uiStateDetail.update {
                    it.copy(
                        original = it.original.copy(
                            indexUserData = it.original.indexUserData.copy(
                                isFav = false
                            )
                        )
                    )
                }
            }
            onFailure {
                _uiStateReading.update { it.copy(isLoading = false) }
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


    fun isLogged() = viewModelScope.launch {
        isLoggedUseCase().handle {
            onLoading {
                _uiStateHome.update { it.copy(isLoading = true) }
            }
            onSuccess { isUserLoggedIn ->
                _uiStateHome.update { it.copy(isUserLoggedIn = isUserLoggedIn, isLoading = false) }
                getProfile()
                getFavoriteOriginals()
                loadContinueReading()
            }
            onFailure {
                _uiStateHome.update { it.copy(isUserLoggedIn = false, isLoading = false) }
            }
        }
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

    fun showOriginal() = viewModelScope.launch {
        showOriginalUseCase(selectedOriginalId ?: -1).handle {
            onLoading {
                _uiStateDetail.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }
            onSuccess { originalModel ->
                _uiStateDetail.update {
                    it.copy(
                        original = originalModel.asIndexOriginal(),
                        isLoading = false
                    )
                }
            }
            onFailure {
                _uiStateDetail.update { it.copy(isLoading = false) }
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
            onSuccess { _ ->

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