package com.baltroid.ui.common

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp

@Composable
fun VerticalSpacer(
    height: Dp,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = modifier.height(height))
}

@Composable
fun VerticalSpacer(
    @DimenRes height: Int,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = modifier.height(dimensionResource(id = height)))
}

@Composable
fun HorizontalSpacer(
    width: Dp,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = modifier.width(width))
}

@Composable
fun HorizontalSpacer(
    @DimenRes width: Int,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = modifier.width(dimensionResource(id = width)))
}