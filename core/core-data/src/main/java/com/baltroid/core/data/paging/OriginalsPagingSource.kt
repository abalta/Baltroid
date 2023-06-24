package com.baltroid.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.baltroid.core.common.result.HttpException
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asOriginalModel
import com.baltroid.core.data.util.Constants
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.DEFAULT_PAGE
import com.hitreads.core.domain.model.OriginalModel
import java.io.IOException

class OriginalsPagingSource(
    private val networkDataSource: HitReadsNetworkDataSource,
    private val filter: String? = null,
    private val getByFav: Boolean? = null
): PagingSource<Int, OriginalModel>() {
    override fun getRefreshKey(state: PagingState<Int, OriginalModel>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OriginalModel> {
        return try {
            val currentPage = params.key ?: DEFAULT_PAGE
            val response = networkDataSource.getOriginals(
                page = currentPage,
                filter,
                getByFav
            )

            when {
                response.isSuccess() -> {
                    val data = response.value.data?.originals?.map(NetworkOriginal::asOriginalModel)
                    data?.forEach {
                        it.dataCount = response.value.data?.dataCount ?: 0
                    }
                    val endOfPaginationReached = data?.isEmpty()

                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached == true) null else currentPage + 1

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
}