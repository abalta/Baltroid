package com.baltroid.core.data.mapper

import com.baltroid.core.common.model.XmlContent
import com.baltroid.core.network.model.author.IndexNetworkAuthor
import com.baltroid.core.network.model.author.NetworkAuthor
import com.baltroid.core.network.model.author.NetworkAuthorCommentItem
import com.baltroid.core.network.model.episode.InteractiveNetworkBundleAsset
import com.baltroid.core.network.model.episode.NetworkShowEpisode
import com.baltroid.core.network.model.notification.NetworkView
import com.baltroid.core.network.model.originals.IndexNetworkContinueReadingEpisode
import com.baltroid.core.network.model.originals.IndexNetworkOriginal
import com.baltroid.core.network.model.originals.IndexNetworkPackage
import com.baltroid.core.network.model.originals.IndexNetworkTag
import com.baltroid.core.network.model.originals.NetworkCommentOriginal
import com.baltroid.core.network.model.originals.NetworkCreateCommentResponse
import com.baltroid.core.network.model.originals.ShowOriginalDto
import com.baltroid.core.network.model.response.AllCommentsDto
import com.baltroid.core.network.model.response.AnnouncementDto
import com.baltroid.core.network.model.response.AuthorDto
import com.baltroid.core.network.model.response.AvatarDto
import com.baltroid.core.network.model.response.BookmarkDto
import com.baltroid.core.network.model.response.CommentDto
import com.baltroid.core.network.model.response.FavoriteDto
import com.baltroid.core.network.model.response.FavoriteOriginalDto
import com.baltroid.core.network.model.response.NotificationDto
import com.baltroid.core.network.model.response.TagWithOriginalsDto
import com.baltroid.core.network.model.response.WelcomeDto
import com.baltroid.core.network.model.user.IndexUserData
import com.hitreads.core.domain.model.AllCommentsModel
import com.hitreads.core.domain.model.AnnouncementModel
import com.hitreads.core.domain.model.AuthorModel
import com.hitreads.core.domain.model.AvatarModel
import com.hitreads.core.domain.model.BookmarkModel
import com.hitreads.core.domain.model.CommentModel
import com.hitreads.core.domain.model.EpisodeModel
import com.hitreads.core.domain.model.FavoriteModel
import com.hitreads.core.domain.model.FavoriteOriginalModel
import com.hitreads.core.domain.model.IndexAuthorModel
import com.hitreads.core.domain.model.IndexContinueReadingEpisodeModel
import com.hitreads.core.domain.model.IndexOriginalModel
import com.hitreads.core.domain.model.IndexPackageModel
import com.hitreads.core.domain.model.IndexTagModel
import com.hitreads.core.domain.model.IndexUserDataModel
import com.hitreads.core.domain.model.InteractiveBundleAssetModel
import com.hitreads.core.domain.model.NetworkAuthorCommentModel
import com.hitreads.core.domain.model.NetworkViewModel
import com.hitreads.core.domain.model.NotificationModel
import com.hitreads.core.domain.model.ShowEpisodeModel
import com.hitreads.core.domain.model.ShowOriginalModel
import com.hitreads.core.domain.model.TagsWithOriginalsModel
import com.hitreads.core.domain.model.WelcomeModel

internal fun IndexNetworkOriginal.asIndexOriginalModel() = IndexOriginalModel(type = type,
    id = id,
    title = title,
    description = description,
    cover = cover,
    banner = banner,
    author = author?.asIndexAuthorModel(),
    isLocked = isLocked,
    isActual = isActual,
    status = status,
    likeCount = likeCount,
    commentCount = commentCount,
    viewCount = viewCount,
    sort = sort,
    `package` = `package`?.asIndexPackageModel(),
    userData = userData?.asIndexUserDataModel(),
    subtitle = subtitle,
    tags = tags?.map { it.asIndexTagModel() },
    episodeCount = episodeCount,
    hashtag = hashtag,
    isNew = isNew,
    barcode = barcode,
    isBulkPurchasable = isBulkPurchasable,
    continueReadingEpisode = continueReadingEpisode?.asIndexContinueReadingEpisodeModel(),
    episodes = episodes?.map { it.asShowEpisodeModelIndex() })

internal fun InteractiveNetworkBundleAsset.asInteractiveBundleAssetsModel() =
    InteractiveBundleAssetModel(
        type = type, typeId = typeId, path = path, isActive = isActive
    )

