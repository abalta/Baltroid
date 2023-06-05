package com.hitreads.core.domain.model

data class EpisodeModel(
    val id: Int,
    val name: String,
    val price: Int,
    val priceType: String,
    val userPurchase: String?,
    val assetContents: String?
)
