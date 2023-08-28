package com.baltroid.ui.screens.menu.place_marks

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor() : ViewModel() {

    private val _bookMarks: MutableStateFlow<BookmarkScreenStates> =
        MutableStateFlow(BookmarkScreenStates())
    val bookMarks: StateFlow<BookmarkScreenStates> = _bookMarks.asStateFlow()

    init {
        //getBookmarks()
    }

    /*private fun getBookmarks() = viewModelScope.launch {
        bookmarkUseCase().handle {
            onSuccess { bookMarkList ->
                _bookMarks.update { it.copy(bookmarks = bookMarkList.map { it.asBookmark() }) }
            }
        }
    }*/
}