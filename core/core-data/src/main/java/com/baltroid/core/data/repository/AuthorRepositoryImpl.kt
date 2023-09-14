package com.baltroid.core.data.repository

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asAuthorModel
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import com.hitreads.core.domain.model.AuthorModel
import com.hitreads.core.domain.repository.AuthorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthorRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource
) : AuthorRepository {
    override fun showAuthor(id: Int): Flow<BaltroidResult<AuthorModel>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.showAuthor(id)

        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.success(it.asAuthorModel()))
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