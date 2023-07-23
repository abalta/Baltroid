package com.hitreads.core.domain.model

data class ShowEpisodeModel(
    val id: Int,
    val seasonId: Int,
    val episodeName: String,
    val price: Int,
    val priceType: String,
    val userPurchase: String?,
    val sort: Int?
)
