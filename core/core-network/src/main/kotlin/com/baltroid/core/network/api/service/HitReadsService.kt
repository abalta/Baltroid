package com.baltroid.core.network.api.service

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.network.model.HitReadsResponse
import com.baltroid.core.network.model.originals.NetworkCreateCommentResponse
import com.baltroid.core.network.model.originals.NetworkShowOriginal
import com.baltroid.core.network.model.originals.NetworkTag
import com.baltroid.core.network.model.request.CreateBookmarkDto
import com.baltroid.core.network.model.response.AllCommentsDto
import com.baltroid.core.network.model.response.BookmarkDto
import com.baltroid.core.network.model.response.CommentDto
import com.baltroid.core.network.model.response.EpisodeResponseDto
import com.baltroid.core.network.model.response.FavoriteDto
import com.baltroid.core.network.model.response.LoginDto
import com.baltroid.core.network.model.response.OriginalResponseDto
import com.baltroid.core.network.model.response.ProfileDto
import com.baltroid.core.network.model.response.WelcomeDto
import com.baltroid.core.network.util.Constants.DEFAULT_PAGE
import com.baltroid.core.network.util.Constants.Fields.ACCEPT_RECEIVE_MARKETING_MAIL
import com.baltroid.core.network.util.Constants.Fields.ACCOUNT_TYPE
import com.baltroid.core.network.util.Constants.Fields.BIRTH_DATE
import com.baltroid.core.network.util.Constants.Fields.CLARIFICATION_TEXT
import com.baltroid.core.network.util.Constants.Fields.CONTENT
import com.baltroid.core.network.util.Constants.Fields.DEVICE
import com.baltroid.core.network.util.Constants.Fields.EMAIL
import com.baltroid.core.network.util.Constants.Fields.FILTER_TAG
import com.baltroid.core.network.util.Constants.Fields.GET_BY_FAV
import com.baltroid.core.network.util.Constants.Fields.ID
import com.baltroid.core.network.util.Constants.Fields.IDENTIFIER
import com.baltroid.core.network.util.Constants.Fields.KARMA
import com.baltroid.core.network.util.Constants.Fields.NAME
import com.baltroid.core.network.util.Constants.Fields.PAGE
import com.baltroid.core.network.util.Constants.Fields.PASSWORD
import com.baltroid.core.network.util.Constants.Fields.PASSWORD_CONFIRMATION
import com.baltroid.core.network.util.Constants.Fields.PRIVACY_POLICY
import com.baltroid.core.network.util.Constants.Fields.RESPONSE_ID
import com.baltroid.core.network.util.Constants.Fields.TYPE
import com.baltroid.core.network.util.Constants.Fields.USER_AGREEMENT
import com.baltroid.core.network.util.Constants.Path.BOOKMARK
import com.baltroid.core.network.util.Constants.Path.BY_ME
import com.baltroid.core.network.util.Constants.Path.COMMENT
import com.baltroid.core.network.util.Constants.Path.EPISODE
import com.baltroid.core.network.util.Constants.Path.FAVORITE
import com.baltroid.core.network.util.Constants.Path.LIKE
import com.baltroid.core.network.util.Constants.Path.LOGIN
import com.baltroid.core.network.util.Constants.Path.ORIGINALS_INDEX
import com.baltroid.core.network.util.Constants.Path.PROFILE
import com.baltroid.core.network.util.Constants.Path.REGISTER
import com.baltroid.core.network.util.Constants.Path.SHOW
import com.baltroid.core.network.util.Constants.Path.TAG
import com.baltroid.core.network.util.Constants.Path.UNLIKE
import com.baltroid.core.network.util.Constants.Path.WELCOME
import retrofit2.http.Body
import retrofit2.http.DELETE
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
    suspend fun showOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<NetworkShowOriginal>>

    @PUT("$ORIGINALS_INDEX/{id}/$LIKE")
    suspend fun likeOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @PUT("$ORIGINALS_INDEX/{id}/$UNLIKE")
    suspend fun unlikeOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @FormUrlEncoded
    @POST("$LOGIN")
    suspend fun login(
        @Field("$EMAIL") email: String,
        @Field("$PASSWORD") password: String
    ): BaltroidResult<HitReadsResponse<LoginDto>>

    @FormUrlEncoded
    @POST("$REGISTER")
    suspend fun register(
        @Field("$NAME") name: String,
        @Field("$EMAIL") email: String,
        @Field("$PASSWORD") password: String,
        @Field("$PASSWORD_CONFIRMATION") passwordConfirmation: String = password,
        @Field("$ACCOUNT_TYPE") accountType: String = "user",
        @Field("$KARMA") karma: String = "1",
        @Field("$DEVICE") device: String = "1",
        @Field("$IDENTIFIER") identifier: String = "1",
        @Field("$USER_AGREEMENT") user_agreement: Boolean = true,
        @Field("$PRIVACY_POLICY") privacy_policy: Boolean = true,
        @Field("$CLARIFICATION_TEXT") clarificationText: Boolean = true,
        @Field("$ACCEPT_RECEIVE_MARKETING_MAIL") acceptReceiveMarketingMail: Boolean = true,
        @Field("$BIRTH_DATE") birthDate: String = "1993-01-01",
    ): BaltroidResult<HitReadsResponse<Unit>>

    @GET("$TAG")
    suspend fun getTags(): BaltroidResult<HitReadsResponse<List<NetworkTag>>>

    @GET("$ORIGINALS_INDEX/$EPISODE/{id}/$SHOW")
    suspend fun showEpisode(@Path("id") id: Int): BaltroidResult<HitReadsResponse<EpisodeResponseDto>>

    @GET("$COMMENT")
    suspend fun getComments(
        @Query("$TYPE") type: String,
        @Query("$ID") id: Int
    ): BaltroidResult<HitReadsResponse<List<CommentDto>>>

    @GET("$COMMENT")
    suspend fun getAllComments(
        @Query("$TYPE") type: String,
        @Query("$ID") id: Int?
    ): BaltroidResult<HitReadsResponse<List<AllCommentsDto>>>

    @GET("$COMMENT/$BY_ME")
    suspend fun getCommentsByMe(): BaltroidResult<HitReadsResponse<List<AllCommentsDto>>>


    @POST("$COMMENT/{id}/$LIKE")
    suspend fun likeComment(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @POST("$COMMENT/{id}/$UNLIKE")
    suspend fun unlikeComment(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @GET("$WELCOME")
    suspend fun getWelcomeScreen(): BaltroidResult<HitReadsResponse<List<WelcomeDto>>>

    @GET("$BOOKMARK")
    suspend fun getBookmarks(): BaltroidResult<HitReadsResponse<List<BookmarkDto>>>

    @POST("$BOOKMARK")
    suspend fun createBookmark(@Body request: CreateBookmarkDto): BaltroidResult<HitReadsResponse<BookmarkDto>>

    @FormUrlEncoded
    @POST("$COMMENT")
    suspend fun createComment(
        @Field("$TYPE") type: String,
        @Field("$ID") id: Int,
        @Field("$CONTENT") content: String,
        @Field("$RESPONSE_ID") responseId: Int?
    ): BaltroidResult<HitReadsResponse<NetworkCreateCommentResponse>>

    @FormUrlEncoded
    @POST("$FAVORITE")
    suspend fun createFavorite(
        @Field("$TYPE") type: String,
        @Field("$ID") id: Int
    ): BaltroidResult<HitReadsResponse<Unit>>

    @FormUrlEncoded
    @DELETE("$FAVORITE")
    suspend fun deleteFavorite(
        @Field("$TYPE") type: String,
        @Field("$ID") id: Int
    ): BaltroidResult<HitReadsResponse<Unit>>

    @GET("$FAVORITE")
    suspend fun getFavorites(
        @Query("$TYPE") type: String,
        @Query("$ID") id: Int?
    ): BaltroidResult<HitReadsResponse<List<FavoriteDto>>>

    @DELETE("$BOOKMARK/{id}")
    suspend fun deleteBookmark(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @GET("$PROFILE")
    suspend fun getProfile(): BaltroidResult<HitReadsResponse<ProfileDto>>

}