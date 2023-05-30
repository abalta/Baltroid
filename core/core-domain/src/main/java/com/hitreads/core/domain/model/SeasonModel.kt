package com.hitreads.core.domain.model

data class SeasonModel(
    val id: Int,
    val name: String,
    val episodes: List<EpisodeModel>
)
