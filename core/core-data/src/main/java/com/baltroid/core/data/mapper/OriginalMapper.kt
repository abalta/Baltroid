package com.baltroid.core.data.mapper

import com.baltroid.core.common.model.XmlContent
import com.baltroid.core.network.model.author.NetworkAuthor
import com.baltroid.core.network.model.originals.NetworkCommentOriginal
import com.baltroid.core.network.model.originals.NetworkCreateCommentResponse
import com.baltroid.core.network.model.originals.NetworkEpisode
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.model.originals.NetworkPackage
import com.baltroid.core.network.model.originals.NetworkSeason
import com.baltroid.core.network.model.originals.NetworkShowEpisode
import com.baltroid.core.network.model.originals.NetworkShowOriginal
import com.baltroid.core.network.model.originals.NetworkTag
import com.baltroid.core.network.model.response.AllCommentsDto
import com.baltroid.core.network.model.response.BookmarkDto
import com.baltroid.core.network.model.response.CommentDto
import com.baltroid.core.network.model.response.FavoriteDto
import com.baltroid.core.network.model.response.WelcomeDto
import com.baltroid.core.network.model.user.NetworkUserData
import com.hitreads.core.domain.model.AllCommentsModel
import com.hitreads.core.domain.model.AuthorModel
import com.hitreads.core.domain.model.BookmarkModel
import com.hitreads.core.domain.model.CommentModel
import com.hitreads.core.domain.model.EpisodeModel
import com.hitreads.core.domain.model.FavoriteModel
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.model.PackageModel
import com.hitreads.core.domain.model.SeasonModel
import com.hitreads.core.domain.model.ShowEpisodeModel
import com.hitreads.core.domain.model.ShowOriginalModel
import com.hitreads.core.domain.model.TagModel
import com.hitreads.core.domain.model.UserDataModel
import com.hitreads.core.domain.model.WelcomeModel

internal fun NetworkOriginal.asOriginalModel() = OriginalModel(
    type = type,
    id = id,
    title = title,
    description = description,
    cover = cover,
    banner = banner,
    author = author.asAuthorModel(),
    isLocked = isLocked,
    isActual = isActual,
    status = status,
    likeCount = likeCount,
    commentCount = commentCount,
    viewCount = viewCount,
    sort = sort,
    `package` = `package`?.asPackageModel(),
    userData = userData.asUserDataModel(),
    subtitle = subtitle.orEmpty(),
    tags = tags.map { it.asTagModel() },
    episodeCount = episodeCount,
    hashtag = hashtag,
    seasons = seasons?.map { it.asSeasonModel() }.orEmpty(),
    isNew = isNew,
    dataCount = dataCount
)

internal fun NetworkCommentOriginal.asOriginalModel() = OriginalModel(
    type = "",
    id = id ?: 0,
    title = title.orEmpty(),
    description = description.orEmpty(),
    cover = cover.orEmpty(),
    banner = banner.orEmpty(),
    author = AuthorModel(authorId ?: 0, "", emptyList()),
    isLocked = isLocked ?: false,
    isActual = false,
    status = false,
    likeCount = 0,
    commentCount = 0,
    viewCount = viewCount ?: 0,
    sort = sort ?: 0,
    `package` = null,
    userData = UserDataModel(false, false),
    subtitle = subtitle.orEmpty(),
    tags = emptyList(),
    episodeCount = 0,
    hashtag = hashtag.orEmpty(),
    seasons = emptyList(),
    isNew = false,
    dataCount = 0
)

internal fun NetworkShowOriginal.asShowOriginalModel() = ShowOriginalModel(
    id = id,
    title = title,
    cover = cover,
    description = description,
    isLocked = isLocked,
    viewCount = viewCount,
    commentsCount = commentsCount,
    updatedAt = updatedAt,
    episodes = episodes.map { it.asShowEpisodeModel() },
    author = author.asAuthorModel()
)

