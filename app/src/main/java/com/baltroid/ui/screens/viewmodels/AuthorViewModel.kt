package com.baltroid.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baltroid.core.common.result.handle
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
    private val likeCommentUseCase: LikeCommentUseCase,
    private val unlikeCommentUseCase: UnlikeCommentUseCase
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
}

data class AuthorScreenUiState(
    val isLoading: Boolean = false,
    val author: Author? = null
)