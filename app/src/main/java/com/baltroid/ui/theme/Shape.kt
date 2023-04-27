package com.baltroid.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

val MaterialShapes = androidx.compose.material.Shapes()

data class Shapes(
    val circleShape: RoundedCornerShape = CircleShape,
    val roundedDp4: RoundedCornerShape = RoundedCornerShape(4.dp),
    val roundedDp18: RoundedCornerShape = RoundedCornerShape(18.dp),
    val roundedDp20: RoundedCornerShape = RoundedCornerShape(20.dp)
)

val LocalShapes = staticCompositionLocalOf { Shapes() }

val MaterialTheme.localShapes
    @Composable
    @ReadOnlyComposable
    get() = LocalShapes.current