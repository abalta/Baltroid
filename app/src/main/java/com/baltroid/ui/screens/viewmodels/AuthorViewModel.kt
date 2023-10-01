package com.baltroid.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
import com.baltroid.util.AUTHOR
import com.baltroid.util.orZero
import com.hitreads.core.domain.usecase.CreateFavoriteUseCase
import com.hitreads.core.domain.usecase.LikeCommentUseCase
import com.hitreads.core.domain.usecase.ShowAuthorUseCase
import com.hitreads.core.domain.usecase.UnlikeCommentUseCase
import com.hitreads.core.model.Author
import com.hitreads.core.ui.mapper.asAuthor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val showAuthorUseCase: ShowAuthorUseCase,
    private val commentUnlikeCommentUseCase: UnlikeCommentUseCase,
    private val commentLikeCommentUseCase: LikeCommentUseCase,
    private val createFavoriteUseCase: CreateFavoriteUseCase
) : ViewModel() {

    private val _author = MutableStateFlow(AuthorScreenUiState())
    val author = _author.asStateFlow()

    fun showAuthor(id: Int) {
        viewModelScope.launch {
            showAuthorUseCase(id).handle {
                onLoading {
                    _author.update { it.copy(isLoading = true) }
                }
                onSuccess { model ->
                    _author.update { it.copy(isLoading = false, author = model.asAuthor()) }
                }
                onFailure {
                    _author.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun createFavorite(author: Author?) {
        viewModelScope.launch {
            createFavoriteUseCase.invoke(AUTHOR, author?.id.orZero()).handle {
                onLoading {
                    _author.update { it.copy(isLoading = true) }
                }
                onSuccess {
                    _author.update {
                        it.copy(
                            author = author?.copy(isFavorite = true),
                            isLoading = false
                        )
                    }
                }
                onFailure {
                    _author.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun likeComment(id: Int) = viewModelScope.launch {
        commentLikeCommentUseCase(id).handle {
            onLoading {
                _author.update { it.copy(isLoading = true) }
            }
            onSuccess {
                _author.update { state ->
                    val updatedList =
                        if (state.author?.comments?.firstOrNull { it.id == id } != null) {
                            state.author.comments?.map { if (it.id == id) it.copy(isLiked = true) else it }
                        } else {
                            state.author?.comments?.map {
                                it.copy(replies = it.replies.map {
                                    if (it.id == id) it.copy(isLiked = true) else it
                                })
                            }
                        }
                    state.copy(
                        author = state.author?.copy(comments = updatedList),
                        isLoading = false
                    )
                }
            }
            onFailure {
                _author.update { it.copy(isLoading = false) }
            }
        }
    }

    fun unlikeComment(id: Int) = viewModelScope.launch {
        commentUnlikeCommentUseCase(id).handle {
            onLoading {
                _author.update { it.copy(isLoading = true) }
            }
            onSuccess {
                _author.update { state ->
                    val updatedList =
                        if (state.author?.comments?.firstOrNull { it.id == id } != null) {
                            state.author.comments?.map { if (it.id == id) it.copy(isLiked = false) else it }
                        } else {
                            state.author?.comments?.map {
                                it.copy(replies = it.replies.map {
                                    if (it.id == id) it.copy(isLiked = false) else it
                                })
                            }
                        }
                    state.copy(
                        author = state.author?.copy(comments = updatedList),
                        isLoading = false
                    )
                }
            }
            onFailure {
                _author.update { it.copy(isLoading = false) }
            }
        }
    }
}

data class AuthorScreenUiState(
    val isLoading: Boolean = false,
    val author: Author? = null
)