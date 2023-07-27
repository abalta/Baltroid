package com.baltroid.core.network.model.originals

import com.baltroid.core.network.model.response.Author
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCreateCommentResponse(
    @SerialName("comment")
    val comment: NetworkCreateComment?
)

@Serializable
data class NetworkCreateComment(
    @SerialName("id")
    val id: Int?,
    @SerialName("content")
    val content: String?,
    @SerialName("user")
    val user: Author?,
    @SerialName("created_at")
    val createdAt: String?,
)
