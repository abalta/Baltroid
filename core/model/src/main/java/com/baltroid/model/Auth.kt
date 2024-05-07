package com.baltroid.model

data class LoginModel(
    val customerId: Int,
    val email: String,
    val token: String
)