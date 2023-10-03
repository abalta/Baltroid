package com.baltroid.core.network.model.response

import com.baltroid.core.network.model.author.NetworkAuthor
import com.baltroid.core.network.model.episode.NetworkShowEpisode
import com.baltroid.core.network.model.notification.NetworkView
import com.baltroid.core.network.model.originals.IndexNetworkOriginal
import com.baltroid.core.network.model.originals.NetworkCommentOriginal
import com.baltroid.core.network.util.Constants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*@Serializable
data class OriginalResponseDto(
    @SerialName(Constants.Fields.DATA_COUNT)
    val dataCount: Int?,

    @SerialName(Constants.Fields.TAGS)
    val tags: List<TagsWithOriginalsDto>,

    @SerialName(Constants.Fields.ACTIVE_PAGE)
    val totalPages: Int?,

    @SerialName(Constants.Fields.TOTAL_PAGE)
    val totalResults: Int?,
)*/

@Serializable
data class TagWithOriginalsDto(
    @SerialName("tag_name")
    val tagName: String?,
    @SerialName("tag_id")
    val tagId: Int?,
    @SerialName("originals")
    val originals: List<IndexNetworkOriginal>?
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
data class ProfileDto(
    @SerialName("name")
    val name: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("username")
    val userName: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("karma")
    val karma: Int?,
    @SerialName("avatar")
    val avatar: String?,
    @SerialName("is_beta")
    val is_beta: Boolean?,
    @SerialName("gem")
    val gem: Int?,
)

@Serializable
data class EpisodeShowDto(
    @SerialName(Constants.Fields.EPISODE)
    val episode: NetworkShowEpisode,
    @SerialName(Constants.Fields.REPUTATION)
    val reputation: List<Unit>?
)

@Serializable
data class Author(
    @SerialName("avatar")
    val avatar: Avatar?,
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
    @SerialName("path")
    val path: String?
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
    val replyCommentId: Int?,
    @SerialName("Original")
    val original: NetworkCommentOriginal?,
    @SerialName("replies")
    val replies: List<AllCommentsDto>?
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
    val episode: NetworkShowEpisode?,
    @SerialName("original")
    val original: IndexNetworkOriginal?,
    @SerialName("content")
    val content: String?,
    @SerialName("cover")
    val cover: String?
)

@Serializable
data class FavoriteDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("original_id")
    val originalId: Int?,
    @SerialName("season_id")
    val seasonId: Int?,
    @SerialName("author_name")
    val authorName: String?,
    @SerialName("episode_name")
    val episodeName: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("asset_contents")
    val assetContents: String?,
    @SerialName("price")
    val price: Int?,
    @SerialName("price_type")
    val priceType: String?,
    @SerialName("sort")
    val sort: Int?
)

@Serializable
data class AuthorDto(
    @SerialName("author")
    val author: NetworkAuthor?
)

@Serializable
data class AllCommentsDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("content")
    val content: String?,
    @SerialName("Original")
    val original: NetworkCommentOriginal?,
    @SerialName("author")
    val author: Author?,
    @SerialName("is_reply")
    val isReply: Boolean?,
    @SerialName("likes_count")
    val likesCount: Int?,
    @SerialName("replies_count")
    val repliesCount: Int?,
    @SerialName("active_user_like")
    val activeUserLike: Boolean?,
    @SerialName("replies")
    val replies: List<AllCommentsDto>?,
    @SerialName("reply_comment_id")
    val replyCommentId: Int?,
    @SerialName("created_at")
    val createdAt: String?,
)

@Serializable
data class NotificationDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("type")
    val type: String?,
    @SerialName("message")
    val message: String?,
    @SerialName("view")
    val view: NetworkView?,
    @SerialName("detail")
    val detail: String?,//todo needs object
    @SerialName("created_at")
    val created_at: String?
)

@Serializable
data class FavoriteOriginalDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("author_id")
    val authorId: Int?,
    @SerialName("cover")
    val cover: String?,
    @SerialName("banner")
    val banner: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("is_locked")
    val isLocked: Boolean?,
    @SerialName("status")
    val status: Boolean?,
    @SerialName("is_actual")
    val isActual: Boolean?,
    @SerialName("sort")
    val sort: Int?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
    @SerialName("subtitle")
    val subtitle: String?,
    @SerialName("hashtag")
    val hashtag: String?,
    @SerialName("is_new")
    val isNew: Boolean?,
    @SerialName("view_count")
    val viewCount: Int?,
    @SerialName("barcode")
    val barcode: String?
)

@Serializable
data class AvatarDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("price")
    val price: String?,
    @SerialName("unit")
    val unit: String?,
    @SerialName("status")
    val status: Boolean?
)

@Serializable
data class PurchaseDetailDto(
    @SerialName("purchase_detail")
    val purchaseDetailDto: NetworkPurchaseDetail
)

@Serializable
data class NetworkPurchaseDetail(
    @SerialName("id")
    val id: Int?,
    @SerialName("user_id")
    val userId: Int?,
    @SerialName("original_id")
    val originalId: Int?,
    @SerialName("episode_id")
    val episodeId: Int?,
    @SerialName("is_started")
    val isStarted: Boolean?,
    @SerialName("is_completed")
    val isCompleted: Boolean?,
    @SerialName("last_check_point")
    val lastCheckpoint: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?
)

@Serializable
data class AnnouncementDto(
    @SerialName("title")
    val title: String?,
    @SerialName("message")
    val message: String?,
    @SerialName("link")
    val link: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("is_active")
    val isActive: Boolean?
)