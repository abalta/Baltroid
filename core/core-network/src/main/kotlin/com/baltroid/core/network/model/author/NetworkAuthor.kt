package com.baltroid.core.network.model.author

import com.baltroid.core.network.model.originals.IndexNetworkOriginal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkAuthor(
    @SerialName("id")
    val id: Int?,
    @SerialName("author_name")
    val authorName: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("comments")
    val comments: List<Unit>?,
    @SerialName("originals")
    val originals: List<NetworkAuthorOriginal>?
)

@Serializable
data class NetworkAuthorOriginal(
    @SerialName("count")
    val count: Int?,
    @SerialName("posts")
    val posts: List<IndexNetworkOriginal>?
)
