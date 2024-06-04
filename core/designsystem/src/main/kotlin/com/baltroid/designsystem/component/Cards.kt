package com.baltroid.designsystem.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.baltroid.core.designsystem.R
import com.baltroid.designsystem.theme.badgeStyle
import com.baltroid.designsystem.theme.electricVioletColor
import com.baltroid.designsystem.theme.goldenTainoiColor
import com.baltroid.designsystem.theme.montserratFamily
import com.gowtham.ratingbar.RatingBar
import com.mobven.domain.model.LessonModel


@Composable
fun MekikCard(
    caption: String,
    title: String,
    popular: Boolean = false,
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
            MekikCardImage(
                modifier = Modifier.align(Alignment.CenterVertically), painter = painter
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, bottom = 10.dp, end = 6.dp, start = 12.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                if (popular) {
                    SmallBold(
                        text = "Popüler", color = MaterialTheme.colorScheme.electricVioletColor
                    )
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
fun MekikHorizontalCard(
    caption: String,
    title: String,
    popular: Boolean = false,
    painter: String = "",
    onClick: () -> Unit
) {
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
            .clickable(onClick = onClick, indication = rememberRipple(
                color = MaterialTheme.colorScheme.electricVioletColor
            ), interactionSource = remember {
                MutableInteractionSource()
            })
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
                    SmallBold(
                        text = "Popüler", color = MaterialTheme.colorScheme.electricVioletColor
                    )
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
                targetValue = 90f, animationSpec = tween(
                    durationMillis = 300
                )
            ) {
                angle = value
            }
        } else {
            // Slow down rotation on pause
            rotation.animateTo(
                targetValue = 0f, animationSpec = tween(
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
                    text = title, modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_arrow),
                    modifier = Modifier
                        .padding(end = 32.dp, start = 8.dp)
                        .rotate(rotation.value),
                    contentDescription = "menu_arrow"
                )
            }
            if (expanded) {
                lessons.forEach {
                    Column(
                        Modifier.clickable(onClick = {

                        }, indication = rememberRipple(
                            color = MaterialTheme.colorScheme.electricVioletColor
                        ), interactionSource = remember {
                            MutableInteractionSource()
                        })
                    ) {
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
        Icon(
            painter = painterResource(id = icon),
            modifier = Modifier.padding(start = 24.dp),
            contentDescription = "info_icon"
        )
        MediumBigText(
            text = info, modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewProfileInfoCard() {
    ProfileInfoCard(R.drawable.ic_mail, "c******@******.***")
}

@Composable
fun RatingCard(
    rating: String, ratingPercent: List<Int>
) {
    val totalRating = ratingPercent.size
    var oneStartPercent by remember { mutableIntStateOf(0) }
    var twoStartPercent by remember { mutableIntStateOf(0) }
    var threeStartPercent by remember { mutableIntStateOf(0) }
    var fourStartPercent by remember { mutableIntStateOf(0) }
    var fiveStartPercent by remember { mutableIntStateOf(0) }

    if (totalRating != 0) {
        oneStartPercent = ratingPercent.count {
            it == 1
        } * 100 / totalRating

        twoStartPercent = ratingPercent.count {
            it == 2
        } * 100 / totalRating

        threeStartPercent = ratingPercent.count {
            it == 3
        } * 100 / totalRating

        fourStartPercent = ratingPercent.count {
            it == 4
        } * 100 / totalRating

        fiveStartPercent = ratingPercent.count {
            it == 5
        } * 100 / totalRating
    }

    val percentList = mutableListOf(
        fiveStartPercent,
        fourStartPercent,
        threeStartPercent,
        twoStartPercent,
        oneStartPercent
    )

    ConstraintLayout(
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
    ) {
        val (avg, ratings, percent, progress) = createRefs()

        createHorizontalChain(avg, ratings, percent, progress, chainStyle = ChainStyle.Spread)

        RatingText(
            rating,
            modifier = Modifier
                .padding(start = 18.dp, end = 18.dp)
                .constrainAs(avg) {
                    centerVerticallyTo(parent)
                })
        Image(
            painter = painterResource(id = R.drawable.ratings),
            modifier = Modifier.constrainAs(ratings) {
                width = Dimension.wrapContent
                centerVerticallyTo(parent)
            },
            contentDescription = "ratings"
        )
        Column(verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.constrainAs(percent) {
                height = Dimension.fillToConstraints
                top.linkTo(ratings.top)
                bottom.linkTo(ratings.bottom)
            }) {
            percentList.forEach {
                Text(
                    text = "% $it",
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    style = MaterialTheme.typography.badgeStyle,
                )
            }
        }
        Column(verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(end = 24.dp)
                .constrainAs(progress) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(ratings.top)
                    bottom.linkTo(ratings.bottom)
                }) {
            percentList.forEach {
                LinearProgressIndicator(
                    progress = {
                        it.toFloat() / 100
                    },
                    color = MaterialTheme.colorScheme.goldenTainoiColor,
                    trackColor = Color(0xFFF5F5F5),
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewRatingCard() {
    RatingCard(
        "4.5", mutableListOf(50, 50, 0, 0, 0)
    )
}

@Composable
fun CommentCard(name: String, rating: String, comment: String, avatar: String) {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 13.dp, end = 13.dp, top = 10.dp)
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
    ) {
        val (img, user, star, rateRef, commentRef) = createRefs()
        AsyncImage(
            model = avatar,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.electricVioletColor, CircleShape)
                .constrainAs(img) {
                    top.linkTo(parent.top, 16.dp)
                    start.linkTo(parent.start, 22.dp)
                },
            contentDescription = "profile",
            error = painterResource(id = R.drawable.ic_auth),
            contentScale = ContentScale.Inside,
            placeholder = painterResource(id = R.drawable.ic_auth),
        )
        MediumText(text = name, modifier = Modifier.constrainAs(user) {
            top.linkTo(img.top, 4.dp)
            start.linkTo(img.end, 14.dp)
        })
        Icon(painter = painterResource(id = R.drawable.ic_star),
            tint = MaterialTheme.colorScheme.goldenTainoiColor,
            contentDescription = "star",
            modifier = Modifier.constrainAs(star) {
                top.linkTo(user.bottom, 2.dp)
                start.linkTo(user.start)
            })
        Text(
            text = rating, color = Color.Black, modifier = Modifier.constrainAs(rateRef) {
                top.linkTo(star.top)
                bottom.linkTo(star.bottom)
                start.linkTo(star.end)
            }, style = TextStyle(
                fontFamily = montserratFamily, fontSize = 10.sp, fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = comment, color = Color.Black, modifier = Modifier.constrainAs(commentRef) {
                top.linkTo(img.bottom, 12.dp)
                start.linkTo(img.start)
                bottom.linkTo(parent.bottom, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            }, style = TextStyle(
                fontFamily = montserratFamily,
                fontSize = 11.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Preview
@Composable
fun PreviewCommentCard() {
    CommentCard(
        "Ceyda Yılmaztürk",
        "4",
        "Her danışmanın 1 kere değil bir çok kez izleyerek sindirebilecek muhteşem bir eğitim.",
        ""
    )
}

@Composable
fun RatingBarCard(onRatingSelect : (Float) -> Unit, modifier: Modifier = Modifier) {
    var rating: Float by remember { mutableFloatStateOf(0.0f) }

    Box(
        modifier = modifier
            .padding(start = 13.dp, end = 13.dp, top = 10.dp)
            .fillMaxWidth()
            .height(68.dp)
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
    ) {
        RatingBar(
            value = rating,
            modifier = Modifier.align(Alignment.Center),
            painterFilled = painterResource(id = R.drawable.ic_star),
            painterEmpty = painterResource(id = R.drawable.ic_star_empty),
            spaceBetween = 0.dp,
            size = 26.dp, onValueChange = {
                rating = it
            }) {
            onRatingSelect(it)
        }
    }
}

@Preview
@Composable
fun PreviewRatingBarCard() {
    RatingBarCard(onRatingSelect = {}, modifier = Modifier.fillMaxWidth())
}