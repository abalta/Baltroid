package com.baltroid.core.data.repository

import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.common.isFailure
import com.baltroid.core.common.isSuccess
import com.baltroid.core.data.mapper.asLoginResponseModel
import com.baltroid.core.network.source.MekikNetworkDataSource
import com.mobven.domain.model.LoginResponseModel
import com.mobven.domain.repository.MekikRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MekikRepositoryImpl @Inject constructor(
    private val networkDataSource: MekikNetworkDataSource
) : MekikRepository {
    override fun login(
        username: String,
        password: String
    ): Flow<BaltroidResult<LoginResponseModel>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.login(username, password)
        if (response.isSuccess()) {
            emit(BaltroidResult.success(response.value.asLoginResponseModel()))
        } else if (response.isFailure()) {
            emit(BaltroidResult.failure(response.error))
        }
    }.flowOn(Dispatchers.IO)
}