internal fun NetworkShowEpisode.asShowEpisodeModel() = ShowEpisodeModel(
    id = id,
    originalId = originalId,
    seasonId = seasonId,
    episodeName = episodeName,
    assetContent = assetContent,
    price = price,
    priceType = priceType,
    sort = sort,
    createdAt = createdAt,
    updatedAt = updatedAt
)

internal fun NetworkAuthor.asAuthorModel() = AuthorModel(
    id = id,
    name = name
)

internal fun NetworkUserData.asUserDataModel() = UserDataModel(
    isLike = isLike,
    isPurchase = isPurchase
)

internal fun NetworkTag.asTagModel() = TagModel(
    id, name ?: title.orEmpty(), icon
)

internal fun NetworkSeason.asSeasonModel() = SeasonModel(
    id = id,
    name = seasonName,
    episodes = episodes.map { it.asEpisodeModel() }
)

internal fun NetworkEpisode.asEpisodeModel(
    episodeContent: String = "",
    xmlContent: XmlContent? = null
) = EpisodeModel(
    id = id,
    name = episodeName,
    price = price,
    priceType = priceType,
    userPurchase = userPurchase,
    assetContents = episodeContent,
    xmlContents = xmlContent,
    isLiked = isLiked ?: false,
    isFav = isFav ?: false,
    isBookmarked = isBookmarked ?: false,
    favoriteId = favorite?.id ?: 0
)

internal fun NetworkPackage.asPackageModel() = PackageModel(
    id = id ?: 0,
    price = price ?: 0,
    priceType = priceType.orEmpty()
)

internal fun CommentDto.asCommentModel() = CommentModel(
    activeUserLike = activeUserLike ?: false,
    author = AuthorModel(id = 0, name = ""),
    content = content.orEmpty(),
    createdAt = createdAt.orEmpty(),
    id = id ?: 0,
    isReply = isReply ?: false,
    likesCount = likesCount ?: 0,
    repliesCount = repliesCount ?: 0,
    replyCommentId = replyCommentId ?: 0,
    replies = emptyList(),
    original = original?.asOriginalModel()
)

internal fun NetworkCreateCommentResponse.asCommentModel() = CommentModel(
    activeUserLike = false,
    author = AuthorModel(id = 0, name = comment?.user?.username.orEmpty()),
    content = comment?.content.orEmpty(),
    createdAt = comment?.createdAt.orEmpty(),
    id = comment?.id ?: 0,
    isReply = true,
    likesCount = 0,
    repliesCount = 0,
    replyCommentId = 0,
    replies = emptyList(),
    original = null
)

internal fun AllCommentsDto.asAllCommentsModel(): AllCommentsModel = AllCommentsModel(
    id = id,
    content = content,
    original = original?.asOriginalModel(),
    author = AuthorModel(
        0,
        author?.username.orEmpty(),
        author?.avatar?.map { it.url.orEmpty() }.orEmpty()
    ),
    isReply = isReply,
    likesCount = likesCount,
    repliesCount = repliesCount,
    activeUserLike = activeUserLike,
    replies = replies?.map { it.asAllCommentsModel() },
    replyCommentId = replyCommentId,
    createdAt = createdAt
)

internal fun WelcomeDto.asWelcomeModel() = WelcomeModel(
    id = id ?: 0,
    message = message.orEmpty(),
    path = path.orEmpty()
)

internal fun BookmarkDto.asBookmarkModel() = BookmarkModel(
    id = id ?: 0,
    user = user.orEmpty(),
    episode = episode?.asEpisodeModel(),
    original = original?.asOriginalModel(),
    content = content.orEmpty(),
    cover = cover.orEmpty()
)

internal fun FavoriteDto.asFavoriteModel() = FavoriteModel(
    id = id,
    originalId = originalId,
    seasonId = seasonId,
    authorName = authorName,
    episodeName = episodeName,
    createdAt = createdAt,
    updatedAt = updatedAt,
    image = image,
    assetContents = assetContents,
    price = price,
    priceType = priceType,
    sort = sort
)

