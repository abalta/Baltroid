package com.hitreads.core.domain.model

data class ProfileModel(
    val name: String,
    val userName: String,
    val email: String,
    val karma: Int,
    val avatar: String,
    val is_beta: Boolean,
    val gem: Int,
)
