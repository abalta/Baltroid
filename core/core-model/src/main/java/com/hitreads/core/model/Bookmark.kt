package com.hitreads.core.model

data class Bookmark(
    val id: Int,
    val user: String,
    val episode: String,
    val original: Original?,
    val content: String,
    val cover: String
)
