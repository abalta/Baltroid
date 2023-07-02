package com.baltroid.core.data.repository

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asWelcomeModel
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import com.hitreads.core.domain.model.WelcomeModel
import com.hitreads.core.domain.repository.WelcomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WelcomeRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource,
) : WelcomeRepository {

    override fun getWelcomeList(): Flow<BaltroidResult<List<WelcomeModel>>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.getWelcomeScreen()

        when {
            response.isSuccess() -> {
                response.value.data?.let { list ->
                    emit(BaltroidResult.success(list.map {
                        it.asWelcomeModel()
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
}
