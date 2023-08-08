package com.baltroid.model

data class Mall(
    val cityCode: Int,
    val address: String,
    val email: String,
    val floors: List<Int>,
    val location: Pair<Double, Double>,
    val name: String,
    val phone: String,
    val services: List<Int>,
    val web: String
)
