package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkUseCase @Inject constructor(private val repository: BookmarkRepository) {
    operator fun invoke() = repository.getBookmarkList()
}
