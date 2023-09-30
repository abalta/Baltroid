package com.hitreads.core.model

data class IndexOriginal(
    val indexAuthor: IndexAuthor = IndexAuthor(-1, ""),
    val banner: String = "",
    val cover: String = "",
    val description: String = "",
    val isBulkPurchasable: Boolean = false,
    val id: Int = -1,
    val isActual: Boolean = false,
    val isLocked: Boolean = false,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val viewCount: Int = 0,
    val indexPackage: IndexPackage = IndexPackage(-1, -1, ""),
    val sort: Int = 0,
    val status: Boolean = false,
    val title: String = "",
    val type: String = "",
    val indexUserData: IndexUserData = IndexUserData(isFav = false, isPurchase = false),
    val indexTags: List<IndexTag> = emptyList(),
    val hashtag: String = "",
    val subtitle: String = "",
    val episodeCount: Int = 0,
    val isNew: Boolean = false,
    val barcode: String = "",
    val continueReadingEpisode: ShowEpisode? = null,
    val episodes: List<ShowEpisode> = emptyList()
)
