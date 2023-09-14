package com.hitreads.core.domain.model

data class AuthorModel(
    val id: Int?,
    val authorName: String?,
    val image: String?,
    val comments: List<NetworkAuthorCommentModel>?,
    val originals: List<IndexOriginalModel>?
)
