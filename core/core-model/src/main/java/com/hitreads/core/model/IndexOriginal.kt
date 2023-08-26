package com.hitreads.core.model

data class IndexOriginal(
    val indexAuthor: IndexAuthor,
    val banner: String,
    val cover: String,
    val description: String,
    val id: Int,
    val isActual: Boolean,
    val isLocked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val viewCount: Int,
    val indexPackage: IndexPackage,
    val sort: Int,
    val status: Boolean,
    val title: String,
    val type: String,
    val indexUserData: IndexUserData,
    val indexTags: List<IndexTag>,
    val hashtag: String,
    val subtitle: String,
    val episodeCount: Int,
    val isNew: Boolean,
    val barcode: String,
    val continueReadingEpisode: ShowEpisode?
)
