package com.baltroid.core.data.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.network.model.HitReadsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal fun <T, R> Flow<List<T>>.listMap(
    transform: suspend (T) -> R
) = map { list -> list.map { transform(it) } }

internal fun <T, R> Flow<BaltroidResult<HitReadsResponse<T>>>.dtoMap(
    transform: suspend (T) -> R
) = map { transform }

internal fun <T : Any, R : Any> Flow<PagingData<T>>.pagingMap(
    transform: (T) -> R
): Flow<PagingData<R>> = map { it.map(transform) }
