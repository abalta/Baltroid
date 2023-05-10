package com.baltroid.ui.screens.reading

data class ReadingScreenState(
    val bodyText: String,
    val title: String,
    val subtitle: String,
    val numberOfComments: Int,
    val numberOfViews: Int,
    val numberOfNotification: Int
)
