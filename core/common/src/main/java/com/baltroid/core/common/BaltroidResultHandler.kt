package com.baltroid.core.common

import kotlinx.coroutines.flow.Flow

interface BaltroidResultHandler<T> {
    fun onLoading(block: (T?) -> Unit)
    fun onSuccess(block: (T) -> Unit)
    fun onFailure(block: (ErrorModel) -> Unit)
}

fun <T> BaltroidResult<T>.handle(builder: BaltroidResultHandler<T>.() -> Unit) {
    val resultHandler = object : BaltroidResultHandler<T> {
        override fun onLoading(block: (T?) -> Unit) {
            if (isLoading()) block(value)
        }

        override fun onSuccess(block: (T) -> Unit) {
            if (isSuccess()) block(value)
        }

        override fun onFailure(block: (ErrorModel) -> Unit) {
            if (isFailure()) block(
                if (error is HttpException) {
                    ErrorModel((error as HttpException).statusCode, "Lütfen giriş yapınız.", error.cause)
                } else {
                    ErrorModel(999, "Bir hata oluştu.", error.cause)
                }
            )
        }
    }

    builder(resultHandler)
}

suspend fun <T> Flow<BaltroidResult<T>>.handle(builder: BaltroidResultHandler<T>.() -> Unit) =
    collect { result -> result.handle(builder = builder) }

data class ErrorModel(
    val code: Int,
    val message: String? = null,
    val cause: Throwable? = null
)
