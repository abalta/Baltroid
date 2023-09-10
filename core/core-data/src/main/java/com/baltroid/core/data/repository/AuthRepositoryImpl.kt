package com.baltroid.core.data.repository

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.HttpException
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asLoginModel
import com.baltroid.core.data.mapper.asProfileModel
import com.baltroid.core.datastore.PreferencesDataStoreDataSource
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import com.hitreads.core.domain.model.LoginModel
import com.hitreads.core.domain.model.ProfileModel
import com.hitreads.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

    override fun isLogged(): Flow<BaltroidResult<Boolean>> = flow {
        val token = preferencesDataStoreDataSource.getToken().first()
        if (token.isNullOrEmpty()) {
            val throwable = HttpException(401)
            emit(BaltroidResult.failure(throwable, false))
        } else if (token.isNotEmpty()) {
            emit(BaltroidResult.success(true))
        } else {
            error("$MESSAGE_UNHANDLED_STATE $token")
        }
    }

    override fun register(
        name: String,
        email: String,
        password: String,
        userAgreement: Boolean,
        privacyPolicy: Boolean,
    ): Flow<BaltroidResult<Unit?>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.register(
            name,
            email,
            password,
            userAgreement = userAgreement,
            privacyPolicy = privacyPolicy
        )

        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.success(it))
                }
            }

            response.isFailure() -> {
                val throwable = response.error
                emit(BaltroidResult.failure(throwable))
            }

            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    }

    override fun getProfile(): Flow<BaltroidResult<ProfileModel>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.getProfile()

        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.success(it.asProfileModel()))
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