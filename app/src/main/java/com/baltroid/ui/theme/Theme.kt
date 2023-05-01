package com.baltroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val DarkColorPalette = darkColors()

private val LightColorPalette = lightColors()

@Composable
fun HitReadsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val textStyles = if (darkTheme) {
        DarkThemeTextStyles
    } else {
        LightThemeTextStyles
    }

    val dimensions = Dimensions()
    val staticColors = Colors()
    val shapes = Shapes()

    CompositionLocalProvider(
        LocalTextStyles provides textStyles,
        LocalDimensions provides dimensions,
        LocalColors provides staticColors,
        LocalShapes provides shapes
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = MaterialShapes,
            content = content
        )
    }
}