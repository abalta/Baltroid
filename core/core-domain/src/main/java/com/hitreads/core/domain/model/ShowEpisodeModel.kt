package com.hitreads.core.domain.model

data class ShowEpisodeModel(
    val id: Int?,
    val originalId: Int?,
    val seasonId: Int?,
    val episodeName: String?,
    val assetContent: String?,
    val price: Int?,
    val priceType: String?,
    val sort: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val isLocked: Boolean?
)
