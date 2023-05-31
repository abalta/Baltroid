package com.baltroid.core.network.source

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.network.api.service.HitReadsService
import com.baltroid.core.network.model.HitReadsResponse
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.model.originals.NetworkTag
import com.baltroid.core.network.model.response.LoginDto
import com.baltroid.core.network.model.response.OriginalResponseDto
import com.baltroid.core.network.util.DEFAULT_PAGE
import javax.inject.Inject

class HitReadsNetworkDataSource @Inject constructor(private val hitReadsService: HitReadsService) {
    suspend fun getOriginals(
        page: Int = DEFAULT_PAGE
    ): BaltroidResult<HitReadsResponse<OriginalResponseDto>> = hitReadsService.getOriginals(page)

    suspend fun likeOriginal(
        originalId: Int
    ): BaltroidResult<HitReadsResponse<Unit>> = hitReadsService.likeOriginal(originalId)

    suspend fun login(email: String, password: String): BaltroidResult<HitReadsResponse<LoginDto>> = hitReadsService.login(email, password)

    suspend fun showOriginal(originalId: Int): BaltroidResult<HitReadsResponse<NetworkOriginal>> = hitReadsService.showOriginal(originalId)

    suspend fun getTags(): BaltroidResult<HitReadsResponse<List<NetworkTag>>> = hitReadsService.getTags()

}