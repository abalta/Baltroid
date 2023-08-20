package com.baltroid.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.baltroid.core.designsystem.R
import com.google.firebase.storage.StorageReference

@Composable
fun CardMedium(avmName: String, imageLoader: ImageLoader, storageReference: StorageReference, onMallClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .height(254.dp).clickable {
                onMallClick()
            }
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .border(BorderStroke(1.dp, Color(0xFFF3F2F2)), RoundedCornerShape(10.dp))
                .padding(10.dp), painter = rememberAsyncImagePainter(
                model = storageReference,
                imageLoader = imageLoader,
                placeholder = painterResource(id = R.drawable.bg_banner)
            ), contentDescription = "AVM Logo", contentScale = ContentScale.FillWidth
        )
        Subhead(text = avmName, modifier = Modifier.padding(top = 14.dp))
    }
}

/*
@Composable
@Preview
fun previewCardMedium() = CardMedium(avmName = "01 Burda")*/
