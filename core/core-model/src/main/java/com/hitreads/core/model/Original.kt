package com.hitreads.core.model

data class Original(
    val author: Author,
    val banner: String,
    val cover: String,
    val description: String,
    val id: Int,
    val isActual: Boolean,
    val isLocked: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val viewCount: Int,
    val `package`: Package?,
    val sort: Int,
    val status: Boolean,
    val title: String,
    val type: String,
    val userData: UserData,
    val tags: List<Tag>,
    val hashtag: String,
    val subtitle: String,
    val episodeCount: Int,
    val seasons: List<Season>,
    val isNew: Boolean,
    val dataCount: Int
)
