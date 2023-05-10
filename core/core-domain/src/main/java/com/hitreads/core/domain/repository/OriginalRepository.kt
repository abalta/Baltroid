package com.hitreads.core.domain.repository

import androidx.paging.PagingData
import com.hitreads.core.domain.model.OriginalModel
import kotlinx.coroutines.flow.Flow

interface OriginalRepository {

    fun getOriginals(): Flow<PagingData<OriginalModel>>

}