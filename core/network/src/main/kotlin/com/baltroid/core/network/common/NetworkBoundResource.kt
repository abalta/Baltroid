package com.baltroid.core.network.common

import com.baltroid.core.common.BaltroidResult
import com.baltroid.core.common.isFailure
import com.baltroid.core.common.isSuccess
import com.baltroid.core.network.util.MESSAGE_UNHANDLED_STATE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

inline fun <ResultType> networkBoundResource(
    crossinline fetch: suspend () -> BaltroidResult<ResultType>,
): Flow<BaltroidResult<ResultType>> = flow {
    emit(BaltroidResult.loading())

        val response = fetch()

        when {
            response.isSuccess() -> {
                emit(BaltroidResult.success(response.value))
            }
            response.isFailure() -> {
                val throwable = response.error
                emit(BaltroidResult.failure(throwable))
            }
            else -> error("$MESSAGE_UNHANDLED_STATE $response")
        }

}
