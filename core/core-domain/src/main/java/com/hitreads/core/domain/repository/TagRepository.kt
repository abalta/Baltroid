package com.hitreads.core.domain.repository

import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.TagModel
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getTagList(): Flow<BaltroidResult<List<TagModel>>>
}