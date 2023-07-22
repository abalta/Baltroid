package com.hitreads.core.domain.model

data class AuthorModel(
    val id: Int,
    val name: String,
    val avatar: List<String> = emptyList()
)
