package com.baltroid.ui.screens.menu.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.hitreads.core.domain.usecase.CreateCommentUseCase
import com.hitreads.core.domain.usecase.GetAllCommentsUseCase
import com.hitreads.core.domain.usecase.LikeCommentUseCase
import com.hitreads.core.domain.usecase.UnlikeCommentUseCase
import com.hitreads.core.model.Comment
import com.hitreads.core.ui.mapper.asComment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val getAllCommentsUseCase: GetAllCommentsUseCase,
    private val commentLikeCommentUseCase: LikeCommentUseCase,
    private val commentUnlikeCommentUseCase: UnlikeCommentUseCase,
    private val createCommentUseCase: CreateCommentUseCase,


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

    fun expanseComment(id: Int) {
        _uiState.update {
            it.copy(commentList = it.commentList.map {
                if (it.id == id) it.copy(isExpanded = true) else it
            })
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
                _uiState.update {
                    val oldList = it.commentList.toMutableList()
                    val oldComment = it.commentList.firstOrNull { it.id == responseId }
                    val oldReplies = it.commentList.firstOrNull { it.id == responseId }?.replies
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
                    val index = it.commentList.indexOfFirst { it.id == responseId }
                    oldList.removeAt(index)
                    oldComment?.copy(replies = newReplies?.toList().orEmpty())?.let { it1 ->
                        oldList.add(
                            index,
                            it1
                        )
                    }
                    it.copy(commentList = oldList)
                }
            }
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