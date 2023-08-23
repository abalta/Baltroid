package com.hitreads.core.ui.mapper

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
import com.hitreads.core.domain.model.TagsWithOriginalsModel
import com.hitreads.core.domain.model.UserDataModel
import com.hitreads.core.domain.model.WelcomeModel
import com.hitreads.core.model.Author
import com.hitreads.core.model.Bookmark
import com.hitreads.core.model.Comment
import com.hitreads.core.model.Episode
import com.hitreads.core.model.Favorite
import com.hitreads.core.model.Original
import com.hitreads.core.model.Package
import com.hitreads.core.model.Season
import com.hitreads.core.model.ShowEpisode
import com.hitreads.core.model.ShowOriginal
import com.hitreads.core.model.Tag
import com.hitreads.core.model.TagsWithOriginals
import com.hitreads.core.model.UserData
import com.hitreads.core.model.Welcome

fun OriginalModel.asOriginal() = Original(
    author = author.asAuthor(),
    banner = banner,
    cover = cover,
    description = description,
    id = id,
    isActual = isActual,
    isLocked = isLocked,
    likeCount = likeCount,
    commentCount = commentCount,
    viewCount = viewCount,
    `package` = `package`?.asPackage(),
    sort = sort,
    status = status,
    title = title,
    type = type,
    userData = userData.asUserData(),
    hashtag = hashtag,
    tags = tags.map { it.asTag() },
    subtitle = subtitle,
    episodeCount = episodeCount,
    seasons = seasons.map { it.asSeason() },
    isNew = isNew,
    barcode = barcode
)

fun TagsWithOriginalsModel.asTagsWithOriginals() = TagsWithOriginals(
    tagName = tagName, tagId = tagId, originals = originals?.map { it.asOriginal() }

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
    author.asAuthor()
)

fun ShowEpisodeModel.asShowEpisode() = ShowEpisode(
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
    isLocked = isLocked ?: false
)

fun PackageModel.asPackage() = Package(
    id, price, priceType
)

fun AuthorModel.asAuthor() = Author(
    id, name
)

fun UserDataModel.asUserData() = UserData(isLike, isPurchase)

fun TagModel.asTag() = Tag(id, name, icon)

fun SeasonModel.asSeason() = Season(id, name, episodes.map { it.asEpisode() })

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
    authorName = author.name,
    hashtag = "",
    createdAt = createdAt,
    isLiked = activeUserLike,
    isReply = isReply,
    replies = replies.map { it.asComment() },
    episode = "",
    original = original?.asOriginal()
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
    original = original?.asOriginal()
)

fun WelcomeModel.asWelcome() = Welcome(id, message, path)

fun BookmarkModel.asBookmark() = Bookmark(
    id = id,
    user = user,
    episode = episode?.asEpisode(),
    original = original?.asOriginal(),
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