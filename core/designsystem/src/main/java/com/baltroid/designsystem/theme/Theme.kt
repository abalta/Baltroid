package com.baltroid.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MqTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}