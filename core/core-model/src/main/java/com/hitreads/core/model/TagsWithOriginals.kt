package com.hitreads.core.model

data class TagsWithOriginals(
    val tagName: String?,
    val tagId: Int?,
    val originals: List<Original>?
)