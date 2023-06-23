package com.baltroid.core.data.repository

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asLoginModel
import com.baltroid.core.data.mapper.asTagModel
import com.baltroid.core.datastore.PreferencesDataStoreDataSource
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import com.hitreads.core.domain.model.LoginModel
import com.hitreads.core.domain.model.TagModel
import com.hitreads.core.domain.repository.AuthRepository
import com.hitreads.core.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource,
) : TagRepository {

    override fun getTagList(): Flow<BaltroidResult<List<TagModel>>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.getTags()

        when {
            response.isSuccess() -> {
                response.value.data?.let { list ->
                    emit(BaltroidResult.success(list.map {
                        it.asTagModel()
                    }))
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