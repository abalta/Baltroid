package com.hitreads.core.domain.repository

import androidx.paging.PagingData
import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.CommentModel
import com.hitreads.core.domain.model.EpisodeModel
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.model.OriginalType
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getComments(type: String, id: Int): Flow<BaltroidResult<List<CommentModel>>>
}