package com.baltroid.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun MallLogo(painter: Painter) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .border(BorderStroke(1.dp, Color(0xFFF3F2F2)), RoundedCornerShape(10.dp))
            .padding(10.dp), painter = painter, contentDescription = "AVM Logo", contentScale = ContentScale.FillWidth
    )
}

@Composable
fun MallPhoto(painter: Painter) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp), painter = painter, contentDescription = "AVM Photo", contentScale = ContentScale.FillBounds
    )
}