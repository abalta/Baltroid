package com.baltroid.ui.screens.home.detail

data class HomeDetailScreenState(
    val id: Int,
    val summary: String,
    val author: String,
    val firstName: String,
    val secondName: String,
    val episodeSize: Int,
    val numberOfNotification: Int,
    val numberOfViews: Int,
    val numberOfComments: Int,
    val genres: List<String>
)
