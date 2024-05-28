package com.baltroid.designsystem.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.theme.electricVioletColor
import com.mobven.domain.model.LessonModel

@Composable
fun MekikCard(caption: String, title: String, popular: Boolean = false, painter: String = "", onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(start = 13.dp, end = 13.dp, top = 10.dp)
            .fillMaxWidth()
            .height(98.dp)
            .shadow(
                color = Color.Black.copy(0.05f),
                offsetX = 2.dp,
                offsetY = 2.dp,
                borderRadius = 9.dp,
                spread = 4.dp,
                blurRadius = 6.dp
            )
            .clip(RoundedCornerShape(9.dp))
            .background(Color.White)
            .clickable(onClick = onClick, indication = rememberRipple(
                color = MaterialTheme.colorScheme.electricVioletColor
            ), interactionSource = remember {
                MutableInteractionSource()
            })
    ) {
        Row(Modifier.fillMaxSize()) {
            MekikCardImage(modifier = Modifier
                .align(Alignment.CenterVertically), painter = painter)
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, bottom = 10.dp, end = 6.dp, start = 12.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                if(popular) {
                    SmallBold(text = "Popüler", color = MaterialTheme.colorScheme.electricVioletColor)
                }
                Body(text = title, modifier = Modifier.padding(top = 4.dp))
                CaptionSmall(
                    text = caption,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMekikCard() {
    MekikCard(
        "Taner Özdeş",
        "Dijital Dünyanın Antidijital Nefesi Dijital Dünyanın Antidijital Nefesi",
        false
    ) {

    }
}

@Composable
fun MekikHorizontalCard(caption: String, title: String, popular: Boolean = false, painter: String = "", onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(181.dp)
            .height(204.dp)
            .shadow(
                color = Color.Black.copy(0.1f),
                offsetY = 2.5.dp,
                borderRadius = 12.dp,
                blurRadius = 6.dp
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable(
                onClick = onClick,
                indication = rememberRipple(
                    color = MaterialTheme.colorScheme.electricVioletColor
                ),
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
    ) {
        Column(Modifier.fillMaxSize()) {
            MekikHorizontalCardImage(painter = painter)
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, bottom = 10.dp, end = 12.dp, start = 12.dp),
                verticalArrangement = Arrangement.Top
            ) {
                if (popular) {
                    SmallBold(text = "Popüler", color = MaterialTheme.colorScheme.electricVioletColor)
                }
                Body(
                    text = title, modifier = Modifier
                        .padding(top = 4.dp)
                        .weight(1f)
                )
                Caption(
                    text = caption,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMekikHorizontalCard() {
    MekikHorizontalCard("Taner Özdeş", "Dijital Dünyanın Antidijital Nefesi", true) {

    }
}

@Composable
fun MekikCardDouble(
    captionTop: String,
    captionBottom: String,
    title: String,
    category: String = "",
    painter: String = "",
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 13.dp, end = 13.dp, top = 10.dp)
            .fillMaxWidth()
            .height(98.dp)
            .shadow(
                color = Color.Black.copy(0.05f),
                offsetX = 2.dp,
                offsetY = 2.dp,
                borderRadius = 9.dp,
                spread = 4.dp,
                blurRadius = 6.dp
            )
            .clip(RoundedCornerShape(9.dp))
            .background(Color.White)
            .clickable(onClick = onClick, indication = rememberRipple(
                color = MaterialTheme.colorScheme.electricVioletColor
            ), interactionSource = remember {
                MutableInteractionSource()
            })
    ) {
        Row(Modifier.fillMaxSize()) {
            AsyncImage(
                model = painter,
                contentDescription = "thumbnail",
                placeholder = painterResource(id = R.drawable.logo),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 10.dp, bottom = 10.dp, start = 6.dp)
                    .width(117.dp)
                    .clip(RoundedCornerShape(9.dp))
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, bottom = 10.dp, end = 6.dp, start = 12.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                SmallBold(
                    text = category,
                    color = MaterialTheme.colorScheme.electricVioletColor,
                    modifier = Modifier.weight(1f)
                )
                Body(text = title, modifier = Modifier.padding(top = 4.dp))
                CaptionSmall(
                    text = captionTop,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 4.dp)
                )
                CaptionSmall(
                    text = captionBottom,
                    color = Color.Black.copy(alpha = 0.5f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMekikCardDoubleCaption() {
    MekikCardDouble("CCIM Institute", "1 Eğitim", "Zeynep Begüm Kocaçal", "Popüler") {

    }
}

@Composable
fun MekikPagerItem() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
    ) {
        Image(
            modifier = Modifier
                .height(96.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 9.dp, topEnd = 9.dp)),
            painter = painterResource(id = R.drawable.sample_pager_image),
            contentScale = ContentScale.FillWidth,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(8.dp))
        SubtitleText("Kariyerinin en doğru seçimi")
        Spacer(modifier = Modifier.height(10.dp))
        SmallText(
            text = "Pazarlama ve hizmet sektöründe onlarca akademi ve yüzlerce eğitim seçeneği ile mekik.org kariyerin için yaptığın en iyi seçim olacak.",
            maxLines = 3
        )
    }
}

@Preview
@Composable
fun PreviewMekikPagerItem() {
    MekikPagerItem()
}

@Composable
fun ExpandableCard(title: String, lessons: List<LessonModel>) {
    var expanded by remember { mutableStateOf(false) }
    var angle by remember { mutableFloatStateOf(0f) }
    val rotation = remember { Animatable(angle) }
    LaunchedEffect(expanded) {
        if (expanded) {
            // Infinite repeatable rotation when is playing
            rotation.animateTo(
                targetValue = 90f,
                animationSpec = tween(
                    durationMillis = 300
                )
            ) {
                angle = value
            }
        } else {
            // Slow down rotation on pause
            rotation.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 300
                )
            ) {
                angle = value
            }
        }
    }
    Box(
        modifier = Modifier
            .padding(13.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(
                color = Color.Black.copy(0.05f),
                offsetX = 2.dp,
                offsetY = 2.dp,
                borderRadius = 9.dp,
                spread = 4.dp,
                blurRadius = 6.dp
            )
            .clip(RoundedCornerShape(9.dp))
            .background(Color.White)
            .clickable(onClick = {
                expanded = !expanded
                angle = if (expanded) 90f else 0f
            }, indication = rememberRipple(
                color = MaterialTheme.colorScheme.electricVioletColor
            ), interactionSource = remember {
                MutableInteractionSource()
            })
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300
                )
            )
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CaptionMedium(
                    text = title,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_arrow), modifier = Modifier
                        .padding(end = 32.dp, start = 8.dp)
                        .rotate(rotation.value), contentDescription = "menu_arrow"
                )
            }
            if (expanded) {
                lessons.forEach {
                    Column(Modifier.clickable(
                        onClick = {

                        }, indication = rememberRipple(
                            color = MaterialTheme.colorScheme.electricVioletColor
                        ), interactionSource = remember {
                            MutableInteractionSource()
                        }
                    )) {
                        HorizontalDivider(
                            Modifier.padding(horizontal = 16.dp),
                            thickness = 0.5.dp,
                            color = Color.Black.copy(0.3f)
                        )
                        MediumBold(
                            text = it.name,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_duration),
                                contentDescription = "time",
                                modifier = Modifier.size(20.dp)
                            )
                            MediumText(
                                text = it.length,
                                modifier = Modifier.padding(start = 8.dp, end = 16.dp)
                            )
                            if (it.isPromo) {
                                BadgeText(text = "Ücretsiz")
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_play),
                                contentDescription = "play",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewExpandableCard() {
    ExpandableCard("Expandable Card", emptyList())
}

@Composable
fun ProfileInfoCard(icon: Int, info: String) {
    Row(
        modifier = Modifier
            .padding(top = 16.dp, start = 13.dp, end = 13.dp)
            .fillMaxWidth()
            .height(54.dp)
            .shadow(
                color = Color.Black.copy(0.05f),
                offsetX = 2.dp,
                offsetY = 2.dp,
                borderRadius = 9.dp,
                spread = 4.dp,
                blurRadius = 6.dp
            )
            .clip(RoundedCornerShape(9.dp))
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = icon), modifier = Modifier.padding(start = 24.dp), contentDescription = "info_icon")
        MediumBigText(
            text = info,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewProfileInfoCard() {
    ProfileInfoCard(R.drawable.ic_mail, "c******@******.***")
}