package com.baltroid.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.baltroid.core.designsystem.R

@Composable
fun MekikCardImage(modifier: Modifier = Modifier, painter: String) {
    AsyncImage(
        modifier = modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 6.dp)
            .width(117.dp)
            .clip(RoundedCornerShape(9.dp)),
        model = painter,
        placeholder = painterResource(id = R.drawable.logo),
        contentDescription = "Image",
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun MekikHorizontalCardImage(modifier: Modifier = Modifier, painter: String) {
    AsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .height(99.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.FillWidth,
        model = painter,
        placeholder = painterResource(id = R.drawable.logo),
        contentDescription = "Image"
    )
}