package com.baltroid.ui.screens.menu.place_marks

import com.hitreads.core.domain.model.BookmarkModel

data class BookmarkScreenStates(
    val bookmarks: List<BookmarkModel> = emptyList()
)