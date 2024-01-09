package com.baltroid.model

import androidx.compose.runtime.Immutable

@Immutable
data class Mall(
    val id: String,
    val cityCode: Int,
    val address: String,
    val email: String,
    val floors: List<Floor>,
    val location: Pair<Double, Double>,
    val name: String,
    val phone: String,
    val services: MutableMap<Int, Service>,
    val web: String,
    val logo: String,
    val photos: List<String>,
    val rating: String,
    val reviews: String,
    val district: String,
    val shops: MutableMap<Int, Shop>
)

data class Floor(
    val no: Int,
    val plan: String
)
