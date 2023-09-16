package com.hitreads.core.ui.mapper

import com.hitreads.core.domain.model.AllCommentsModel
import com.hitreads.core.domain.model.AuthorModel
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
import com.hitreads.core.domain.model.ShowEpisodeModel
import com.hitreads.core.domain.model.ShowOriginalModel
import com.hitreads.core.domain.model.TagsWithOriginalsModel
import com.hitreads.core.domain.model.WelcomeModel
import com.hitreads.core.model.Author
import com.hitreads.core.model.Bookmark
import com.hitreads.core.model.Comment
import com.hitreads.core.model.Episode
import com.hitreads.core.model.Favorite
import com.hitreads.core.model.FavoriteOriginal
import com.hitreads.core.model.IndexAuthor
import com.hitreads.core.model.IndexOriginal
import com.hitreads.core.model.IndexPackage
import com.hitreads.core.model.IndexTag
import com.hitreads.core.model.IndexUserData
import com.hitreads.core.model.InteractiveBundleAsset
import com.hitreads.core.model.ShowEpisode
import com.hitreads.core.model.ShowOriginal
import com.hitreads.core.model.TagsWithOriginals
import com.hitreads.core.model.Welcome

fun IndexOriginalModel.asIndexOriginal() = IndexOriginal(
    indexAuthor = author?.asIndexAuthor() ?: IndexAuthor(-1, ""),
    banner = banner.orEmpty(),
    cover = cover.orEmpty(),
    description = description.orEmpty(),
    id = id ?: -1,
    isActual = isActual ?: false,
    isLocked = isLocked ?: false,
    likeCount = likeCount ?: 0,
    commentCount = commentCount ?: 0,
    viewCount = viewCount ?: 0,
    indexPackage = `package`?.asIndexPackage() ?: IndexPackage(-1, -1, ""),
    sort = sort ?: 0,
    status = status ?: false,
    title = title.orEmpty(),
    type = type.orEmpty(),
    indexUserData = userData?.asIndexUserData() ?: IndexUserData(
        isFav = false,
        isPurchase = false
    ),
    hashtag = hashtag.orEmpty(),
    indexTags = tags?.map { it.asIndexTag() } ?: emptyList(),
    subtitle = subtitle.orEmpty(),
    episodeCount = episodeCount ?: 0,
    isNew = isNew ?: false,
    barcode = barcode.orEmpty(),
    continueReadingEpisode = continueReadingEpisode?.asShowEpisode(),
    episodes = episodes?.map { it.asShowEpisodeIndex() }.orEmpty()
)

fun IndexContinueReadingEpisodeModel.asShowEpisode() = ShowEpisode(
    id = id ?: -1,
    episodeName = episodeName.orEmpty(),
    price = price ?: -1,
    episodeSort = 0,
    priceType = priceType.orEmpty(),
    sort = sort ?: 0,
    createdAt = createdAt.orEmpty(),
    updatedAt = updatedAt.orEmpty(),
    originalId = originalId ?: -1,
    seasonId = seasonId ?: -1,
    isLocked = isLocked ?: false,
    isLastEpisode = false,
    original = null,
    bundleAssets = null,
    assetContents = assetContent,
    xmlContents = null,
    episodeContent = null,
    nextEpisodeId = nextEpisodeId ?: -1
)

fun InteractiveBundleAssetModel.asInteractiveBundleAssets() = InteractiveBundleAsset(
    type = type.orEmpty(),
    typeId = typeId ?: -1,
    path = path.orEmpty(),
    isActive = isActive ?: false

)

fun TagsWithOriginalsModel.asTagsWithOriginals() = TagsWithOriginals(
    tagName = tagName,
    tagId = tagId,
    indexOriginals = indexOriginalModels?.map { it.asIndexOriginal() }
)

fun ShowOriginalModel.asShowOriginal() = ShowOriginal(
    id,
    title,
    cover,
    description,
    isLocked,
    viewCount,
    commentsCount,
    updatedAt,
    episodes.sortedBy { it.sort }.map { it.asShowEpisode() },
    author.asIndexAuthor()
)

