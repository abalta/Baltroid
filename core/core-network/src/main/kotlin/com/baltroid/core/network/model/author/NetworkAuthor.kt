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
    val comments: NetworkAuthorComment?,
    @SerialName("originals")
    val originals: NetworkAuthorOriginal?
)

@Serializable
data class NetworkAuthorOriginal(
    @SerialName("count")
    val count: Int?,
    @SerialName("posts")
    val posts: List<IndexNetworkOriginal>?
)

@Serializable
data class NetworkAuthorComment(
    @SerialName("count")
    val count: Int?,
    @SerialName("posts")
    val posts: List<NetworkAuthorCommentItem>?
)

@Serializable
data class NetworkAuthorCommentItem(
    @SerialName("id")
    val id: Int?,
    @SerialName("content")
    val content: String?,
    @SerialName("Author")
    val author: NetworkAuthor?,
    @SerialName("likes_count")
    val likesCount: Int?,
    @SerialName("replies_count")
    val repliesCount: Int?,
    @SerialName("active_user_like")
    val activeUserLike: Boolean?,
    @SerialName("is_reply")
    val isReply: Boolean?,
    @SerialName("reply_comment_id")
    val replyCommentId: Int?,
    @SerialName("created_at")
    val createdAt: String?,
)
