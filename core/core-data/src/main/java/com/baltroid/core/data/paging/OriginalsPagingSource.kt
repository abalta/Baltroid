package com.baltroid.core.data.paging

/*
class OriginalsPagingSource(
    private val networkDataSource: HitReadsNetworkDataSource,
    private val filter: String? = null,
    private val getByFav: Boolean? = null
) : PagingSource<Int, TagsWithOriginalsModel>() {
    override fun getRefreshKey(state: PagingState<Int, TagsWithOriginalsModel>) =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TagsWithOriginalsModel> {
        return try {
            val currentPage = params.key ?: DEFAULT_PAGE
            val response = networkDataSource.getOriginals(
                page = currentPage,
                filter,
                getByFav
            )

            when {
                response.isSuccess() -> {
                    val data = response.value.data?.tags?.map { it.asTagsWithOriginalsModel() }
                    val endOfPaginationReached =
                        (response.value.data?.totalPages == response.value.data?.totalResults)
                                || response.value.data?.tags?.isEmpty() == true

                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached) null else currentPage + 1

                    LoadResult.Page(
                        data = data.orEmpty(),
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }

                response.isFailure() -> return LoadResult.Error(response.error)
                else -> error("${Constants.Messages.UNHANDLED_STATE} $response")
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}*/