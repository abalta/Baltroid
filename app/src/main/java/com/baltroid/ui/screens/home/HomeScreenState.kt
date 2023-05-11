package com.baltroid.ui.screens.home

data class HomeScreenState(
    val author: String,
    val firstName: String,
    val secondName: String,
    val summary: String,
    val genres: List<String>,
    val imgUrls: List<String>,
    val numberOfNotification: Int,
    val numberOfStory: Int,
    val numberOfViews: Int,
    val numberOfComments: Int,
    val numberOfFavorites: Int,
    val episodeSize: Int,
    val selectedFilters: List<Int>,
)