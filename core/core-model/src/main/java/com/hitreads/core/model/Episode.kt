package com.hitreads.core.model

data class Episode(
    val id: Int,
    val name: String,
    val price: Int,
    val priceType: String,
    val userPurchase: String,
    val content: String
)
