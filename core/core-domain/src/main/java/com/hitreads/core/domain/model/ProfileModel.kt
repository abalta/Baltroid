package com.hitreads.core.domain.model

data class ProfileModel(
    val name: String = "",
    val userName: String = "",
    val email: String = "",
    val karma: Int = 0,
    val avatar: String = "",
    val is_beta: Boolean = false,
    val gem: Int = 0,
    val id: Int = 0,
)
