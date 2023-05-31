package com.baltroid.core.network.api.service

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.network.model.HitReadsResponse
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.model.originals.NetworkTag
import com.baltroid.core.network.model.response.LoginDto
import com.baltroid.core.network.model.response.OriginalResponseDto
import com.baltroid.core.network.util.Constants.DEFAULT_PAGE
import com.baltroid.core.network.util.Constants.Fields.EMAIL
import com.baltroid.core.network.util.Constants.Fields.PAGE
import com.baltroid.core.network.util.Constants.Fields.PASSWORD
import com.baltroid.core.network.util.Constants.Path.LIKE
import com.baltroid.core.network.util.Constants.Path.LOGIN
import com.baltroid.core.network.util.Constants.Path.ORIGINALS_INDEX
import com.baltroid.core.network.util.Constants.Path.TAG
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HitReadsService {

    @GET(ORIGINALS_INDEX)
    suspend fun getOriginals(
        @Query(PAGE) page: Int = DEFAULT_PAGE
    ): BaltroidResult<HitReadsResponse<OriginalResponseDto>>

    @GET("$ORIGINALS_INDEX/{id}")
    suspend fun showOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<NetworkOriginal>>

    @PUT("$ORIGINALS_INDEX/{id}/$LIKE")
    suspend fun likeOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @POST("$LOGIN")
    suspend fun login(@Field("$EMAIL") email: String, @Field("$PASSWORD") password: String): BaltroidResult<HitReadsResponse<LoginDto>>

    @GET("$TAG")
    suspend fun getTags(): BaltroidResult<HitReadsResponse<List<NetworkTag>>>

}