package com.baltroid.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.baltroid.core.data.paging.OriginalsPagingSource
import com.baltroid.core.data.util.defaultPagingConfig
import com.baltroid.core.network.source.HitReadsNetworkDataSource
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.repository.OriginalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OriginalRepositoryImpl @Inject constructor(
    private val networkDataSource: HitReadsNetworkDataSource
) : OriginalRepository {

    override fun getOriginals(): Flow<PagingData<OriginalModel>> = Pager(
        config = defaultPagingConfig,
        pagingSourceFactory = {
            OriginalsPagingSource(
                networkDataSource = networkDataSource,
            )
        }
    ).flow

}
