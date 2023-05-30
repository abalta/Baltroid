package com.hitreads.core.model

data class Season(
    val id: Int,
    val name: String,
    val episodes: List<Episode>
)
