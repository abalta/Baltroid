package com.baltroid.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val Black_alpha09 = Color(0xE6000000)
val Black_alpha03 = Color(0x4D000000)
val Black_alpha02 = Color(0x33000000)
val White = Color(0xFFFFFFFF)
val White_alpha09 = Color(0xE6FFFFFF)
val White_alpha07 = Color(0xB3FFFFFF)
val White_alpha08 = Color(0xCCFFFFFF)
val White_alpha05 = Color(0x80FFFFFF)
val White_alpha04 = Color(0x66FFFFFF)

data class Colors(
    val black: Color = Black,
    val black_alpha09: Color = Black_alpha09,
    val black_alpha03: Color = Black_alpha03,
    val black_alpha02: Color = Black_alpha02,
    val white: Color = White,
    val white_alpha09: Color = White_alpha09,
    val white_alpha08: Color = White_alpha08,
    val white_alpha07: Color = White_alpha07,
    val white_alpha04: Color = White_alpha04,
    val white_alpha05: Color = White_alpha05
)

val LocalColors = staticCompositionLocalOf { Colors() }

val MaterialTheme.localColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current