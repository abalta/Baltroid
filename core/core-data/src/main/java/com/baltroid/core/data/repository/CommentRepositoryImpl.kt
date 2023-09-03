package com.baltroid.core.data.repository

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.data.mapper.asCommentModel
import com.baltroid.core.network.common.networkBoundResource
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import com.hitreads.core.domain.model.CommentModel
import com.hitreads.core.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource
) : CommentRepository {

    override fun getComments(type: String, id: Int): Flow<BaltroidResult<List<CommentModel>>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.getComments(type, id)

            when {
                response.isSuccess() -> {
                    response.value.data?.let {
                        emit(BaltroidResult.success(it.map { commentDto ->
                            commentDto.asCommentModel()
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

    override fun getAllComments(
        type: String
    ): Flow<BaltroidResult<List<CommentModel>>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.getAllComments(type)

            when {
                response.isSuccess() -> {
                    response.value.data?.let {
                        emit(BaltroidResult.success(it.map { dto ->
                            dto.asCommentModel()
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

    override fun createComment(
        type: String,
        id: Int,
        content: String,
        responseId: Int?
    ): Flow<BaltroidResult<CommentModel>> = flow {
        emit(BaltroidResult.loading())
        val response = networkDataSource.createComment(type, id, content, responseId)

        when {
            response.isSuccess() -> {
                response.value.data?.let {
                    emit(BaltroidResult.success(it.asCommentModel()))
                }
            }

            response.isFailure() -> {
                val throwable = response.error
                emit(BaltroidResult.failure(throwable))
            }

            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    }

    override fun getCommentsByMe(): Flow<BaltroidResult<List<CommentModel>>> =
        flow {
            emit(BaltroidResult.loading())
            val response = networkDataSource.getCommentsByMe()

            when {
                response.isSuccess() -> {
                    response.value.data?.let {
                        emit(BaltroidResult.success(it.map { dto ->
                            dto.asCommentModel()
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

    override fun likeComment(commentId: Int): Flow<BaltroidResult<Unit?>> = networkBoundResource {
        networkDataSource.likeComment(commentId)
    }

    override fun unlikeComment(commentId: Int): Flow<BaltroidResult<Unit?>> =
        networkBoundResource {
            networkDataSource.unlikeComment(commentId)
        }

}
