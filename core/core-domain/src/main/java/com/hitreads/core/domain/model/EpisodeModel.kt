package com.hitreads.core.domain.model

import com.baltroid.core.common.model.XmlContent

data class EpisodeModel(
    val id: Int,
    val name: String,
    val price: Int,
    val priceType: String,
    val userPurchase: String?,
    val assetContents: String?,
    val xmlContents: XmlContent?,
    val isLiked: Boolean
)
