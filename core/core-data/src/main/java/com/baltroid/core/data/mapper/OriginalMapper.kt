package com.baltroid.core.data.mapper

import com.baltroid.core.network.model.author.NetworkAuthor
import com.baltroid.core.network.model.originals.NetworkEpisode
import com.baltroid.core.network.model.originals.NetworkOriginal
import com.baltroid.core.network.model.originals.NetworkSeason
import com.baltroid.core.network.model.originals.NetworkTag
import com.baltroid.core.network.model.user.NetworkUserData
import com.hitreads.core.domain.model.AuthorModel
import com.hitreads.core.domain.model.EpisodeModel
import com.hitreads.core.domain.model.OriginalModel
import com.hitreads.core.domain.model.SeasonModel
import com.hitreads.core.domain.model.TagModel
import com.hitreads.core.domain.model.UserDataModel

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
    sort = sort,
    `package` = `package`,
    userData = userData.asUserDataModel(),
    subtitle = subtitle.orEmpty(),
    tags = tags.map { it.asTagModel() },
    episodeCount = episodeCount,
    hashtag = hashtag,
    seasons = seasons?.map { it.asSeasonModel() }.orEmpty(),
    dataCount = dataCount
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
    id, name, icon
)

internal fun NetworkSeason.asSeasonModel() = SeasonModel(
    id = id,
    name = seasonName,
    episodes = episodes.map { it.asEpisodeModel() }
)

internal fun NetworkEpisode.asEpisodeModel(episodeContent: String = "") = EpisodeModel(
    id = id,
    name = episodeName,
    price = price,
    priceType = priceType,
    userPurchase = userPurchase,
    assetContents = episodeContent
)

