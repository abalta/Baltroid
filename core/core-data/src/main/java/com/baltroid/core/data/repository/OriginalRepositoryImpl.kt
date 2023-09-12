package com.baltroid.core.data.repository

import com.baltroid.core.common.model.XmlContent
import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asIndexOriginalModel
import com.baltroid.core.data.mapper.asShowEpisodeModel
import com.baltroid.core.data.mapper.asTagsWithOriginalsModel
import com.baltroid.core.network.common.networkBoundResource
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import com.github.underscore.U
import com.google.gson.Gson
import com.hitreads.core.domain.model.IndexOriginalModel
import com.hitreads.core.domain.model.OriginalType
import com.hitreads.core.domain.model.ShowEpisodeModel
import com.hitreads.core.domain.model.TagsWithOriginalsModel
import com.hitreads.core.domain.repository.OriginalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OriginalRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource
) : OriginalRepository {

    /*override fun getOriginals(
        filter: String?, getByFav: Boolean?
    ): Flow<PagingData<TagsWithOriginalsModel>> =
        Pager(config = defaultPagingConfig, pagingSourceFactory = {
            OriginalsPagingSource(
                networkDataSource = networkDataSource, filter, getByFav
            )
        }).flow*/

    override fun getOriginals(
        getByFav: Boolean?,
        continueReading: Boolean?
    ): Flow<BaltroidResult<List<TagsWithOriginalsModel>>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.getOriginals(
                getByFav = getByFav,
                continueReading = continueReading
            )
            when {
                response.isSuccess() -> {
                    response.value.data?.let {
                        emit(BaltroidResult.success(it.map { it.asTagsWithOriginalsModel() }))
                    }
                }

                response.isFailure() -> {
                    val throwable = response.error
                    emit(BaltroidResult.failure(throwable))
                }

                else -> error("$MESSAGE_UNHANDLED_STATE $response")
            }
        }

    override fun likeOriginal(originalId: Int): Flow<BaltroidResult<Unit?>> = networkBoundResource {
        networkDataSource.likeOriginal(originalId)
    }

    override fun unlikeOriginal(originalId: Int): Flow<BaltroidResult<Unit?>> =
        networkBoundResource {
            networkDataSource.unlikeOriginal(originalId)
        }

    override fun showOriginal(originalId: Int): Flow<BaltroidResult<IndexOriginalModel>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.showOriginal(originalId)

        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.success(it.asIndexOriginalModel()))
                }
            }

            response.isFailure() -> {
                val throwable = response.error
                emit(BaltroidResult.failure(throwable))
            }

            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    }

    override fun showEpisode(episodeId: Int, type: String): Flow<BaltroidResult<ShowEpisodeModel>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.showEpisode(episodeId)

            when {
                response.isSuccess() -> {
                    response.value.data?.let {
                        val episodeContent = fetchEpisodeFromUrl(it.episode.assetContents.orEmpty())
                        if (type == OriginalType.INTERACTIVE) {
                            try {
                                val xmlContent: XmlContent =
                                    Gson().fromJson(
                                        U.xmlToJson(episodeContent),
                                        XmlContent::class.java
                                    )
                                emit(BaltroidResult.success(it.episode.asShowEpisodeModel(xmlContent = xmlContent)))
                            } catch (e: Exception) {
                                emit(BaltroidResult.failure(e))
                            }
                        } else {
                            emit(BaltroidResult.success(it.episode.asShowEpisodeModel(episodeContent = episodeContent)))
                        }
                    }
                }

                response.isFailure() -> {
                    val throwable = response.error
                    emit(BaltroidResult.failure(throwable))
                }

                else -> error("$MESSAGE_UNHANDLED_STATE $response")
            }
        }

    override suspend fun fetchEpisodeFromUrl(url: String) = networkDataSource.fetchTextFromUrl(url)

    override fun startReadingEpisode(episodeId: Int): Flow<BaltroidResult<Unit>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.startReadingEpisode(episodeId)
        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.success(Unit))
                }
            }

            response.isFailure() -> {
                val throwable = response.error
                emit(BaltroidResult.failure(throwable))
            }

            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    }

    override fun endReadingEpisode(episodeId: Int): Flow<BaltroidResult<Unit>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.endReadingEpisode(episodeId)
        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.success(Unit))
                }
            }

            response.isFailure() -> {
                val throwable = response.error
                emit(BaltroidResult.failure(throwable))
            }

            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    }

}
