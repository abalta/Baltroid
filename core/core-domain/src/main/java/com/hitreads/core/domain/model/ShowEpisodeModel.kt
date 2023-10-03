package com.hitreads.core.domain.model

import com.baltroid.core.common.model.XmlContent

data class ShowEpisodeModel(
    val id: Int?,
    val episodeName: String?,
    val price: Int?,
    val episodeSort: Int?,
    val priceType: String?,
    val sort: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val originalId: Int?,
    val seasonId: Int?,
    val isPurchase: Boolean?,
    val isReadable: Boolean?,
    val isLocked: Boolean?,
    val isLastEpisode: Boolean?,
    val original: IndexOriginalModel?,
    val bundleAssets: List<InteractiveBundleAssetModel>?,
    val assetContents: String?,
    val xmlContents: XmlContent?,
    val episodeContent: String?,
    val nextEpisodeId: Int?
)