internal fun NetworkAuthorCommentItem.asNetworkAuthorCommentModel() = NetworkAuthorCommentModel(
    id = id,
    content = content,
    author = author?.asAuthorModel(),
    likesCount = likesCount,
    repliesCount = repliesCount,
    activeUserLike = activeUserLike,
    isReply = isReply,
    replyCommentId = replyCommentId,
    createdAt = createdAt
)

internal fun NetworkCommentOriginal.asIndexOriginalModel() = IndexOriginalModel(
    type = "",
    id = id ?: 0,
    title = title.orEmpty(),
    description = description.orEmpty(),
    cover = cover.orEmpty(),
    banner = banner.orEmpty(),
    author = IndexAuthorModel(authorId ?: 0, "", img = null),
    isLocked = isLocked ?: false,
    isActual = false,
    status = false,
    likeCount = 0,
    commentCount = 0,
    viewCount = viewCount ?: 0,
    sort = sort ?: 0,
    `package` = null,
    userData = IndexUserDataModel(false, false),
    subtitle = subtitle.orEmpty(),
    tags = emptyList(),
    episodeCount = 0,
    hashtag = hashtag.orEmpty(),
    isNew = false,
    barcode = "",
    continueReadingEpisode = null,
    isBulkPurchasable = null,
    episodes = null
)

internal fun ShowOriginalDto.asShowOriginalModel() = ShowOriginalModel(
    id = id,
    title = title,
    cover = cover,
    description = description,
    isLocked = isLocked,
    viewCount = viewCount,
    commentsCount = commentsCount,
    updatedAt = updatedAt,
    episodes = episodes.map { it.asIndexContinueReadingEpisodeModel() },
    author = author.asIndexAuthorModel(),
    comments = comments
)

internal fun IndexNetworkContinueReadingEpisode.asIndexContinueReadingEpisodeModel() =
    IndexContinueReadingEpisodeModel(
        id = id,
        originalId = originalId,
        seasonId = seasonId,
        episodeName = episodeName,
        assetContent = assetContent,
        price = price,
        priceType = priceType,
        sort = sort,
        createdAt = createdAt,
        updatedAt = updatedAt,
        nextEpisodeId = nextEpisodeId,
        isLocked = isLocked,
        isReadable = isReadable,
        episodeSort = episodeSort
    )

internal fun IndexNetworkAuthor.asIndexAuthorModel() = IndexAuthorModel(
    id = id, name = name, img = null
)

internal fun IndexUserData.asIndexUserDataModel() = IndexUserDataModel(
    isFav = isFav, isPurchase = isPurchase
)

internal fun IndexNetworkTag.asIndexTagModel() = IndexTagModel(
    id, name ?: title.orEmpty(), icon
)

internal fun NetworkShowEpisode.asShowEpisodeModelIndex() = ShowEpisodeModel(
    id = id ?: -1,
    episodeName = episodeName.orEmpty(),
    price = price ?: -1,
    episodeSort = episodeSort ?: 0,
    priceType = priceType.orEmpty(),
    sort = sort ?: 0,
    createdAt = createdAt.orEmpty(),
    updatedAt = updatedAt.orEmpty(),
    originalId = originalId ?: -1,
    seasonId = seasonId ?: -1,
    isLocked = isLocked ?: false,
    isLastEpisode = isLastEpisode ?: false,
    original = null,
    bundleAssets = bundleAssets?.map { it.asInteractiveBundleAssetsModel() },
    assetContents = assetContents,
    xmlContents = null,
    nextEpisodeId = nextEpisodeId,
    isReadable = isReadable,
    episodeContent = null
)

internal fun NetworkShowEpisode.asShowEpisodeModel(
    episodeContent: String = "", xmlContent: XmlContent? = null
) = ShowEpisodeModel(
    id = id ?: -1,
    episodeName = episodeName.orEmpty(),
    price = price ?: -1,
    episodeSort = episodeSort ?: 0,
    priceType = priceType.orEmpty(),
    sort = sort ?: 0,
    isReadable = isReadable ?: false,
    createdAt = createdAt.orEmpty(),
    updatedAt = updatedAt.orEmpty(),
    originalId = originalId ?: -1,
    seasonId = seasonId ?: -1,
    isLocked = isLocked ?: false,
    isLastEpisode = isLastEpisode ?: false,
    original = original?.asIndexOriginalModel(),
    bundleAssets = bundleAssets?.map { it.asInteractiveBundleAssetsModel() },
    assetContents = assetContents,
    xmlContents = xmlContent,
    nextEpisodeId = nextEpisodeId,
    episodeContent = episodeContent
)

