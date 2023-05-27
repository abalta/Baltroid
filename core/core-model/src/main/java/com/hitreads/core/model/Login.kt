package com.hitreads.core.model

data class Login(
    val avatar: String,
    val token: String,
    val userId: Int,
    val username: String,
    val wallet: Int
)