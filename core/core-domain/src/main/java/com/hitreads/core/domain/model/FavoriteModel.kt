package com.hitreads.core.domain.model

data class FavoriteModel(
    val id: Int?,
    val originalId: Int?,
    val seasonId: Int?,
    val authorName: String?,
    val episodeName: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val image: String?,
    val assetContents: String?,
    val price: Int?,
    val priceType: String?,
    val sort: Int?
)
