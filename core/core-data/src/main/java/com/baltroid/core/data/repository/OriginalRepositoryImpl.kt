package com.baltroid.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asLoginModel
import com.baltroid.core.data.mapper.asOriginalModel
import com.baltroid.core.data.paging.OriginalsPagingSource
import com.baltroid.core.data.util.defaultPagingConfig
import com.baltroid.core.network.common.networkBoundResource
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.repository.OriginalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OriginalRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource
) : OriginalRepository {

    override fun getOriginals(): Flow<PagingData<OriginalModel>> = Pager(
        config = defaultPagingConfig,
        pagingSourceFactory = {
            OriginalsPagingSource(
                networkDataSource = networkDataSource,
            )
        }
    ).flow

    override fun likeOriginal(originalId: Int): Flow<BaltroidResult<Unit?>> = networkBoundResource {
        networkDataSource.likeOriginal(originalId)
    }

    override fun showOriginal(originalId: Int): Flow<BaltroidResult<OriginalModel>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.showOriginal(originalId)

        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.success(it.asOriginalModel()))
                }
            }

            response.isFailure() -> {
                val throwable = response.error
                emit(BaltroidResult.failure(throwable))
            }

            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    }

}
