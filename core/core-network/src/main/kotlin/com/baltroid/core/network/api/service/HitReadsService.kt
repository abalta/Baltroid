package com.baltroid.core.network.api.service

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.network.model.HitReadsResponse
import com.baltroid.core.network.model.originals.IndexNetworkOriginal
import com.baltroid.core.network.model.originals.NetworkCreateCommentResponse
import com.baltroid.core.network.model.request.ForgotPasswordRequestBody
import com.baltroid.core.network.model.request.PurchaseOptionRequestBody
import com.baltroid.core.network.model.request.RegisterRequestBody
import com.baltroid.core.network.model.response.AnnouncementDto
import com.baltroid.core.network.model.response.AuthorDto
import com.baltroid.core.network.model.response.AvatarDto
import com.baltroid.core.network.model.response.CommentDto
import com.baltroid.core.network.model.response.EpisodeShowDto
import com.baltroid.core.network.model.response.FavoriteDto
import com.baltroid.core.network.model.response.FavoriteOriginalDto
import com.baltroid.core.network.model.response.LoginDto
import com.baltroid.core.network.model.response.NotificationDto
import com.baltroid.core.network.model.response.ProfileDto
import com.baltroid.core.network.model.response.PurchaseDetailDto
import com.baltroid.core.network.model.response.TagWithOriginalsDto
import com.baltroid.core.network.model.response.WelcomeDto
import com.baltroid.core.network.util.Constants.Fields.ANNOUNCEMENT
import com.baltroid.core.network.util.Constants.Fields.AVATAR
import com.baltroid.core.network.util.Constants.Fields.AVATAR_ID
import com.baltroid.core.network.util.Constants.Fields.CONTENT
import com.baltroid.core.network.util.Constants.Fields.CONTINUE_READING
import com.baltroid.core.network.util.Constants.Fields.EMAIL
import com.baltroid.core.network.util.Constants.Fields.EPISODE_ID
import com.baltroid.core.network.util.Constants.Fields.FORGOT_PASSWORD
import com.baltroid.core.network.util.Constants.Fields.GET_BY_FAV
import com.baltroid.core.network.util.Constants.Fields.ID
import com.baltroid.core.network.util.Constants.Fields.NAME
import com.baltroid.core.network.util.Constants.Fields.ORIGINAL
import com.baltroid.core.network.util.Constants.Fields.ORIGINALS
import com.baltroid.core.network.util.Constants.Fields.ORIGINAL_ID
import com.baltroid.core.network.util.Constants.Fields.PASSWORD
import com.baltroid.core.network.util.Constants.Fields.PURCHASE
import com.baltroid.core.network.util.Constants.Fields.RESPONSE_ID
import com.baltroid.core.network.util.Constants.Fields.SET_CHOICE
import com.baltroid.core.network.util.Constants.Fields.TYPE
import com.baltroid.core.network.util.Constants.Fields.USER
import com.baltroid.core.network.util.Constants.Fields.USERNAME
import com.baltroid.core.network.util.Constants.Path.AUTHOR
import com.baltroid.core.network.util.Constants.Path.BY_ME
import com.baltroid.core.network.util.Constants.Path.COMMENT
import com.baltroid.core.network.util.Constants.Path.END
import com.baltroid.core.network.util.Constants.Path.EPISODE
import com.baltroid.core.network.util.Constants.Path.FAVORITE
import com.baltroid.core.network.util.Constants.Path.LIKE
import com.baltroid.core.network.util.Constants.Path.LIKED_BY_ME
import com.baltroid.core.network.util.Constants.Path.LOGIN
import com.baltroid.core.network.util.Constants.Path.NOTIFICATION
import com.baltroid.core.network.util.Constants.Path.ORIGINALS_INDEX
import com.baltroid.core.network.util.Constants.Path.PROFILE
import com.baltroid.core.network.util.Constants.Path.REGISTER
import com.baltroid.core.network.util.Constants.Path.SHOW
import com.baltroid.core.network.util.Constants.Path.START
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
        @Query(GET_BY_FAV) getByFav: Boolean? = null,
        @Query(CONTINUE_READING) continueReading: Boolean? = null
    ): BaltroidResult<HitReadsResponse<List<TagWithOriginalsDto>>>

    @GET(FAVORITE)
    suspend fun getFavoriteOriginals(
        @Query(TYPE) type: String = ORIGINALS
    ): BaltroidResult<HitReadsResponse<List<FavoriteOriginalDto>>>

    @DELETE(FAVORITE)
    suspend fun deleteFavorite(
        @Query(TYPE) type: String,
        @Query(ID) id: Int
    ): BaltroidResult<HitReadsResponse<Unit>>

    @GET("$ORIGINALS_INDEX/{id}")
    suspend fun showOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<IndexNetworkOriginal>>

    @GET("$ORIGINALS_INDEX/$EPISODE/{id}/$SHOW")
    suspend fun showEpisode(@Path("id") id: Int): BaltroidResult<HitReadsResponse<EpisodeShowDto>>

    @GET(COMMENT)
    suspend fun getComments(
        @Query(TYPE) type: String,
        @Query(ID) id: Int?,
        @Query(LIKED_BY_ME) likedByMe: Boolean? = null
    ): BaltroidResult<HitReadsResponse<List<CommentDto>>>

    @GET("$COMMENT/$BY_ME")
    suspend fun getCommentsByMe(
        @Query(ORIGINALS_INDEX) originalId: Int? = null
    ): BaltroidResult<HitReadsResponse<List<CommentDto>>>

    @GET("$COMMENT/$LIKE/$BY_ME")
    suspend fun getCommentsLikedByMe(): BaltroidResult<HitReadsResponse<List<CommentDto>>>

    @PUT("$ORIGINALS_INDEX/{id}/$LIKE")
    suspend fun likeOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @PUT("$ORIGINALS_INDEX/{id}/$UNLIKE")
    suspend fun unlikeOriginal(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @GET("$ORIGINAL/$EPISODE/{id}/$START")
    suspend fun startReadingEpisode(@Path("id") episodeId: Int): BaltroidResult<HitReadsResponse<Unit>>

    @GET("$ORIGINAL/$EPISODE/{id}/$END")
    suspend fun endReadingEpisode(@Path("id") episodeId: Int): BaltroidResult<HitReadsResponse<Unit>>

    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun login(
        @Field(EMAIL) email: String,
        @Field(PASSWORD) password: String
    ): BaltroidResult<HitReadsResponse<LoginDto>>

    @POST(REGISTER)
    suspend fun register(
        @Body requestBody: RegisterRequestBody
    ): BaltroidResult<HitReadsResponse<Unit>>

    @POST("$COMMENT/{id}/$LIKE")
    suspend fun likeComment(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @POST("$COMMENT/{id}/$UNLIKE")
    suspend fun unlikeComment(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @GET(WELCOME)
    suspend fun getWelcomeScreen(): BaltroidResult<HitReadsResponse<List<WelcomeDto>>>

    @FormUrlEncoded
    @POST(COMMENT)
    suspend fun createComment(
        @Field(TYPE) type: String,
        @Field(ID) id: Int,
        @Field(CONTENT) content: String,
        @Field(RESPONSE_ID) responseId: Int?
    ): BaltroidResult<HitReadsResponse<NetworkCreateCommentResponse>>

    @FormUrlEncoded
    @POST(FAVORITE)
    suspend fun createFavorite(
        @Field(TYPE) type: String,
        @Field(ID) id: Int
    ): BaltroidResult<HitReadsResponse<Unit>>

    @GET(FAVORITE)
    suspend fun getFavorites(
        @Query(TYPE) type: String,
        @Query(ID) id: Int?
    ): BaltroidResult<HitReadsResponse<List<FavoriteDto>>>

    @GET("$AUTHOR/{id}")
    suspend fun showAuthor(
        @Path("id") id: Int
    ): BaltroidResult<HitReadsResponse<AuthorDto>>

    @GET(PROFILE)
    suspend fun getProfile(): BaltroidResult<HitReadsResponse<ProfileDto>>

    @GET(NOTIFICATION)
    suspend fun getAllNotifications(): BaltroidResult<HitReadsResponse<List<NotificationDto>>>

    @GET(AVATAR)
    suspend fun getAvatars(): BaltroidResult<HitReadsResponse<List<AvatarDto>>>

    @POST(USER)
    @FormUrlEncoded
    suspend fun updateUserProfile(
        @Field(AVATAR_ID) avatarId: Int?,
        @Field(NAME) username: String?,
        @Field(USERNAME) nickname: String?,
        @Field(EMAIL) email: String?
    ): BaltroidResult<HitReadsResponse<Unit?>>

    @GET(ANNOUNCEMENT)
    suspend fun getAnnouncement(): BaltroidResult<HitReadsResponse<AnnouncementDto>>

    @POST(FORGOT_PASSWORD)
    suspend fun forgotPassword(@Body requestBody: ForgotPasswordRequestBody): BaltroidResult<HitReadsResponse<Unit>>

    @POST("$ORIGINALS_INDEX/$PURCHASE")
    @FormUrlEncoded
    suspend fun bulkPurchase(
        @Field(ORIGINAL_ID) originalId: Int
    ): BaltroidResult<HitReadsResponse<Unit?>>

    @POST("$ORIGINALS_INDEX/$EPISODE/{$EPISODE_ID}/$PURCHASE")
    suspend fun episodePurchase(
        @Path(EPISODE_ID) episodeId: Int
    ): BaltroidResult<HitReadsResponse<PurchaseDetailDto?>>

    @POST("$ORIGINAL/$EPISODE/{$EPISODE_ID}/$SET_CHOICE")
    suspend fun purchaseOption(
        @Path(EPISODE_ID) episodeId: Int,
        @Body purchaseOptionRequestBody: PurchaseOptionRequestBody
    ): BaltroidResult<HitReadsResponse<Unit?>>

    /*@GET(ORIGINALS_INDEX)
    suspend fun getOriginals(
        @Query(PAGE) page: Int = DEFAULT_PAGE,
        @Query(FILTER_TAG) filter: String? = null,
        @Query(GET_BY_FAV) getByFav: Boolean? = null
    ): BaltroidResult<HitReadsResponse<OriginalResponseDto>>*/

    /*@GET(BOOKMARK)
    suspend fun getBookmarks(): BaltroidResult<HitReadsResponse<List<BookmarkDto>>>

    @POST(BOOKMARK)
    suspend fun createBookmark(@Body request: CreateBookmarkDto): BaltroidResult<HitReadsResponse<BookmarkDto>>

    @DELETE("$BOOKMARK/{id}")
    suspend fun deleteBookmark(@Path("id") id: Int): BaltroidResult<HitReadsResponse<Unit>>

    @GET(TAG)
    suspend fun getTags(): BaltroidResult<HitReadsResponse<List<IndexNetworkTag>>>*/
}