package com.baltroid.ui.screens.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.baltroid.ui.screens.home.HomeUiState
import com.baltroid.ui.screens.home.detail.HomeDetailUIState
import com.baltroid.ui.screens.interactive.InteractiveScreenAction
import com.baltroid.ui.screens.reading.ReadingUiState
import com.baltroid.ui.screens.reading.comments.CommentsTabState
import com.baltroid.util.ORIGINAL
import com.baltroid.util.ORIGINALS
import com.baltroid.util.orZero
import com.hitreads.core.domain.model.OriginalType
import com.hitreads.core.domain.usecase.CreateCommentUseCase
import com.hitreads.core.domain.usecase.CreateFavoriteUseCase
import com.hitreads.core.domain.usecase.DeleteFavoriteUseCase
import com.hitreads.core.domain.usecase.EndReadingEpisodeUseCase
import com.hitreads.core.domain.usecase.GetCommentsLikedByMeByIdUseCase
import com.hitreads.core.domain.usecase.GetCommentsUseCase
import com.hitreads.core.domain.usecase.GetFavoriteOriginalsUseCase
import com.hitreads.core.domain.usecase.GetOriginalsUseCase
import com.hitreads.core.domain.usecase.IsLoggedUseCase
import com.hitreads.core.domain.usecase.LikeCommentUseCase
import com.hitreads.core.domain.usecase.ProfileUseCase
import com.hitreads.core.domain.usecase.ShowEpisodeUseCase
import com.hitreads.core.domain.usecase.ShowOriginalUseCase
import com.hitreads.core.domain.usecase.StartReadingEpisodeUseCase
import com.hitreads.core.domain.usecase.UnlikeCommentUseCase
import com.hitreads.core.model.IndexOriginal
import com.hitreads.core.ui.mapper.asComment
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
    private val commentLikeCommentUseCase: LikeCommentUseCase,
    private val commentUnlikeCommentUseCase: UnlikeCommentUseCase,
    private val startReadingEpisodeUseCase: StartReadingEpisodeUseCase,
    private val endReadingEpisodeUseCase: EndReadingEpisodeUseCase,
    private val getFavoriteOriginalsUseCase: GetFavoriteOriginalsUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getCommentsLikedByMeByIdUseCase: GetCommentsLikedByMeByIdUseCase,
) : ViewModel() {

    private val _uiStateHome = MutableStateFlow(HomeUiState())
    val uiStateHome = _uiStateHome.asStateFlow()

    private val _uiStateDetail: MutableStateFlow<HomeDetailUIState> =
        MutableStateFlow(HomeDetailUIState())
    val uiStateDetail = _uiStateDetail.asStateFlow()

    private val _uiStateReading = MutableStateFlow(ReadingUiState())
    val uiStateReading = _uiStateReading.asStateFlow()

    private val _selectedEpisodeId: MutableState<Int> = mutableStateOf(0)
    val selectedEpisodeId: State<Int> = _selectedEpisodeId

    var selectedOriginalId: Int? = null

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

    fun createFavorite() = viewModelScope.launch {
        createFavoriteUseCase.invoke(ORIGINALS, selectedOriginalId ?: -1).handle {
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

    fun deleteFavorite() = viewModelScope.launch {
        deleteFavoriteUseCase.invoke(ORIGINALS, selectedOriginalId ?: -1).handle {
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

    fun getOriginalComments() {
        getAllOriginalComments()
        getCommentsLikedByMeById()
    }

    private fun getAllOriginalComments() {
        viewModelScope.launch {
            getCommentsUseCase(type = ORIGINAL, 1).handle {
                onLoading {
                    _uiStateReading.update { it.copy(isLoading = true) }
                }
                onSuccess { comments ->
                    _uiStateReading.update {
                        it.copy(
                            isLoading = false,
                            allComments = comments.map { it.asComment() })
                    }
                }
                onFailure {
                    _uiStateReading.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun getCommentsLikedByMeById() {
        viewModelScope.launch {
            getCommentsLikedByMeByIdUseCase(type = ORIGINAL, 1).handle {
                onLoading {
                    _uiStateReading.update { it.copy(isLoading = true) }
                }
                onSuccess { comments ->
                    _uiStateReading.update {
                        it.copy(
                            isLoading = false,
                            commentsLikedByMe = comments.map { it.asComment() })
                    }
                }
                onFailure {
                    _uiStateReading.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun nextEpisode() {
        _uiStateReading.value.episode?.nextEpisodeId?.let {
            _selectedEpisodeId.value = it
            showEpisode(OriginalType.TEXT)
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

    fun startReadingEpisode() {
        viewModelScope.launch {
            startReadingEpisodeUseCase(_selectedEpisodeId.value).handle {
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
                val original = originalModel.copy(
                    episodes = originalModel.episodes?.sortedBy { it.episodeSort }
                )
                _uiStateDetail.update {
                    it.copy(
                        original = original.asIndexOriginal(),
                        isLoading = false
                    )
                }
            }
            onFailure {
                _uiStateDetail.update { it.copy(isLoading = false) }
            }
        }
    }

    fun expanseComment(id: Int, tabState: CommentsTabState) {
        when (tabState) {
            CommentsTabState.AllComments -> {
                _uiStateReading.update {
                    it.copy(allComments = it.allComments.map {
                        if (it.id == id) it.copy(isExpanded = true) else it
                    })
                }
            }

            CommentsTabState.MyFavorites -> {
                _uiStateReading.update {
                    it.copy(commentsLikedByMe = it.commentsLikedByMe.map {
                        if (it.id == id) it.copy(isExpanded = true) else it
                    })
                }
            }

            CommentsTabState.MyComments -> {/* no-op */

            }
        }
    }

    fun setSelectedEpisodeId(id: Int) {
        _selectedEpisodeId.value = id
    }

    // 761 OriginalType.INTERACTIVE
    fun showEpisode(type: String) = viewModelScope.launch {
        showEpisodeUseCase(_selectedEpisodeId.value, type).handle {
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

    fun createComment(content: String, responseId: Int?) = viewModelScope.launch {
        createCommentUseCase(
            type = "original",
            selectedOriginalId.orZero(),
            content,
            responseId
        ).handle {
            onLoading {
                _uiStateReading.update { it.copy(isLoading = true) }
            }
            onSuccess { _ ->
                _uiStateReading.update { it.copy(isLoading = false) }
                _uiStateDetail.update { it.copy(original = it.original.copy(commentCount = it.original.commentCount + 1)) }
            }
            onFailure {
                _uiStateReading.update { it.copy(isLoading = false) }
            }
        }
    }

    fun likeComment(id: Int, tabState: CommentsTabState) = viewModelScope.launch {
        commentLikeCommentUseCase(id).handle {
            onLoading {
                _uiStateReading.update { it.copy(isLoading = true) }
            }
            onSuccess {
                _uiStateReading.update { state ->
                    when (tabState) {
                        CommentsTabState.AllComments -> {
                            val updatedList =
                                if (state.allComments.firstOrNull { it.id == id } != null) {
                                    state.allComments.map { if (it.id == id) it.copy(isLiked = true) else it }
                                } else {
                                    state.allComments.map {
                                        it.copy(replies = it.replies.map {
                                            if (it.id == id) it.copy(isLiked = true) else it
                                        })
                                    }
                                }
                            state.copy(allComments = updatedList, isLoading = false)
                        }

                        CommentsTabState.MyFavorites -> {
                            val updatedList =
                                if (state.commentsLikedByMe.firstOrNull { it.id == id } != null) {
                                    state.commentsLikedByMe.map { if (it.id == id) it.copy(isLiked = true) else it }
                                } else {
                                    state.commentsLikedByMe.map {
                                        it.copy(replies = it.replies.map {
                                            if (it.id == id) it.copy(isLiked = true) else it
                                        })
                                    }
                                }
                            state.copy(commentsLikedByMe = updatedList, isLoading = false)
                        }

                        CommentsTabState.MyComments -> state
                    }
                }
            }
            onFailure {
                _uiStateReading.update { it.copy(isLoading = false) }
            }
        }
    }

    fun unlikeComment(id: Int, tabState: CommentsTabState) = viewModelScope.launch {
        commentUnlikeCommentUseCase(id).handle {
            onLoading {
                _uiStateReading.update { it.copy(isLoading = true) }
            }
            onSuccess {
                _uiStateReading.update { state ->
                    when (tabState) {
                        CommentsTabState.AllComments -> {
                            val updatedList =
                                if (state.allComments.firstOrNull { it.id == id } != null) {
                                    state.allComments.map { if (it.id == id) it.copy(isLiked = false) else it }
                                } else {
                                    state.allComments.map {
                                        it.copy(replies = it.replies.map {
                                            if (it.id == id) it.copy(isLiked = false) else it
                                        })
                                    }
                                }
                            state.copy(allComments = updatedList, isLoading = false)
                        }

                        CommentsTabState.MyFavorites -> {
                            val updatedList =
                                if (state.commentsLikedByMe.firstOrNull { it.id == id } != null) {
                                    state.commentsLikedByMe.map { if (it.id == id) it.copy(isLiked = false) else it }
                                } else {
                                    state.commentsLikedByMe.map {
                                        it.copy(replies = it.replies.map {
                                            if (it.id == id) it.copy(isLiked = false) else it
                                        })
                                    }
                                }
                            state.copy(commentsLikedByMe = updatedList, isLoading = false)
                        }

                        CommentsTabState.MyComments -> state
                    }
                }
            }
            onFailure {
                _uiStateReading.update { it.copy(isLoading = false) }
            }
        }
    }

    fun handleUiEvent(interactiveScreenAction: InteractiveScreenAction) {
        when (interactiveScreenAction) {
            InteractiveScreenAction.CREATE_FAVORITE -> {
                if (_uiStateDetail.value.original.indexUserData.isFav) {
                    deleteFavorite()
                } else {
                    createFavorite()
                }
            }

            InteractiveScreenAction.SHARE -> {

            }

            InteractiveScreenAction.GO_TO_BEGINNING -> {
                _selectedEpisodeId.value = _uiStateDetail.value.original.episodes
                    .firstOrNull { it.sort == 1 }?.nextEpisodeId.orZero()
            }

            InteractiveScreenAction.NEXT_EPISODE -> {
                _selectedEpisodeId.value = _uiStateReading.value.episode?.nextEpisodeId.orZero()
            }

            InteractiveScreenAction.CREATE_COMMENT -> {

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