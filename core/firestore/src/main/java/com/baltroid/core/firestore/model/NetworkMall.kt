package com.baltroid.core.firestore.model

import com.google.firebase.firestore.GeoPoint

data class NetworkMall(
    val cityCode: Int? = null,
    val address: String? = null,
    val email: String? = null,
    val floors: List<Int>? = null,
    val location: GeoPoint? = null,
    val name: String? = null,
    val phone: String? = null,
    val services: List<Int>? = null,
    val web: String? = null,
    val logo: String? = null
)
