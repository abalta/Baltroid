package com.baltroid.core.data.repository

/*class TagRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource,
) : TagRepository {

    override fun getTagList(): Flow<BaltroidResult<List<IndexTagModel>>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.getTags()

        when {
            response.isSuccess() -> {
                response.value.data?.let { list ->
                    emit(BaltroidResult.success(list.map {
                        it.asIndexTagModel()
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
}*/