fun ShowEpisodeModel.asShowEpisode() = ShowEpisode(
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
    original = original?.asIndexOriginal(),
    bundleAssets = bundleAssets?.map { it.asInteractiveBundleAssets() },
    assetContents = assetContents,
    xmlContents = xmlContents,
    nextEpisodeId = nextEpisodeId ?: -1,
    episodeContent = episodeContent?.replace("\n", "")?.replace("\\", "")
)

fun ShowEpisodeModel.asShowEpisodeIndex() = ShowEpisode(
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
    bundleAssets = bundleAssets?.map { it.asInteractiveBundleAssets() },
    assetContents = assetContents,
    xmlContents = xmlContents,
    nextEpisodeId = nextEpisodeId ?: -1,
    episodeContent = episodeContent?.replace("\\", "")
)

fun IndexPackageModel.asIndexPackage() = IndexPackage(
    id, price, priceType
)

fun IndexAuthorModel.asIndexAuthor() = IndexAuthor(
    id ?: -1, name.orEmpty()
)

fun IndexUserDataModel.asIndexUserData() = IndexUserData(isFav ?: false, isPurchase ?: false)

fun IndexTagModel.asIndexTag() = IndexTag(id ?: -1, name.orEmpty(), icon.orEmpty())

fun EpisodeModel.asEpisode() =
    Episode(
        id = id,
        name = name,
        price = price,
        priceType = priceType,
        userPurchase = userPurchase.orEmpty(),
        content = assetContents?.replace("\\", ""),
        xmlContent = xmlContents,
        isLiked = isLiked,
        isBookmarked = isBookmarked,
        isFav = isFav,
        favoriteId = favoriteId
    )

fun CommentModel.asComment() = Comment(
    id = id,
    imgUrl = "",
    content = content,
    repliesCount = repliesCount,
    authorName = author.name.orEmpty(),
    hashtag = "",
    createdAt = createdAt,
    isLiked = activeUserLike,
    isReply = isReply,
    replies = replies?.map { it.asComment() }.orEmpty(),
    episode = "",
    indexOriginal = original?.asIndexOriginal()
)

fun AllCommentsModel.asComment(): Comment = Comment(
    id = id ?: -1,
    imgUrl = "",
    content = content.orEmpty(),
    repliesCount = repliesCount ?: 0,
    authorName = author?.name.orEmpty(),
    hashtag = "",
    createdAt = createdAt.orEmpty(),
    isLiked = activeUserLike ?: false,
    isReply = isReply ?: false,
    replies = replies?.map { it.asComment() }.orEmpty(),
    episode = "",
    indexOriginal = original?.asIndexOriginal()
)

fun WelcomeModel.asWelcome() = Welcome(id, message, path)

fun BookmarkModel.asBookmark() = Bookmark(
    id = id,
    user = user,
    episode = episode?.asEpisode(),
    indexOriginal = original?.asIndexOriginal(),
    content = content,
    cover = cover
)

fun FavoriteModel.asFavorite() = Favorite(
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

fun NetworkAuthorCommentModel.asComment() = Comment(
    id = id ?: -1,
    imgUrl = author?.image.orEmpty(),
    content = content.orEmpty(),
    repliesCount = repliesCount ?: 0,
    authorName = author?.authorName.orEmpty(),
    hashtag = "",
    createdAt = createdAt.orEmpty(),
    isLiked = false,
    isReply = isReply ?: false,
    replies = emptyList(),
    episode = "",
    indexOriginal = null,
    isExpanded = false

)

fun AuthorModel.asAuthor() = Author(
    id = id,
    authorName = authorName,
    image = image,
    comments = comments?.map { it.asComment() },
    originals = originals?.map { it.asIndexOriginal() }
)

fun FavoriteOriginalModel.asFavoriteOriginal() = FavoriteOriginal(
    id = id ?: -1,
    title = title.orEmpty(),
    description = description.orEmpty(),
    authorId = authorId ?: -1,
    cover = cover.orEmpty(),
    banner = banner.orEmpty(),
    type = type.orEmpty(),
    isLocked = isLocked ?: false,
    status = status ?: false,
    isActual = isActual ?: false,
    sort = sort ?: 0,
    createdAt = createdAt.orEmpty(),
    updatedAt = updatedAt.orEmpty(),
    subtitle = subtitle.orEmpty(),
    hashtag = hashtag.orEmpty(),
    isNew = isNew ?: false,
    viewCount = viewCount ?: 0,
    barcode = barcode.orEmpty()
)