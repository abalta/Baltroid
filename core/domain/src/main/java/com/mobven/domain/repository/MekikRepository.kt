package com.mobven.domain.repository

import com.baltroid.core.common.BaltroidResult
import com.mobven.domain.model.LoginResponseModel
import kotlinx.coroutines.flow.Flow

interface MekikRepository {
    fun login(username: String, password: String): Flow<BaltroidResult<LoginResponseModel>>
}