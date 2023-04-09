package com.baltroid.core.network.common

import com.baltroid.core.common.result.BaltroidResult
import com.baltroid.core.common.result.isFailure
import com.baltroid.core.common.result.isSuccess
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> BaltroidResult<RequestType>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<BaltroidResult<ResultType>> = flow {
    emit(BaltroidResult.loading())
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(BaltroidResult.Loading(data))
        val response = fetch()

        when {
            response.isSuccess() -> {
                saveFetchResult(response.value)
                query().map { BaltroidResult.success(it) }
            }
            response.isFailure() -> {
                val throwable = response.error
                query().map { BaltroidResult.failure(throwable, it) }
            }
            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }
    } else {
        query().map { BaltroidResult.success(it) }
    }

    emitAll(flow)
}
