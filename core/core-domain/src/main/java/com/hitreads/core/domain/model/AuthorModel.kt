package com.hitreads.core.domain.model

data class AuthorModel(
    val id: Int?,
    val authorName: String?,
    val image: String?,
    val comments: List<Unit>?,
    val originals: List<IndexOriginalModel>?
)
