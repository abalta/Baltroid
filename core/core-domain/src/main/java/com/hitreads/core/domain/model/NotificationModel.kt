package com.hitreads.core.domain.model

data class NotificationModel(
    val id: Int?,
    val type: String?,
    val message: String?,
    val view: NetworkViewModel?,
    val detail: String?,//todo needs object
    val created_at: String?
)
