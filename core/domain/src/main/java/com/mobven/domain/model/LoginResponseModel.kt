package com.mobven.domain.model

data class LoginResponseModel(
    val customerId: Int,
    val token: String,
    val email: String
)

data class ProfileModel(
    val id: Int,
    val email: String,
    val avatar: String,
    val name: String,
    val phone: String,
    val about: String
)
