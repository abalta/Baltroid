package com.hitreads.core.domain.model

data class ResponseModel<T>(
    val status: Int,
    val message: String,
    val data: T?
)
