package com.baltroid.core.network.source

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.network.api.service.HitReadsService
import com.baltroid.core.network.model.HitReadsResponse
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.model.originals.NetworkTag
import com.baltroid.core.network.model.response.EpisodeResponseDto
import com.baltroid.core.network.model.response.LoginDto
import com.baltroid.core.network.model.response.OriginalResponseDto
import com.baltroid.core.network.util.DEFAULT_PAGE
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import javax.inject.Inject

class HitReadsNetworkDataSource @Inject constructor(private val hitReadsService: HitReadsService) {
    suspend fun getOriginals(
        page: Int = DEFAULT_PAGE,
        filter: String? = null
    ): BaltroidResult<HitReadsResponse<OriginalResponseDto>> =
        hitReadsService.getOriginals(page, filter)

    suspend fun likeOriginal(
        originalId: Int
    ): BaltroidResult<HitReadsResponse<Unit>> = hitReadsService.likeOriginal(originalId)

    suspend fun unlikeOriginal(
        originalId: Int
    ): BaltroidResult<HitReadsResponse<Unit>> = hitReadsService.unlikeOriginal(originalId)

    suspend fun login(email: String, password: String): BaltroidResult<HitReadsResponse<LoginDto>> =
        hitReadsService.login(email, password)

    suspend fun showOriginal(originalId: Int): BaltroidResult<HitReadsResponse<NetworkOriginal>> =
        hitReadsService.showOriginal(originalId)

    suspend fun getTags(): BaltroidResult<HitReadsResponse<List<NetworkTag>>> =
        hitReadsService.getTags()

    suspend fun showEpisode(episodeId: Int): BaltroidResult<HitReadsResponse<EpisodeResponseDto>> =
        hitReadsService.showEpisode(episodeId)

    fun fetchTextFromUrl(url: String): String {
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder().url(url).build()
        val response: Response
        try {
            response = client.newCall(request).execute()
            val body: ResponseBody? = response.body
            if (response.isSuccessful && body != null) {
                return body.string()
            } else {
                throw IOException("Error fetching text from URL: ${response.code}")
            }
        } catch (e: Exception) {
            throw IOException("Error fetching text from URL: ${e.message}")
        }
    }
}