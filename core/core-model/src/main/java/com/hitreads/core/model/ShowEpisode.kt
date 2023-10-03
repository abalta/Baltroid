package com.hitreads.core.model

import com.baltroid.core.common.model.XmlContent

data class ShowEpisode(
    val id: Int,
    val episodeName: String,
    val price: Int,
    val episodeSort: Int,
    val priceType: String,
    val sort: Int,
    val createdAt: String,
    val isPurchase: Boolean,
    val updatedAt: String,
    val originalId: Int,
    val seasonId: Int,
    val isLocked: Boolean,
    val isLastEpisode: Boolean,
    val original: IndexOriginal?,
    val bundleAssets: List<InteractiveBundleAsset>?,
    val assetContents: String?,
    val xmlContents: XmlContent?,
    val episodeContent: String?,
    val nextEpisodeId: Int,
    val isReadable: Boolean
)
