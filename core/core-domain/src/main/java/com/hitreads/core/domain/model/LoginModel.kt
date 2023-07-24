package com.hitreads.core.domain.model

data class LoginModel(
    val userId: Int,
    val token: String,
    val username: String,
    val avatar: String,
    val wallet: Int,
    val imgUrl: String
)
