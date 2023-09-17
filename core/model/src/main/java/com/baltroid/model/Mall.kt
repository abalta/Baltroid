package com.baltroid.model

data class Mall(
    val id: String,
    val cityCode: Int,
    val address: String,
    val email: String,
    val floors: List<Int>,
    val location: Pair<Double, Double>,
    val name: String,
    val phone: String,
    val services: MutableMap<Int, Service>,
    val web: String,
    val logo: String,
    val photos: List<String>,
    val rating: String,
    val reviews: String,
    val district: String
)
