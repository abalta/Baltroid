package com.baltroid.core.network.source

import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.network.api.service.MekikService
import com.baltroid.core.network.model.DataResponse
import com.baltroid.core.network.model.LoginRequestDto
import com.baltroid.core.network.model.LoginResponseDto
import javax.inject.Inject

class MekikNetworkDataSource @Inject constructor(private val mekikService: MekikService) {
    suspend fun login(
        email: String,
        password: String
    ): BaltroidResult<DataResponse<LoginResponseDto>> = mekikService.login(LoginRequestDto(email, password))
}