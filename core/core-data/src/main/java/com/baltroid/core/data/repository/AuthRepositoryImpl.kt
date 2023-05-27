package com.baltroid.core.data.repository

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asLoginModel
import com.baltroid.core.datastore.PreferencesDataStoreDataSource
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import com.hitreads.core.domain.model.LoginModel
import com.hitreads.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource,
    private val preferencesDataStoreDataSource: PreferencesDataStoreDataSource
) : AuthRepository {

    override fun login(email: String, password: String): Flow<BaltroidResult<LoginModel>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.login(email, password)

        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    preferencesDataStoreDataSource.setToken(it.token)
                    emit(BaltroidResult.success(it.asLoginModel()))
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
