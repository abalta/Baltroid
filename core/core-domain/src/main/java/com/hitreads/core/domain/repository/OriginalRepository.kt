package com.hitreads.core.domain.repository

import androidx.paging.PagingData
import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.EpisodeModel
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.model.OriginalType
import kotlinx.coroutines.flow.Flow

interface OriginalRepository {
    fun getOriginals(filter: String? = null, getByFav: Boolean? = null): Flow<PagingData<OriginalModel>>
    fun likeOriginal(originalId: Int): Flow<BaltroidResult<Unit?>>
    fun unlikeOriginal(originalId: Int): Flow<BaltroidResult<Unit?>>
    fun showOriginal(originalId: Int): Flow<BaltroidResult<OriginalModel>>
    fun showEpisode(episodeId: Int, originalType: String): Flow<BaltroidResult<EpisodeModel>>
    suspend fun fetchEpisodeFromUrl(url: String): String
}