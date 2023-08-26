package com.baltroid.core.data.repository

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asFavoriteModel
import com.baltroid.core.data.mapper.asFavoriteOriginalModel
import com.baltroid.core.network.common.networkBoundResource
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import com.hitreads.core.domain.model.FavoriteModel
import com.hitreads.core.domain.model.FavoriteOriginalModel
import com.hitreads.core.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource,
) : FavoriteRepository {

    override fun getFavorites(type: String, id: Int?): Flow<BaltroidResult<List<FavoriteModel>>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.getFavorites(type, id)

            when {
                response.isSuccess() -> {
                    response.value.data?.let {
                        emit(BaltroidResult.success(it.map { favoriteDto ->
                            favoriteDto.asFavoriteModel()
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

    override fun createFavorite(type: String, id: Int): Flow<BaltroidResult<Unit?>> =
        networkBoundResource {
            networkDataSource.createFavorite(type, id)
        }

    override fun deleteFavorite(type: String, id: Int): Flow<BaltroidResult<Unit?>> =
        networkBoundResource {
            networkDataSource.deleteFavorite(type, id)
        }

    override fun getFavoriteOriginals(): Flow<BaltroidResult<List<FavoriteOriginalModel>>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.getFavoriteOriginals()

        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.success(it.map { it.asFavoriteOriginalModel() }))
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