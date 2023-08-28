package com.baltroid.core.data.repository

/*class BookmarkRepositoryImpl @Inject constructor(
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

    override fun deleteBookmark(bookmarkId: Int): Flow<BaltroidResult<Unit>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.deleteBookmark(bookmarkId)
        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.Companion.success(Unit))
                }
            }

            response.isFailure() -> {
                val throwable = response.error
                emit(BaltroidResult.failure(throwable))
            }

            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    }
}*/
