package com.baltroid.core.network.model.response

import com.baltroid.core.network.model.originals.NetworkEpisode
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OriginalResponseDto(
    @SerialName(Constants.Fields.DATA_COUNT)
    val dataCount: Int,

    @SerialName(Constants.Fields.ORIGINALS)
    val originals: List<NetworkOriginal>,

    @SerialName(Constants.Fields.ACTIVE_PAGE)
    val totalPages: Int,

    @SerialName(Constants.Fields.TOTAL_PAGE)
    val totalResults: Int,
)

@Serializable
data class LoginDto(
    @SerialName(Constants.Fields.USER_ID)
    val userId: Int,

    @SerialName(Constants.Fields.TOKEN)
    val token: String,

    @SerialName(Constants.Fields.USERNAME)
    val username: String,

    @SerialName(Constants.Fields.AVATAR)
    val avatar: String,

    @SerialName(Constants.Fields.GEM)
    val wallet: Int,
)

@Serializable
data class EpisodeResponseDto(
    @SerialName(Constants.Fields.EPISODE)
    val episode: NetworkEpisode,

    @SerialName(Constants.Fields.REPUTATION)
    val reputation: List<Unit>?
)

@Serializable
data class Author(
    @SerialName("avatar")
    val avatar: List<Avatar>?,
    @SerialName("username")
    val username: String?
)

@Serializable
data class Avatar(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("price")
    val price: String?,
    @SerialName("status")
    val status: Boolean?,
    @SerialName("unit")
    val unit: String?,
    @SerialName("url")
    val url: String?
)

@Serializable
data class CommentDto(
    @SerialName("active_user_like")
    val activeUserLike: Boolean?,
    @SerialName("author")
    val author: Author?,
    @SerialName("content")
    val content: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("is_reply")
    val isReply: Boolean?,
    @SerialName("likes_count")
    val likesCount: Int?,
    @SerialName("replies_count")
    val repliesCount: Int?,
    @SerialName("reply_comment_id")
    val replyCommentId: Int?
)

@Serializable
data class WelcomeDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("message")
    val message: String?,
    @SerialName("path")
    val path: String?
)

@Serializable
data class BookmarkDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("user")
    val user: String?,
    @SerialName("episode")
    val episode: String?,
    @SerialName("original")
    val original: NetworkOriginal?,
    @SerialName("content")
    val content: String?,
    @SerialName("cover")
    val cover: String?
)

