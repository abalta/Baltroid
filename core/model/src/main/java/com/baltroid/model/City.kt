package com.baltroid.model

data class City(
    val code: Int,
    val name: String,
    val malls: MutableList<Mall>
)