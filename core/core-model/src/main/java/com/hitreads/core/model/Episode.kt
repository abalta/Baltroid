package com.hitreads.core.model

import com.baltroid.core.common.model.XmlContent

data class Episode(
    val id: Int,
    val name: String,
    val price: Int,
    val priceType: String,
    val userPurchase: String,
    val content: String?,
    val xmlContent: XmlContent?,
    val isLiked: Boolean
)
