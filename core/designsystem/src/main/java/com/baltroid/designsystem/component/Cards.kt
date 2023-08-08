package com.baltroid.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.core.designsystem.R

@Composable
fun CardMedium(avmName: String) {
    Column(modifier = Modifier
        .width(200.dp)
        .height(254.dp)) {
        Image(modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(10.dp)), painter = painterResource(id = R.drawable.bg_banner), contentDescription = "AVM Logo", contentScale = ContentScale.FillBounds)
        Subhead(text = avmName, modifier = Modifier.padding(top = 14.dp))
    }
}

@Composable
@Preview
fun previewCardMedium() = CardMedium(avmName = "01 Burda")