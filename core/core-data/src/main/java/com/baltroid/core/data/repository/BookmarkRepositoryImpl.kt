package com.baltroid.core.data.repository

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asBookmarkModel
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import com.hitreads.core.domain.model.BookmarkModel
import com.hitreads.core.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource,
) : BookmarkRepository {

    override fun getBookmarkList(): Flow<BaltroidResult<List<BookmarkModel>>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.getBookmarkList()

        when {
            response.isSuccess() -> {
                response.value.data?.let { list ->
                    emit(BaltroidResult.success(list.map {
                        it.asBookmarkModel()
                    }))
                }
            }

            response.isFailure() -> {
                val throwable = response.error
                emit(BaltroidResult.failure(throwable))
            }

            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    }

    override fun createBookmark(
        originalId: Int,
        episodeId: Int
    ): Flow<BaltroidResult<BookmarkModel>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.createBookmark(originalId, episodeId)
        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.Companion.success(it.asBookmarkModel()))
                }
            }

            response.isFailure() -> {
                val throwable = response.error
                emit(BaltroidResult.failure(throwable))
            }

            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    }

    override fun deleteBookmark(bookmarkId: Int): Flow<BaltroidResult<Unit>> {
        TODO("Not yet implemented")
    }
}
