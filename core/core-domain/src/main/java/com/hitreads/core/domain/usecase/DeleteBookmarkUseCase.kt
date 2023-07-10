package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.BookmarkRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(private val repository: BookmarkRepository) {
    operator fun invoke(bookmarkId: Int) =
        repository.deleteBookmark(bookmarkId)
}
