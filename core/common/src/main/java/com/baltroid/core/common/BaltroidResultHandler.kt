package com.baltroid.core.common

import kotlinx.coroutines.flow.Flow

interface BaltroidResultHandler<T> {
    fun onLoading(block: (T?) -> Unit)
    fun onSuccess(block: (T) -> Unit)
    fun onFailure(block: (Throwable) -> Unit)
}

fun <T> BaltroidResult<T>.handle(builder: BaltroidResultHandler<T>.() -> Unit) {
    val resultHandler = object : BaltroidResultHandler<T> {
        override fun onLoading(block: (T?) -> Unit) {
            if (isLoading()) block(value)
        }

        override fun onSuccess(block: (T) -> Unit) {
            if (isSuccess()) block(value)
        }

        override fun onFailure(block: (Throwable) -> Unit) {
            if (isFailure()) block(error)
        }
    }

    builder(resultHandler)
}

suspend fun <T> Flow<BaltroidResult<T>>.handle(builder: BaltroidResultHandler<T>.() -> Unit) =
    collect { result -> result.handle(builder = builder) }
