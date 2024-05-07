package com.baltroid.core.network.api.service

import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.network.model.DataResponse
import com.baltroid.core.network.model.LoginRequestDto
import com.baltroid.core.network.model.LoginResponseDto
import com.baltroid.core.network.util.Constants
import retrofit2.http.Body
import retrofit2.http.POST

interface MekikService {
    @POST(Constants.Path.LOGIN)
    suspend fun login(
        @Body request: LoginRequestDto
    ): BaltroidResult<DataResponse<LoginResponseDto>>
}