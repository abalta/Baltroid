package com.baltroid.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun SimpleImage(
    @DrawableRes imgResId: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = imgResId),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun CroppedImage(
    @DrawableRes imgResId: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = imgResId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun SimpleIcon(
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        painter = painterResource(id = iconResId),
        contentDescription = null,
        tint = tint,
        modifier = modifier
    )
}