package com.hitreads.core.domain.repository

import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.CommentModel
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getComments(type: String, id: Int): Flow<BaltroidResult<List<CommentModel>>>
    fun likeComment(commentId: Int): Flow<BaltroidResult<Unit?>>
    fun unlikeComment(commentId: Int): Flow<BaltroidResult<Unit?>>
}