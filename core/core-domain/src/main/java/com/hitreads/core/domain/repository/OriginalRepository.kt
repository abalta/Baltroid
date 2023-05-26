package com.hitreads.core.domain.repository

import androidx.paging.PagingData
import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.OriginalModel
import kotlinx.coroutines.flow.Flow

interface OriginalRepository {
    fun getOriginals(): Flow<PagingData<OriginalModel>>

    fun likeOriginal(originalId: Int): Flow<BaltroidResult<Unit?>>

}