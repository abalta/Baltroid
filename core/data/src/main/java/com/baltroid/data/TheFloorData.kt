package com.baltroid.data

data class Participant(
    var id: Int,
    var category: String,
    var areas: MutableList<Area>
)

data class Area(
    val id: Int,
    val x: Int,
    val y: Int
)