internal fun IndexNetworkPackage.asIndexPackageModel() = IndexPackageModel(
    id = id ?: 0, price = price ?: 0, priceType = priceType.orEmpty()
)

internal fun CommentDto.asCommentModel() = CommentModel(
    activeUserLike = activeUserLike ?: false,
    author = IndexAuthorModel(id = 0, name = "", img = author?.avatar?.path),
    content = content.orEmpty(),
    createdAt = createdAt.orEmpty(),
    id = id ?: 0,
    isReply = isReply ?: false,
    likesCount = likesCount ?: 0,
    repliesCount = repliesCount ?: 0,
    replyCommentId = replyCommentId ?: 0,
    replies = replies?.map { it.asAllCommentsModel() },
    original = original?.asIndexOriginalModel()
)

internal fun NetworkCreateCommentResponse.asCommentModel() = CommentModel(
    activeUserLike = false,
    author = IndexAuthorModel(
        id = 0,
        name = comment?.user?.username.orEmpty(),
        img = comment?.user?.avatar?.path
    ),
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
    original = original?.asIndexOriginalModel(),
    author = IndexAuthorModel(
        0, author?.username.orEmpty(), img = author?.avatar?.path
    ),
    isReply = isReply,
    likesCount = likesCount,
    repliesCount = repliesCount,
    activeUserLike = activeUserLike,
    replies = replies?.map { it.asAllCommentsModel() },
    replyCommentId = replyCommentId,
    createdAt = createdAt
)

internal fun NetworkView.asNetworkViewModel() = NetworkViewModel(
    status = status, viewedAt = viewedAt
)

internal fun NotificationDto.asNotificationModel(): NotificationModel = NotificationModel(
    id = id,
    type = type,
    message = message,
    view = view?.asNetworkViewModel(),
    detail = detail,
    created_at = created_at

)

internal fun AuthorDto.asAuthorModel(): AuthorModel = AuthorModel(
    id = author?.id,
    authorName = author?.authorName,
    image = author?.image,
    comments = author?.comments?.posts?.map { it.asNetworkAuthorCommentModel() },
    originals = author?.originals?.posts?.map { it.asIndexOriginalModel() },
    isFavorite = author?.isFavorite
)

internal fun NetworkAuthor.asAuthorModel(): AuthorModel = AuthorModel(
    id = id,
    authorName = authorName,
    image = image,
    comments = emptyList(),
    originals = emptyList(),
    isFavorite = isFavorite,
)

internal fun WelcomeDto.asWelcomeModel() = WelcomeModel(
    id = id ?: 0, message = message.orEmpty(), path = path.orEmpty()
)

internal fun AnnouncementDto.asAnnouncementModel() = AnnouncementModel(
    title = title,
    message = message,
    link = link,
    image = image,
    isActive = isActive
)

internal fun BookmarkDto.asBookmarkModel() = BookmarkModel(
    id = id ?: 0,
    user = user.orEmpty(),
    episode = EpisodeModel(
        id = 2859,
        name = "Larry Rodgers",
        price = 7409,
        priceType = "mus",
        userPurchase = null,
        assetContents = null,
        xmlContents = null,
        isLiked = false,
        isBookmarked = false,
        isFav = false,
        favoriteId = 6342
    ),
    original = original?.asIndexOriginalModel(),
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

internal fun FavoriteOriginalDto.asFavoriteOriginalModel() = FavoriteOriginalModel(
    id = id,
    title = title,
    description = description,
    authorId = authorId,
    cover = cover,
    banner = banner,
    type = type,
    isLocked = isLocked,
    status = status,
    isActual = isActual,
    sort = sort,
    createdAt = createdAt,
    updatedAt = updatedAt,
    subtitle = subtitle,
    hashtag = hashtag,
    isNew = isNew,
    viewCount = viewCount,
    barcode = barcode
)

internal fun AvatarDto.asAvatarModel() = AvatarModel(
    id = id,
    name = name,
    url = url,
    price = price,
    unit = unit,
    status = status
)

internal fun TagWithOriginalsDto.asTagsWithOriginalsModel() =
    TagsWithOriginalsModel(tagName = tagName,
        tagId = tagId,
        indexOriginalModels = originals?.map { it.asIndexOriginalModel() })
