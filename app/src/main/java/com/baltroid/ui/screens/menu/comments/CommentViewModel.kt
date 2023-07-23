package com.baltroid.ui.screens.menu.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.usecase.CreateBookmarkUseCase
import com.hitreads.core.domain.usecase.DeleteBookmarkUseCase
import com.hitreads.core.domain.usecase.GetAllCommentsUseCase
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
    private val getAllCommentsUseCase: GetAllCommentsUseCase,
    private val commentLikeCommentUseCase: LikeCommentUseCase,
    private val commentUnlikeCommentUseCase: UnlikeCommentUseCase,
    private val createBookmarkUseCase: CreateBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommentsUiState())
    val uiState = _uiState.asStateFlow()

    fun getAllComments(type: String, id: Int?) = viewModelScope.launch {
        getAllCommentsUseCase(type, id).handle {
            onLoading {
                _uiState.update { it.copy(isLoading = true) }
            }
            onSuccess { commentList ->
                _uiState.update {
                    it.copy(commentList = commentList.map { it.asComment() }, isLoading = false)
                }
            }
            onFailure(::handleFailure)
        }
    }

    fun likeComment(id: Int) = viewModelScope.launch {
        commentLikeCommentUseCase(id).handle {
            onSuccess {
                _uiState.update { commentsUiState ->
                    val updatedList =
                        if (commentsUiState.commentList.firstOrNull { it.id == id } != null) {
                            commentsUiState.commentList.map { if (it.id == id) it.copy(isLiked = true) else it }
                        } else {
                            commentsUiState.commentList.map {
                                it.copy(replies = it.replies.map {
                                    if (it.id == id) it.copy(isLiked = true) else it
                                })
                            }
                        }
                    commentsUiState.copy(commentList = updatedList)
                }
            }
            onFailure(::handleFailure)
        }
    }

    fun unlikeComment(id: Int) = viewModelScope.launch {
        commentUnlikeCommentUseCase(id).handle {
            onSuccess {
                _uiState.update { commentsUiState ->
                    val updatedList =
                        if (commentsUiState.commentList.firstOrNull { it.id == id } != null) {
                            commentsUiState.commentList.map { if (it.id == id) it.copy(isLiked = false) else it }
                        } else {
                            commentsUiState.commentList.map {
                                it.copy(replies = it.replies.map {
                                    if (it.id == id) it.copy(isLiked = false) else it
                                })
                            }
                        }
                    commentsUiState.copy(commentList = updatedList)
                }
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