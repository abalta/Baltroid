package com.hitreads.core.domain.model

data class TagsWithOriginalsModel(
    val tagName: String?,
    val tagId: Int?,
    val indexOriginalModels: List<IndexOriginalModel>?
)
