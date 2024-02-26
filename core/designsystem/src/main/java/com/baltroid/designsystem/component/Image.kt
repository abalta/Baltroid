package com.baltroid.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.baltroid.core.designsystem.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun MallLogo(painter: String) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .border(BorderStroke(1.dp, Color(0xFFF3F2F2)), RoundedCornerShape(10.dp))
            .padding(16.dp),
        model = painter,
        placeholder = painterResource(id = R.drawable.bg_banner),
        contentDescription = "AVM Logo",
        contentScale = ContentScale.Fit
    )
}

@Composable
fun MallBigLogo(painter: String) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(185.dp)
            .border(BorderStroke(1.dp, Color(0xFFF3F2F2)), RoundedCornerShape(10.dp))
            .padding(16.dp),
        model = painter,
        placeholder = painterResource(id = R.drawable.bg_banner),
        contentDescription = "AVM Logo",
        contentScale = ContentScale.Fit
    )
}

@Composable
fun ShopLogo(
    model: String,
) {
    AsyncImage(
        modifier = Modifier
            .size(110.dp)
            .border(BorderStroke(1.dp, Color(0xFFF3F2F2)), RoundedCornerShape(10.dp))
            .padding(10.dp),
        contentDescription = "Shop Logo",
        contentScale = ContentScale.FillWidth,
        model = model,
        placeholder = painterResource(
            id = R.drawable.bg_banner
        )
    )
}

@Composable
fun MallPhoto(model: String) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        model = model,
        placeholder = painterResource(id = R.drawable.bg_banner),
        contentDescription = "AVM Photo",
        contentScale = ContentScale.FillBounds
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZoomableImage(
    painter: String
) {
    val angle by remember { mutableFloatStateOf(0f) }
    var zoom by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.value
    val screenHeight = configuration.screenHeightDp.dp.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {},
                onDoubleClick = {
                    zoom = if (zoom > 1f) 1f
                    else 3f
                }
            )
    ) {
        AsyncImage(
            model = painter,
            contentDescription = "Floor Plan",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .graphicsLayer(scaleX = zoom, scaleY = zoom, rotationZ = angle)
                .pointerInput(Unit) {
                    detectTransformGestures(
                        onGesture = { _, pan, gestureZoom, _ ->
                            zoom = (zoom * gestureZoom).coerceIn(1F..4F)
                            if (zoom > 1) {
                                val x = (pan.x * zoom)
                                val y = (pan.y * zoom)
                                val angleRad = angle * PI / 180.0

                                offsetX =
                                    (offsetX + (x * cos(angleRad) - y * sin(angleRad)).toFloat()).coerceIn(
                                        -(screenWidth * zoom)..(screenWidth * zoom)
                                    )
                                offsetY =
                                    (offsetY + (x * sin(angleRad) + y * cos(angleRad)).toFloat()).coerceIn(
                                        -(screenHeight * zoom)..(screenHeight * zoom)
                                    )
                            } else {
                                offsetX = 0F
                                offsetY = 0F
                            }
                        }
                    )
                }
                .fillMaxSize()
        )
    }
}