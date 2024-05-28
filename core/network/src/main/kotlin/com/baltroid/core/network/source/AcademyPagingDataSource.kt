package com.baltroid.core.network.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.network.model.AcademyDto
import com.baltroid.core.network.model.TeacherDto

class AcademyPagingDataSource(
    private val networkDataSource: MekikNetworkDataSource,
) : PagingSource<Int, AcademyDto>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val LIMIT = 20
    }

    override fun getRefreshKey(state: PagingState<Int, AcademyDto>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            val anchorPage = state.closestPageToPosition(anchorPos)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AcademyDto> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            when (val result = networkDataSource.academies(page, LIMIT)) {
                is BaltroidResult.Failure -> {
                    return LoadResult.Error(result.error)
                }

                is BaltroidResult.Success -> {
                    return LoadResult.Page(
                        data = result.value.result?.courses.orEmpty(),
                        prevKey = null,
                        nextKey = if ((result.value.result?.paginate?.total
                                ?: 0) <= page
                        ) null else page + 1
                    )
                }

                else -> {
                    LoadResult.Invalid()
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}