package com.hitreads.core.domain.repository

import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.LoginModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<BaltroidResult<LoginModel>>
    fun isLogged(): Flow<BaltroidResult<Boolean>>
    fun register(name: String, email: String, password: String): Flow<BaltroidResult<Any?>>
}