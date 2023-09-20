package com.hitreads.core.model

data class Author(
    val id: Int?,
    val authorName: String?,
    val image: String?,
    val comments: List<Comment>?,
    val originals: List<IndexOriginal>?,
    val isFavorite: Boolean?
)
