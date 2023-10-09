package com.baltroid.ui.screens.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.baltroid.ui.screens.home.HomeUiState
import com.baltroid.ui.screens.home.detail.HomeDetailUIState
import com.baltroid.ui.screens.interactive.InteractiveOptions
import com.baltroid.ui.screens.interactive.InteractiveScreenAction
import com.baltroid.ui.screens.reading.ReadingUiState
import com.baltroid.ui.screens.reading.comments.CommentsTabState
import com.baltroid.util.ORIGINAL
import com.baltroid.util.ORIGINALS
import com.baltroid.util.orZero
import com.hitreads.core.domain.model.NotificationModel
import com.hitreads.core.domain.model.OriginalType
import com.hitreads.core.domain.usecase.BulkPurchaseUseCase
import com.hitreads.core.domain.usecase.CreateCommentUseCase
import com.hitreads.core.domain.usecase.CreateFavoriteUseCase
import com.hitreads.core.domain.usecase.DeleteFavoriteUseCase
import com.hitreads.core.domain.usecase.EndReadingEpisodeUseCase
import com.hitreads.core.domain.usecase.EpisodePurchaseUseCase
import com.hitreads.core.domain.usecase.GetAllNotificationsUseCase
import com.hitreads.core.domain.usecase.GetCommentsByMeByIdUseCase
import com.hitreads.core.domain.usecase.GetCommentsLikedByMeByIdUseCase
import com.hitreads.core.domain.usecase.GetCommentsUseCase
import com.hitreads.core.domain.usecase.GetFavoriteOriginalsUseCase
import com.hitreads.core.domain.usecase.GetOriginalsUseCase
import com.hitreads.core.domain.usecase.IsLoggedUseCase
import com.hitreads.core.domain.usecase.LikeCommentUseCase
import com.hitreads.core.domain.usecase.OptionPurchaseUseCase
import com.hitreads.core.domain.usecase.ProfileUseCase
import com.hitreads.core.domain.usecase.ShowEpisodeUseCase
import com.hitreads.core.domain.usecase.ShowOriginalUseCase
import com.hitreads.core.domain.usecase.StartReadingEpisodeUseCase
import com.hitreads.core.domain.usecase.UnlikeCommentUseCase
import com.hitreads.core.model.Comment
import com.hitreads.core.model.IndexOriginal
import com.hitreads.core.model.ShowEpisode
import com.hitreads.core.ui.mapper.asComment
import com.hitreads.core.ui.mapper.asFavoriteOriginal
import com.hitreads.core.ui.mapper.asIndexOriginal
import com.hitreads.core.ui.mapper.asShowEpisode
import com.hitreads.core.ui.mapper.asTagsWithOriginals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val getAllNotificationsUseCase: GetAllNotificationsUseCase,
    private val getCommentsByMeByIdUseCase: GetCommentsByMeByIdUseCase,
    private val bulkPurchaseUseCase: BulkPurchaseUseCase,
    private val purchaseUseCase: EpisodePurchaseUseCase,
    private val optionPurchaseUseCase: OptionPurchaseUseCase
) : ViewModel() {

    private val _uiStateHome = MutableStateFlow(HomeUiState())
    val uiStateHome = _uiStateHome.asStateFlow()

    private val _uiStateDetail: MutableStateFlow<HomeDetailUIState> =
        MutableStateFlow(HomeDetailUIState())
    val uiStateDetail = _uiStateDetail.asStateFlow()

    private val _uiStateReading = MutableStateFlow(ReadingUiState())
    val uiStateReading = _uiStateReading.asStateFlow()

    private val _uiStateNotifications: MutableStateFlow<List<NotificationModel>> =
        MutableStateFlow(listOf())
    val uiStateNotifications: StateFlow<List<NotificationModel>> =
        _uiStateNotifications.asStateFlow()

    private val _selectedEpisodeId: MutableState<Int> = mutableStateOf(0)
    val selectedEpisodeId: State<Int> = _selectedEpisodeId

    var selectedOriginalId: Int? = null
    var selectedComment: Comment? = null

    private fun loadNotifications() = viewModelScope.launch {
        getAllNotificationsUseCase().handle {
            onSuccess { notifications ->
                _uiStateNotifications.update { notifications }
            }
        }
    }

    fun setSelectedOriginalId(originalId: Int) {
        selectedOriginalId = originalId
        showOriginal()
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

    fun getOriginalComments(tabState: CommentsTabState) {
        when (tabState) {
            CommentsTabState.AllComments -> {
                getAllOriginalComments()
            }

            CommentsTabState.MyFavorites -> {
                getCommentsLikedByMeById()
            }

            CommentsTabState.MyComments -> {
                getCommentsByMeById()
            }
        }
    }

    private fun getAllOriginalComments() {
        viewModelScope.launch {
            getCommentsUseCase(type = ORIGINAL, selectedOriginalId ?: -1).handle {
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
            getCommentsLikedByMeByIdUseCase(type = ORIGINAL, selectedOriginalId ?: -1).handle {
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

    private fun getCommentsByMeById() {
        viewModelScope.launch {
            getCommentsByMeByIdUseCase(selectedOriginalId ?: -1).handle {
                onLoading {
                    _uiStateReading.update { it.copy(isLoading = true) }
                }
                onSuccess { myComments ->
                    _uiStateReading.update {
                        it.copy(
                            isLoading = false,
                            commentsByMe = myComments.map { it.asComment() })
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

    fun startReadingEpisode() {
        if (_uiStateHome.value.isUserLoggedIn) {
            viewModelScope.launch {
                startReadingEpisodeUseCase(_selectedEpisodeId.value).handle { /* no-op */
                }
                endReadingEpisode()
            }
        }
    }

    private fun endReadingEpisode() {
        viewModelScope.launch {
            endReadingEpisodeUseCase(_selectedEpisodeId.value).handle { /* no-op */
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
                loadNotifications()
                getFavoriteOriginals()
                loadContinueReading()
            }
            onFailure {
                _uiStateHome.update { it.copy(isUserLoggedIn = false, isLoading = false) }
            }
        }
    }

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

            CommentsTabState.MyComments -> {
                _uiStateReading.update {
                    it.copy(commentsByMe = it.commentsByMe.map {
                        if (it.id == id) it.copy(isExpanded = true) else it
                    })
                }
            }
        }
    }

    fun hideComment(id: Int, tabState: CommentsTabState) {
        when (tabState) {
            CommentsTabState.AllComments -> {
                _uiStateReading.update {
                    it.copy(allComments = it.allComments.map {
                        if (it.id == id) it.copy(isExpanded = false) else it
                    })
                }
            }

            CommentsTabState.MyFavorites -> {
                _uiStateReading.update {
                    it.copy(commentsLikedByMe = it.commentsLikedByMe.map {
                        if (it.id == id) it.copy(isExpanded = false) else it
                    })
                }
            }

            CommentsTabState.MyComments -> {
                _uiStateReading.update {
                    it.copy(commentsByMe = it.commentsByMe.map {
                        if (it.id == id) it.copy(isExpanded = false) else it
                    })
                }
            }
        }
    }

    fun setSelectedEpisodeId(id: Int) {
        _selectedEpisodeId.value = id
    }

    fun getNextEpisode(): ShowEpisode? {
        return _uiStateDetail.value.original.episodes.firstOrNull { it.id == _uiStateReading.value.episode?.nextEpisodeId }
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
            type = ORIGINAL,
            selectedOriginalId.orZero(),
            content,
            responseId
        ).handle {
            onLoading {
                _uiStateReading.update { it.copy(isLoading = true) }
            }
            onSuccess { newComment ->
                _uiStateReading.update {
                    val newList = it.allComments.toMutableList()
                    newList.add(
                        Comment(
                            id = newComment.id,
                            imgUrl = "",
                            content = newComment.content,
                            repliesCount = newComment.repliesCount,
                            authorName = newComment.author.name.orEmpty(),
                            hashtag = "",
                            createdAt = newComment.createdAt,
                            isLiked = false,
                            isReply = false,
                            replies = listOf(),
                            episode = "",
                            indexOriginal = null
                        )
                    )
                    it.copy(isLoading = false, allComments = newList.toList())
                }
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
                    .firstOrNull { it.episodeSort == 1 }?.nextEpisodeId.orZero()
            }

            InteractiveScreenAction.NEXT_EPISODE -> {
                _selectedEpisodeId.value = _uiStateReading.value.episode?.nextEpisodeId.orZero()
            }

            InteractiveScreenAction.CREATE_COMMENT -> {

            }
        }
    }

    fun setReplyComment(comment: Comment) {
        selectedComment = comment
    }

    fun replyComment(comment: String, tab: CommentsTabState) = viewModelScope.launch {
        createCommentUseCase(
            type = ORIGINAL,
            selectedOriginalId.orZero(),
            comment,
            selectedComment?.id.orZero()
        ).handle {
            onLoading {
                _uiStateReading.update { it.copy(isLoading = true) }
            }
            onSuccess { newComment ->
                when (tab) {
                    CommentsTabState.AllComments -> {
                        _uiStateReading.update {
                            val oldList = it.allComments.toMutableList()
                            val oldComment =
                                it.allComments.firstOrNull { it.id == selectedComment?.id }
                            val oldReplies =
                                it.allComments.firstOrNull { it.id == selectedComment?.id }?.replies
                            val newReplies = oldReplies?.toMutableList()
                            newReplies?.add(
                                Comment(
                                    id = newComment.id,
                                    imgUrl = "",
                                    content = newComment.content,
                                    repliesCount = newComment.repliesCount,
                                    authorName = newComment.author.name.orEmpty(),
                                    hashtag = "",
                                    createdAt = newComment.createdAt,
                                    isLiked = false,
                                    isReply = true,
                                    replies = listOf(),
                                    episode = "",
                                    indexOriginal = null
                                )
                            )
                            val index = it.allComments.indexOfFirst { it.id == selectedComment?.id }
                            oldList.removeAt(index)
                            oldComment?.copy(
                                repliesCount = newReplies?.toList().orEmpty().size,
                                replies = newReplies?.toList().orEmpty()
                            )?.let { it1 ->
                                oldList.add(
                                    index,
                                    it1
                                )
                            }
                            it.copy(isLoading = false, allComments = oldList)
                        }
                    }

                    CommentsTabState.MyFavorites -> {
                        _uiStateReading.update {
                            val oldList = it.commentsLikedByMe.toMutableList()
                            val oldComment =
                                it.commentsLikedByMe.firstOrNull { it.id == selectedComment?.id }
                            val oldReplies =
                                it.commentsLikedByMe.firstOrNull { it.id == selectedComment?.id }?.replies
                            val newReplies = oldReplies?.toMutableList()
                            newReplies?.add(
                                Comment(
                                    id = newComment.id,
                                    imgUrl = "",
                                    content = newComment.content,
                                    repliesCount = newComment.repliesCount,
                                    authorName = newComment.author.name.orEmpty(),
                                    hashtag = "",
                                    createdAt = newComment.createdAt,
                                    isLiked = false,
                                    isReply = true,
                                    replies = listOf(),
                                    episode = "",
                                    indexOriginal = null
                                )
                            )
                            val index =
                                it.commentsLikedByMe.indexOfFirst { it.id == selectedComment?.id }
                            oldList.removeAt(index)
                            oldComment?.copy(
                                repliesCount = newReplies?.toList().orEmpty().size,
                                replies = newReplies?.toList().orEmpty()
                            )?.let { it1 ->
                                oldList.add(
                                    index,
                                    it1
                                )
                            }
                            it.copy(isLoading = false, commentsLikedByMe = oldList)
                        }
                    }

                    CommentsTabState.MyComments -> {
                        _uiStateReading.update {
                            val oldList = it.commentsByMe.toMutableList()
                            val oldComment =
                                it.commentsByMe.firstOrNull { it.id == selectedComment?.id }
                            val oldReplies =
                                it.commentsByMe.firstOrNull { it.id == selectedComment?.id }?.replies
                            val newReplies = oldReplies?.toMutableList()
                            newReplies?.add(
                                Comment(
                                    id = newComment.id,
                                    imgUrl = "",
                                    content = newComment.content,
                                    repliesCount = newComment.repliesCount,
                                    authorName = newComment.author.name.orEmpty(),
                                    hashtag = "",
                                    createdAt = newComment.createdAt,
                                    isLiked = false,
                                    isReply = true,
                                    replies = listOf(),
                                    episode = "",
                                    indexOriginal = null
                                )
                            )
                            val index =
                                it.commentsByMe.indexOfFirst { it.id == selectedComment?.id }
                            oldList.removeAt(index)
                            oldComment?.copy(
                                repliesCount = newReplies?.toList().orEmpty().size,
                                replies = newReplies?.toList().orEmpty()
                            )?.let { it1 ->
                                oldList.add(
                                    index,
                                    it1
                                )
                            }
                            it.copy(isLoading = false, commentsByMe = oldList)
                        }
                    }
                }
            }
            onFailure {
                _uiStateReading.update { it.copy(isLoading = false) }
            }
        }
    }

    fun bulkPurchase() {
        viewModelScope.launch {
            bulkPurchaseUseCase(selectedOriginalId ?: -1).handle {
                onLoading {
                    _uiStateDetail.update { it.copy(isLoading = true) }
                }
                onSuccess {
                    _uiStateDetail.update { it.copy(isLoading = false, originalPurchased = true) }
                }
                onFailure {
                    _uiStateDetail.update { it.copy(isLoading = false, originalPurchased = false) }
                }
            }
        }
    }

    fun clearPurchaseState() {
        _uiStateDetail.update { it.copy(originalPurchased = null) }
    }

    fun purchaseEpisode(showEpisode: ShowEpisode) {
        viewModelScope.launch {
            purchaseUseCase.invoke(showEpisode.id).handle {
                onLoading {
                    _uiStateDetail.update { it.copy(isLoading = true) }
                }
                onSuccess {
                    _uiStateDetail.update { uiState ->
                        val newList = uiState.original.episodes.map { episode ->
                            if (episode.id == showEpisode.id) episode.copy(isReadable = true)
                            else episode
                        }
                        val newOriginal = uiState.original.copy(episodes = newList)
                        uiState.copy(
                            isLoading = false,
                            episodePurchased = true,
                            original = newOriginal
                        )
                    }
                }
                onFailure {
                    _uiStateDetail.update { it.copy(isLoading = false, episodePurchased = false) }
                }
            }
        }
    }

    fun clearEpisodePurchaseState() {
        _uiStateDetail.update { it.copy(episodePurchased = null) }
    }

    fun clearOptionPurchased() {
        _uiStateReading.update { it.copy(optionPurchased = null) }
    }

    fun purchaseOption(currentLineId: String, interactiveOptions: InteractiveOptions?) {
        viewModelScope.launch {
            optionPurchaseUseCase.invoke(
                episodeId = _selectedEpisodeId.value,
                lineId = currentLineId,
                optionIndex = interactiveOptions?.index.orEmpty(),
                price = interactiveOptions?.cost?.filter { it.isDigit() }?.toInt() ?: 0
            ).handle {
                onLoading {
                    _uiStateReading.update { it.copy(isLoading = true) }
                }
                onSuccess {
                    _uiStateReading.update { it.copy(optionPurchased = true, isLoading = false) }
                }
                onFailure {
                    _uiStateReading.update { it.copy(optionPurchased = false, isLoading = false) }
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