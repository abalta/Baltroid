package com.baltroid.core.network.api.service

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.network.model.HitReadsResponse
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.model.originals.NetworkTag
import com.baltroid.core.network.model.response.CommentDto
import com.baltroid.core.network.model.response.EpisodeResponseDto
import com.baltroid.core.network.model.response.LoginDto
import com.baltroid.core.network.model.response.OriginalResponseDto
import com.baltroid.core.network.util.Constants.DEFAULT_PAGE
import com.baltroid.core.network.util.Constants.Fields.EMAIL
import com.baltroid.core.network.util.Constants.Fields.FILTER_TAG
import com.baltroid.core.network.util.Constants.Fields.GET_BY_FAV
import com.baltroid.core.network.util.Constants.Fields.ID
import com.baltroid.core.network.util.Constants.Fields.PAGE
import com.baltroid.core.network.util.Constants.Fields.PASSWORD
import com.baltroid.core.network.util.Constants.Fields.TYPE
import com.baltroid.core.network.util.Constants.Path.COMMENT
import com.baltroid.core.network.util.Constants.Path.EPISODE
import com.baltroid.core.network.util.Constants.Path.LIKE
import com.baltroid.core.network.util.Constants.Path.LOGIN
import com.baltroid.core.network.util.Constants.Path.ORIGINALS_INDEX
import com.baltroid.core.network.util.Constants.Path.SHOW
import com.baltroid.core.network.util.Constants.Path.TAG
import com.baltroid.core.network.util.Constants.Path.UNLIKE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HitReadsService {

    @GET(ORIGINALS_INDEX)
    suspend fun getOriginals(
        @Query(PAGE) page: Int = DEFAULT_PAGE,
        @Query(FILTER_TAG) filter: String? = null,
        @Query(GET_BY_FAV) getByFav: Boolean? = null
    ): BaltroidResult<HitReadsResponse<OriginalResponseDto>>

    @GET("$ORIGINALS_INDEX/{id}")
    suspend fun showOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<NetworkOriginal>>

    @PUT("$ORIGINALS_INDEX/{id}/$LIKE")
    suspend fun likeOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @PUT("$ORIGINALS_INDEX/{id}/$UNLIKE")
    suspend fun unlikeOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @FormUrlEncoded
    @POST("$LOGIN")
    suspend fun login(@Field("$EMAIL") email: String, @Field("$PASSWORD") password: String): BaltroidResult<HitReadsResponse<LoginDto>>

    @GET("$TAG")
    suspend fun getTags(): BaltroidResult<HitReadsResponse<List<NetworkTag>>>

    @GET("$ORIGINALS_INDEX/$EPISODE/{id}/$SHOW")
    suspend fun showEpisode(@Path("id") id: Int): BaltroidResult<HitReadsResponse<EpisodeResponseDto>>

    @FormUrlEncoded
    @GET("$COMMENT")
    suspend fun getComments(@Field("$TYPE") type: String, @Field("$ID") id: Int): BaltroidResult<HitReadsResponse<List<CommentDto>>>

    @POST("$COMMENT/{id}/$LIKE")
    suspend fun likeComment(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>
    @POST("$COMMENT/{id}/$UNLIKE")
    suspend fun unlikeComment(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

}