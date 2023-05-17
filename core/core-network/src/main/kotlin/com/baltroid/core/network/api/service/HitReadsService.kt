package com.baltroid.core.network.api.service

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.network.model.HitReadsResponse
import com.baltroid.core.network.model.response.OriginalResponseDto
import com.baltroid.core.network.util.Constants.DEFAULT_PAGE
import com.baltroid.core.network.util.Constants.Fields.LIKE
import com.baltroid.core.network.util.Constants.Fields.PAGE
import com.baltroid.core.network.util.Constants.Path.ORIGINALS_INDEX
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HitReadsService {

    @GET(ORIGINALS_INDEX)
    suspend fun getOriginals(
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): BaltroidResult<HitReadsResponse<OriginalResponseDto>>

    @PUT("$ORIGINALS_INDEX/{id}/$LIKE")
    suspend fun likeOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

}