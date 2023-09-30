package com.hitreads.core.domain.repository

import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.IndexOriginalModel
import com.hitreads.core.domain.model.ShowEpisodeModel
import com.hitreads.core.domain.model.TagsWithOriginalsModel
import kotlinx.coroutines.flow.Flow

interface OriginalRepository {
    /*fun getOriginals(
        filter: String? = null,
        getByFav: Boolean? = null
    ): Flow<PagingData<TagsWithOriginalsModel>>*/

    fun getOriginals(
        getByFav: Boolean? = null,
        continueReading: Boolean? = null
    ): Flow<BaltroidResult<List<TagsWithOriginalsModel>>>

    fun likeOriginal(originalId: Int): Flow<BaltroidResult<Unit?>>
    fun unlikeOriginal(originalId: Int): Flow<BaltroidResult<Unit?>>
    fun bulkPurchase(originalId: Int): Flow<BaltroidResult<Unit?>>
    fun showOriginal(originalId: Int): Flow<BaltroidResult<IndexOriginalModel>>
    fun showEpisode(episodeId: Int, originalType: String): Flow<BaltroidResult<ShowEpisodeModel>>
    suspend fun fetchEpisodeFromUrl(url: String): String
    fun startReadingEpisode(episodeId: Int): Flow<BaltroidResult<Unit>>
    fun endReadingEpisode(episodeId: Int): Flow<BaltroidResult<Unit>>
}