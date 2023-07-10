package com.hitreads.core.domain.usecase

import com.hitreads.core.domain.repository.BookmarkRepository
import javax.inject.Inject

class CreateBookmarkUseCase @Inject constructor(private val repository: BookmarkRepository) {
    operator fun invoke(originalId: Int, episodeId: Int) =
        repository.createBookmark(originalId, episodeId)
}
