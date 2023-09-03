package com.baltroid.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun CardMedium(avmId: String, avmName: String, painter: Painter, onMallClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .height(254.dp)
            .clickable {
                onMallClick(avmId)
            }
    ) {
        MallLogo(painter)
        Subhead(text = avmName, modifier = Modifier.padding(top = 14.dp))
    }
}

/*
@Composable
@Preview
fun previewCardMedium() = CardMedium(avmName = "01 Burda")*/
