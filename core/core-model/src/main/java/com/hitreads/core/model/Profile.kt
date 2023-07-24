package com.hitreads.core.model

data class Profile(
    val userName: String,
    val email: String,
    val karma: Int,
    val avatar: String,
    val is_beta: Boolean,
    val gem: Int,
    val imgUrl: String
)
