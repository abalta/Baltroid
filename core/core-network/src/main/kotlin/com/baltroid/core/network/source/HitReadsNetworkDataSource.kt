package com.baltroid.core.network.source

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.network.api.service.HitReadsService
import com.baltroid.core.network.model.HitReadsResponse
import com.baltroid.core.network.model.originals.IndexNetworkOriginal
import com.baltroid.core.network.model.originals.NetworkCreateCommentResponse
import com.baltroid.core.network.model.request.RegisterRequestBody
import com.baltroid.core.network.model.response.AuthorDto
import com.baltroid.core.network.model.response.CommentDto
import com.baltroid.core.network.model.response.EpisodeShowDto
import com.baltroid.core.network.model.response.FavoriteDto
import com.baltroid.core.network.model.response.FavoriteOriginalDto
import com.baltroid.core.network.model.response.LoginDto
import com.baltroid.core.network.model.response.ProfileDto
import com.baltroid.core.network.model.response.TagWithOriginalsDto
import com.baltroid.core.network.model.response.WelcomeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import javax.inject.Inject

class HitReadsNetworkDataSource @Inject constructor(
    private val hitReadsService: HitReadsService,
) {

    suspend fun getOriginals(
        getByFav: Boolean?,
        continueReading: Boolean?
    ): BaltroidResult<HitReadsResponse<List<TagWithOriginalsDto>>> =
        hitReadsService.getOriginals(getByFav = getByFav, continueReading = continueReading)

    suspend fun likeOriginal(
        originalId: Int
    ): BaltroidResult<HitReadsResponse<Unit>> = hitReadsService.likeOriginal(originalId)

    suspend fun unlikeOriginal(
        originalId: Int
    ): BaltroidResult<HitReadsResponse<Unit>> = hitReadsService.unlikeOriginal(originalId)

    suspend fun login(email: String, password: String): BaltroidResult<HitReadsResponse<LoginDto>> =
        hitReadsService.login(email, password)

    suspend fun register(
        name: String,
        email: String,
        password: String,
        userAgreement: Boolean,
        privacyPolicy: Boolean,
    ): BaltroidResult<HitReadsResponse<Unit>> = hitReadsService.register(
        RegisterRequestBody(
            name = name,
            email = email,
            password = password,
            passwordConfirmation = password,
            accountType = "user",
            karma = "1",
            device = "1",
            identifier = "1",
            user_agreement = userAgreement,
            privacy_policy = privacyPolicy,
            clarificationText = true,
            acceptReceiveMarketingMail = true,
            birthDate = "1993-01-01"
        )
    )


    suspend fun showOriginal(originalId: Int): BaltroidResult<HitReadsResponse<IndexNetworkOriginal>> =
        hitReadsService.showOriginal(originalId)

    suspend fun showEpisode(episodeId: Int): BaltroidResult<HitReadsResponse<EpisodeShowDto>> =
        hitReadsService.showEpisode(episodeId)

    suspend fun startReadingEpisode(episodeId: Int): BaltroidResult<HitReadsResponse<Unit>> =
        hitReadsService.startReadingEpisode(episodeId)

    suspend fun endReadingEpisode(episodeId: Int): BaltroidResult<HitReadsResponse<Unit>> =
        hitReadsService.endReadingEpisode(episodeId)

    suspend fun getComments(
        type: String,
        id: Int
    ): BaltroidResult<HitReadsResponse<List<CommentDto>>> = hitReadsService.getComments(type, id)

    suspend fun fetchTextFromUrl(url: String): String {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient.Builder().build()
            val request = Request.Builder().url(url).build()
            val response: Response
            try {
                response = client.newCall(request).execute()
                val body: ResponseBody? = response.body
                if (response.isSuccessful && body != null) {
                    body.string()
                } else {
                    throw IOException("Error fetching text from URL: ${response.code}")
                }
            } catch (e: Exception) {
                throw IOException("Error fetching text from URL: ${e.message}")
            }
        }
    }

    suspend fun createComment(
        type: String,
        id: Int,
        content: String,
        responseId: Int?
    ): BaltroidResult<HitReadsResponse<NetworkCreateCommentResponse>> =
        hitReadsService.createComment(type, id, content, responseId)


    suspend fun getAllComments(
        type: String,
    ): BaltroidResult<HitReadsResponse<List<CommentDto>>> =
        hitReadsService.getComments(type, null)

    suspend fun getCommentsLikedByMe(
        type: String,
        id: Int
    ): BaltroidResult<HitReadsResponse<List<CommentDto>>> =
        hitReadsService.getComments(type, id, true)

    suspend fun getCommentsByMe(): BaltroidResult<HitReadsResponse<List<CommentDto>>> =
        hitReadsService.getCommentsByMe()

    suspend fun getCommentsLikedByMe(): BaltroidResult<HitReadsResponse<List<CommentDto>>> =
        hitReadsService.getCommentsLikedByMe()

    suspend fun likeComment(
        commentId: Int
    ): BaltroidResult<HitReadsResponse<Unit>> = hitReadsService.likeComment(commentId)

    suspend fun unlikeComment(
        commentId: Int
    ): BaltroidResult<HitReadsResponse<Unit>> = hitReadsService.unlikeComment(commentId)

    suspend fun getWelcomeScreen(): BaltroidResult<HitReadsResponse<List<WelcomeDto>>> =
        hitReadsService.getWelcomeScreen()

    suspend fun createFavorite(type: String, id: Int): BaltroidResult<HitReadsResponse<Unit>> =
        hitReadsService.createFavorite(type, id)

    suspend fun deleteFavorite(type: String, id: Int): BaltroidResult<HitReadsResponse<Unit>> =
        hitReadsService.deleteFavorite(type, id)

    suspend fun getFavorites(
        type: String,
        id: Int?
    ): BaltroidResult<HitReadsResponse<List<FavoriteDto>>> =
        hitReadsService.getFavorites(type, id)

    suspend fun getProfile(): BaltroidResult<HitReadsResponse<ProfileDto>> =
        hitReadsService.getProfile()

    suspend fun getFavoriteOriginals(): BaltroidResult<HitReadsResponse<List<FavoriteOriginalDto>>> =
        hitReadsService.getFavoriteOriginals()

    suspend fun showAuthor(id: Int): BaltroidResult<HitReadsResponse<AuthorDto>> =
        hitReadsService.showAuthor(id)

    /*suspend fun getOriginals(
        page: Int = DEFAULT_PAGE,
        filter: String? = null,
        getByFav: Boolean? = null
    ): BaltroidResult<HitReadsResponse<OriginalResponseDto>> =
        hitReadsService.getOriginals(page, filter, getByFav)

    suspend fun getBookmarkList(): BaltroidResult<HitReadsResponse<List<BookmarkDto>>> =
        hitReadsService.getBookmarks()

    suspend fun createBookmark(
        originalId: Int,
        episodeId: Int
    ): BaltroidResult<HitReadsResponse<BookmarkDto>> = hitReadsService.createBookmark(
        CreateBookmarkDto(originalId, episodeId)
    )

    suspend fun deleteBookmark(bookmarkId: Int): BaltroidResult<HitReadsResponse<Unit>> =
        hitReadsService.deleteBookmark(bookmarkId)

    suspend fun getTags(): BaltroidResult<HitReadsResponse<List<IndexNetworkTag>>> =
        hitReadsService.getTags()*/
}