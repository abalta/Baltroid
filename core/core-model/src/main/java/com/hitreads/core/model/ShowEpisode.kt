package com.hitreads.core.model

data class ShowEpisode(
    val id: Int,
    val seasonId: Int,
    val episodeName: String,
    val price: Int,
    val priceType: String,
    val userPurchase: String?,
    val sort: Int?,
)
