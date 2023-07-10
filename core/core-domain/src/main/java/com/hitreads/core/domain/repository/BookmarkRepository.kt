package com.hitreads.core.domain.repository

import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.BookmarkModel
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarkList(): Flow<BaltroidResult<List<BookmarkModel>>>
    fun createBookmark(originalId: Int, episodeId: Int): Flow<BaltroidResult<BookmarkModel>>
}