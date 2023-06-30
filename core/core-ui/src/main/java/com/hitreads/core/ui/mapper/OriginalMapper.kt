package com.hitreads.core.ui.mapper

import com.hitreads.core.domain.model.AuthorModel
import com.hitreads.core.domain.model.CommentModel
import com.hitreads.core.domain.model.EpisodeModel
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.model.PackageModel
import com.hitreads.core.domain.model.SeasonModel
import com.hitreads.core.domain.model.TagModel
import com.hitreads.core.domain.model.UserDataModel
import com.hitreads.core.model.Author
import com.hitreads.core.model.Comment
import com.hitreads.core.model.Episode
import com.hitreads.core.model.Original
import com.hitreads.core.model.Package
import com.hitreads.core.model.Season
import com.hitreads.core.model.Tag
import com.hitreads.core.model.UserData

fun OriginalModel.asOriginal() = Original(
    author = author.asAuthor(),
    banner = banner,
    cover = cover,
    description = description,
    id = id,
    isActual = isActual,
    isLocked = isLocked,
    likeCount = likeCount,
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
    dataCount = dataCount
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

fun EpisodeModel.asEpisode() = Episode(id, name, price, priceType, userPurchase.orEmpty(), assetContents, xmlContents)

fun CommentModel.asComment() = Comment(id, content, repliesCount)