package com.baltroid.ui.screens.onboarding

import androidx.annotation.DrawableRes

data class OnboardingScreenState(
    @DrawableRes val imageResId: Int,
    val messageText: String
)