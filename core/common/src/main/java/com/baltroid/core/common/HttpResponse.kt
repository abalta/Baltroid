package com.baltroid.core.common

internal interface HttpResponse {
    val statusCode: Int
    val statusMessage: String?
    val url: String?
}
