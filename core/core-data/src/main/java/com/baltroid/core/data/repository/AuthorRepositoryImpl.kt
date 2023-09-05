package com.baltroid.core.data.repository

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.hitreads.core.domain.model.AuthorModel
import com.hitreads.core.domain.repository.AuthorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthorRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource
) : AuthorRepository {
    override fun showAuthor(id: Int): Flow<BaltroidResult<AuthorModel>> {
        TODO("Not yet implemented")
    }

}