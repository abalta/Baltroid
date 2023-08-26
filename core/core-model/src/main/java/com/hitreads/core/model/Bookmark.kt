package com.hitreads.core.model

data class Bookmark(
    val id: Int,
    val user: String,
    val episode: Episode?,
    val indexOriginal: IndexOriginal?,
    val content: String,
    val cover: String
)
