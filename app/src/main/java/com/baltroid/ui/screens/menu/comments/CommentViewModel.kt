package com.baltroid.ui.screens.menu.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.usecase.CreateBookmarkUseCase
import com.hitreads.core.domain.usecase.DeleteBookmarkUseCase
import com.hitreads.core.domain.usecase.GetCommentsUseCase
import com.hitreads.core.domain.usecase.LikeCommentUseCase
import com.hitreads.core.domain.usecase.UnlikeCommentUseCase
import com.hitreads.core.ui.mapper.asComment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val commentsUseCase: GetCommentsUseCase,
    private val commentLikeCommentUseCase: LikeCommentUseCase,
    private val commentUnlikeCommentUseCase: UnlikeCommentUseCase,
    private val createBookmarkUseCase: CreateBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommentsUiState())
    val uiState = _uiState.asStateFlow()

    private val _likeUiState = MutableStateFlow(CommentsLikeUiState())
    val likeUiState = _likeUiState.asStateFlow()

    fun getComments(type: String, id: Int) = viewModelScope.launch {
        commentsUseCase(type, id).handle {
            onLoading {
                _uiState.update { it.copy(isLoading = true) }
            }
            onSuccess { commentList ->
                _uiState.update {
                    it.copy(commentList = commentList.map { model ->
                        model.asComment()
                    }, isLoading = false)
                }
            }
            onFailure(::handleFailure)
        }
    }

    fun likeComment(id: Int) = viewModelScope.launch {
        commentLikeCommentUseCase(id).handle {
            onLoading {
                _likeUiState.update { it.copy(isLike = null, isLoading = true) }
            }
            onSuccess {
                _likeUiState.update { it.copy(isLike = true, isLoading = false) }
            }
            onFailure(::handleFailure)
        }
    }

    fun unlikeComment(id: Int) = viewModelScope.launch {
        commentUnlikeCommentUseCase(id).handle {
            onLoading {
                _likeUiState.update { it.copy(isLike = null, isLoading = true) }
            }
            onSuccess {
                _likeUiState.update { it.copy(isLike = false, isLoading = false) }
            }
            onFailure(::handleFailure)
        }
    }

    private fun createBookMark(originalId: Int, episodeId: Int) = viewModelScope.launch {
        createBookmarkUseCase.invoke(originalId, episodeId).handle {
            onSuccess {

            }
        }
    }

    private fun deleteBookMark(bookmarkId: Int) = viewModelScope.launch {
        deleteBookmarkUseCase.invoke(bookmarkId).handle {
            onSuccess {

            }
        }
    }

    private fun handleFailure(error: Throwable) = _uiState.update {
        it.copy(
            error = error,
            isLoading = false
        )
    }

}