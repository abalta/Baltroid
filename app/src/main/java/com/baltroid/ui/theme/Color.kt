package com.baltroid.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val Black_alpha09 = Color(0xE6000000)
val Black_alpha05 = Color(0x80000000)
val Black_alpha03 = Color(0x4D000000)
val Black_alpha02 = Color(0x33000000)
val White = Color(0xFFFFFFFF)
val White_alpha09 = Color(0xE6FFFFFF)
val White_alpha08 = Color(0xCCFFFFFF)
val White_alpha07 = Color(0xB3FFFFFF)
val White_alpha06 = Color(0x99FFFFFF)
val White_alpha05 = Color(0x80FFFFFF)
val White_alpha04 = Color(0x66FFFFFF)
val White_alpha03 = Color(0x4DFFFFFF)
val White_alpha02 = Color(0x33FFFFFF)
val White_black = Color(0xFF808080)
val Grey = Color(0xFFA5A4A3)
val Purple = Color(0xFF8D1697)
val Purple_alpha08 = Color(0xCC8D1697)
val Purple_Dark = Color(0xCC6F0D80)
val Pink = Color(0xFFED116D)
val Orange = Color(0xFFFF7A00)
val Yellow_100 = Color(0xFFFFDA1A)
val Yellow_200 = Color(0xFFEFCD19)
val Gold = Color(0xFFB39912)
val White_bb = Color(0xFFBBBBBB)
val Transparent = Color.Transparent
val Green = Color(0xFF34C759)

data class Colors(
    val black: Color = Black,
    val black_alpha09: Color = Black_alpha09,
    val black_alpha05: Color = Black_alpha05,
    val black_alpha03: Color = Black_alpha03,
    val black_alpha02: Color = Black_alpha02,
    val white: Color = White,
    val white_alpha09: Color = White_alpha09,
    val white_alpha08: Color = White_alpha08,
    val white_alpha07: Color = White_alpha07,
    val white_alpha06: Color = White_alpha06,
    val white_alpha05: Color = White_alpha05,
    val white_alpha04: Color = White_alpha04,
    val white_alpha03: Color = White_alpha03,
    val white_alpha02: Color = White_alpha02,
    val grey: Color = Grey,
    val purple: Color = Purple,
    val purple_alpha08: Color = Purple_alpha08,
    val purple_dark: Color = Purple_Dark,
    val pink: Color = Pink,
    val orange: Color = Orange,
    val yellow: Color = Yellow_100,
    val textSelectionColor: Color = Yellow_200,
    val gold: Color = Gold,
    val transparent: Color = Transparent,
    val white_black: Color = White_black,
    val white_bb: Color = White_bb,
    val green: Color = Green
)

val LocalColors = staticCompositionLocalOf { Colors() }

val MaterialTheme.localColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current