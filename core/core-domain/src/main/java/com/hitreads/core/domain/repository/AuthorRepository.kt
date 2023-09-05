package com.hitreads.core.domain.repository

import com.baltroid.core.common.result.BaltroidResult
import com.hitreads.core.domain.model.AuthorModel
import kotlinx.coroutines.flow.Flow

interface AuthorRepository {
    fun showAuthor(id: Int): Flow<BaltroidResult<AuthorModel>>
}