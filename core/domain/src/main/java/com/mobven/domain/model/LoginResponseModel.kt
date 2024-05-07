package com.mobven.domain.model

data class LoginResponseModel(
    val customerId: Int,
    val token: String,
    val email: String
)
