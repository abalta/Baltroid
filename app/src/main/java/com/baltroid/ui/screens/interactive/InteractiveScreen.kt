package com.baltroid.ui.screens.interactive

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.baltroid.apps.R
import com.baltroid.core.common.model.DialogueXml
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.IconWithTextNextTo
import com.baltroid.ui.common.SetLoadingState
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.HitReadsTopBar
import com.baltroid.ui.screens.reading.LastEpisodeButtons
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orEmpty
import com.baltroid.util.orZero
import com.hitreads.core.domain.model.OriginalType
import com.hitreads.core.model.Episode
import com.hitreads.core.model.IndexOriginal
import com.hitreads.core.model.ShowEpisode

@Composable
fun InteractiveScreen(
    viewModel: OriginalViewModel,
    openMenuScreen: () -> Unit
) {


    /*LaunchedEffect(original) {
        original?.id?.let { viewModel.showEpisode(761, OriginalType.INTERACTIVE) }
    }*/

    val interactiveContent =
        viewModel.uiStateReading.collectAsStateWithLifecycle().value.episode?.xmlContents
            ?.episode?.dialogue

    val readingUiState by viewModel.uiStateReading.collectAsStateWithLifecycle()

    var currentDialogue by remember(interactiveContent) {
        mutableStateOf(interactiveContent?.firstOrNull())
    }

    val original by viewModel.uiStateDetail.collectAsStateWithLifecycle()

    SetLoadingState(isLoading = readingUiState.isLoading)

    LaunchedEffect(viewModel.selectedEpisodeId.value) {
        viewModel.setSelectedEpisodeId(761)
        viewModel.showEpisode(OriginalType.INTERACTIVE)
    }


    Box(
        modifier = Modifier.navigationBarsPadding()
    ) {
        CroppedImage(
            imgResId = R.drawable.woods_image,
            modifier = Modifier.fillMaxSize()
        )
        if (currentDialogue?.focus != null && currentDialogue?.lineType == null) {
            AsyncImage(
                model = readingUiState.episode
                    ?.bundleAssets
                    ?.firstOrNull { it.type == "focus" && it.typeId.toString() == currentDialogue?.focus }
                    ?.path.orEmpty(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            HitReadsTopBar(
                iconResId = R.drawable.ic_bell,
                numberOfNotification = 12,
                onMenuClick = openMenuScreen,
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.dp11))
            ) {}
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                currentDialogue?.let { dialogue ->
                    if (dialogue.optionCount != null) {
                        Options(
                            listOf(
                                InteractiveOptions(
                                    dialogue.optionOne.toString(),
                                    dialogue.optionOneNextLineId.toString()
                                ),
                                InteractiveOptions(
                                    dialogue.optionTwo.toString(),
                                    dialogue.optionTwoNextLineId.toString()
                                ),
                                InteractiveOptions(
                                    dialogue.optionThree.toString(),
                                    dialogue.optionThreeNextLineId.toString()
                                ),
                            ),
                            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dp24))
                        ) { nextLineId ->
                            currentDialogue =
                                interactiveContent?.firstOrNull { it?.lineId == nextLineId }
                        }
                    }

                    when (dialogue.lineType) {
                        null -> {
                            InteractiveTextBox(
                                model = dialogue,
                                episode = readingUiState.episode,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .wrapContentHeight(Alignment.Bottom)
                            ) { nextLineId ->
                                currentDialogue =
                                    interactiveContent?.firstOrNull { it?.lineId == nextLineId }
                            }
                        }

                        "NOT" -> {
                            InteractiveNote(
                                dialogue = dialogue,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .wrapContentHeight(Alignment.CenterVertically)
                                    .padding(bottom = dimensionResource(id = R.dimen.dp125))
                            ) { nextLineId ->
                                currentDialogue =
                                    interactiveContent?.firstOrNull { it?.lineId == nextLineId }
                            }
                        }
                    }
                }
            }
            if (currentDialogue != null && (readingUiState.episode?.isLastEpisode == true && (currentDialogue?.nextLineId == currentDialogue?.lineId || currentDialogue?.nextLineId.isNullOrEmpty()) && currentDialogue?.optionCount == null)) {
                LastEpisodeButtons(
                    onCreateFavorite = {},
                    onShare = { },
                    goToFirstEpisode = {},
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.dp65))
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
            if (currentDialogue?.lineType == "SMS") {
                InteractiveSmsBox(dialogue = currentDialogue) { nextLineId ->
                    currentDialogue =
                        interactiveContent?.firstOrNull { it?.lineId == nextLineId }
                }
            } else {
                InteractiveScreenBottomSection(
                    indexOriginal = original.original,
                    episode = readingUiState.episode,
                    createComment = {

                    },
                    createFavorite = {
                        if (it) viewModel.deleteFavorite()
                        else viewModel.createFavorite()
                    }
                )
            }
        }
    }
}

@Composable
fun RemindingInfo(
    model: DialogueXml,
    modifier: Modifier = Modifier,
    onClick: (nextLineId: String) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                onClick.invoke(model.nextLineId.toString())
            }
            .padding(
                start = dimensionResource(id = R.dimen.dp35),
                end = dimensionResource(id = R.dimen.dp20)
            )
    ) {
        ImageWithNameCard()
        HorizontalSpacer(width = dimensionResource(id = R.dimen.dp13))
        TextBalloon(
            text = model.text.toString(),
            modifier = Modifier.align(Alignment.Bottom)
        )
    }
}

@Composable
fun InteractiveSmsBox(
    dialogue: DialogueXml?,
    modifier: Modifier = Modifier,
    onClick: (nextLineId: String) -> Unit
) {
    Column(Modifier.clickable { onClick.invoke(dialogue?.nextLineId.orEmpty()) }) {
        Text(
            text = dialogue?.text.toString(),
            style = MaterialTheme.localTextStyles.poppins14Regular,
            color = MaterialTheme.localColors.white_alpha08,
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.dp16),
                    bottom = dimensionResource(id = R.dimen.dp8),
                    end = dimensionResource(id = R.dimen.dp70)
                )
                .background(MaterialTheme.localColors.black, MaterialTheme.localShapes.roundedDp20)
                .padding(
                    vertical = dimensionResource(id = R.dimen.dp25), horizontal = dimensionResource(
                        id = R.dimen.dp23
                    )
                )
        )
        Box {
            Column(
                modifier = modifier.background(MaterialTheme.localColors.black)
            ) {
                Divider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.localColors.white
                )
                VerticalSpacer(height = R.dimen.dp18)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Bir mesaj yaz",
                        style = MaterialTheme.localTextStyles.poppins14Regular,
                        color = MaterialTheme.localColors.black_alpha05,
                        modifier = Modifier
                            .padding(start = dimensionResource(id = R.dimen.dp16))
                            .background(
                                MaterialTheme.localColors.white_alpha06,
                                shape = MaterialTheme.localShapes.roundedDp12
                            )
                            .padding(vertical = dimensionResource(id = R.dimen.dp12))
                            .padding(start = dimensionResource(id = R.dimen.dp23))
                            .weight(1f)
                    )
                    HorizontalSpacer(width = R.dimen.dp12)
                    SimpleIcon(iconResId = R.drawable.ic_interactive_arrow)
                    HorizontalSpacer(width = R.dimen.dp22)
                }
                VerticalSpacer(height = R.dimen.dp50)
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(
                        dimensionResource(id = R.dimen.dp75),
                        dimensionResource(id = R.dimen.dp67)
                    )
                    .background(
                        MaterialTheme.localColors.yellow,
                        MaterialTheme.localShapes.roundedDp8
                    )
            ) {
                SimpleIcon(
                    iconResId = R.drawable.ic_menu_horizontal,
                    modifier = Modifier.alpha(0.5f),
                    tint = MaterialTheme.localColors.black
                )
            }
        }
    }
}

@Composable
fun Options(
    items: List<InteractiveOptions>,
    modifier: Modifier = Modifier,
    onClick: (nextLineId: String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(id = R.dimen.dp27),
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.dp12))
        ) {
            items(items) { dialogue ->
                if (dialogue.option != "null") {
                    Text(
                        text = dialogue.option,
                        style = MaterialTheme.localTextStyles.poppins14Medium,
                        color = MaterialTheme.localColors.white,
                        modifier = Modifier
                            .clickable { onClick.invoke(dialogue.nextLineId) }
                            .fillMaxWidth()
                            .clip(MaterialTheme.localShapes.roundedDp10)
                            .background(MaterialTheme.localColors.black)
                            .padding(
                                vertical = dimensionResource(id = R.dimen.dp14),
                                horizontal = dimensionResource(id = R.dimen.dp21)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun InteractiveNote(
    dialogue: DialogueXml,
    modifier: Modifier = Modifier,
    onClick: (nextLineId: String) -> Unit,
) {
    Text(
        text = dialogue.text.toString(),
        style = MaterialTheme.localTextStyles.spaceGrotesk22Medium,
        color = MaterialTheme.localColors.yellow,
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(horizontal = dimensionResource(id = R.dimen.dp56))
            .border(dimensionResource(id = R.dimen.dp1), color = MaterialTheme.localColors.white)
            .fillMaxWidth()
            .clickable { onClick.invoke(dialogue.nextLineId.orEmpty()) }
            .background(MaterialTheme.localColors.black)
            .padding(vertical = dimensionResource(id = R.dimen.dp15))
    )
}

@Composable
fun InteractiveScreenBottomSection(
    indexOriginal: IndexOriginal?,
    episode: ShowEpisode?,
    createComment: () -> Unit,
    createFavorite: (Boolean) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            thickness = 0.5.dp,
            color = MaterialTheme.localColors.white
        )
        VerticalSpacer(height = 7.dp)
        SimpleIcon(iconResId = R.drawable.ic_menu_horizontal)
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp12))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalSpacer(width = dimensionResource(id = R.dimen.dp31))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = indexOriginal?.title.orEmpty(),
                    style = MaterialTheme.localTextStyles.poppins17Light,
                    color = MaterialTheme.localColors.white,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(dimensionResource(id = R.dimen.dp100))
                )
                Text(
                    text = stringResource(id = R.string.episode_number, episode?.sort.orZero()),
                    style = MaterialTheme.localTextStyles.poppins13Medium,
                    color = MaterialTheme.localColors.white
                )
            }
            IconWithTextNextTo(
                iconResId = R.drawable.ic_comment,
                text = indexOriginal?.commentCount.orZero().toString(),
                textStyle = MaterialTheme.localTextStyles.poppins10SemiBold,
                spacedBy = dimensionResource(id = R.dimen.dp10),
                onIconClick = createComment,
            )
            HorizontalSpacer(width = dimensionResource(id = R.dimen.dp19))
            SimpleIcon(
                iconResId = if (indexOriginal?.indexUserData?.isFav == true) R.drawable.ic_star
                else R.drawable.ic_star_outlined,
                tint = if (indexOriginal?.indexUserData?.isFav == true) MaterialTheme.localColors.yellow
                else Color.Unspecified,
                modifier = Modifier.clickable {
                    createFavorite.invoke(indexOriginal?.indexUserData?.isFav ?: false)
                }
            )
            HorizontalSpacer(
                width = dimensionResource(id = R.dimen.dp21)
            )
        }
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp34))
    }
}

@Composable
fun InteractiveScreenBottomBar(
    indexOriginal: IndexOriginal?,
    episode: Episode?,
    onCommentsClicked: () -> Unit,
) {

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Text(
                text = indexOriginal?.hashtag.orEmpty(),
                style = MaterialTheme.localTextStyles.poppins15Medium,
                color = MaterialTheme.localColors.white
            )
            IconWithTextNextTo(
                iconResId = R.drawable.ic_eye,
                text = indexOriginal?.viewCount.toString(),
                textStyle = MaterialTheme.localTextStyles.poppins10Regular,
                spacedBy = dimensionResource(id = R.dimen.dp9),
            ) {}
            IconWithTextNextTo(
                iconResId = R.drawable.ic_comment,
                text = indexOriginal?.commentCount.toString(),
                textStyle = MaterialTheme.localTextStyles.poppins10Regular,
                spacedBy = dimensionResource(id = R.dimen.dp9),
                modifier = Modifier
                    .clickable { onCommentsClicked.invoke() }
            ) {}
            Row {
                Divider(
                    color = MaterialTheme.localColors.white,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(dimensionResource(id = R.dimen.dp1))
                )
                SimpleIcon(
                    iconResId = R.drawable.ic_add_comment, modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.dp12),
                            vertical = 8.dp
                        )
                )
                Divider(
                    color = MaterialTheme.localColors.white,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(dimensionResource(id = R.dimen.dp1))
                )
            }
            Text(
                text = "BÖLÜM ${episode?.id}",
                style = MaterialTheme.localTextStyles.poppins13Medium,
                color = MaterialTheme.localColors.white_alpha07,
            )
            SimpleIcon(
                iconResId = R.drawable.ic_banner_filled,
                tint = MaterialTheme.localColors.purple
            )
        }
        Divider(
            thickness = dimensionResource(id = R.dimen.dp1),
            color = MaterialTheme.localColors.white
        )
    }
}

@Composable
fun ImageWithNameCard(
    modifier: Modifier = Modifier
) {

    Layout(
        modifier = modifier,
        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .layoutId("image")
                    .size(106.dp)
                    .drawBehind {
                        drawCircle(Color.Black)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.woods_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(96.dp)
                )

            }
            Box(
                modifier = Modifier
                    .size(131.dp, 62.dp)
                    .background(Color.Black, RoundedCornerShape(10.dp))
                    .layoutId("dark_box")

            )
            Text(
                text = "MURAT",
                style = MaterialTheme.localTextStyles.poppins14Medium,
                color = MaterialTheme.localColors.white,
                modifier = Modifier.layoutId("name")
            )
        }
    ) { measurables, constraints ->
        val imagePlaceable = measurables.first { it.layoutId == "image" }.measure(
            constraints.copy(
                minWidth = 0, minHeight = 0
            )
        )

        val boxPlaceable = measurables.first { it.layoutId == "dark_box" }.measure(
            constraints.copy(
                minWidth = 0, minHeight = 0
            )
        )
        val namePlaceable = measurables.first { it.layoutId == "name" }.measure(
            constraints.copy(
                minWidth = 0, minHeight = 0
            )
        )

        val penetrationPadding = (27.5).dp.roundToPx()
        val textPadding = (9.5).dp.roundToPx()
        val minWidth = boxPlaceable.width
        val minHeight = boxPlaceable.height + imagePlaceable.height - penetrationPadding

        layout(minWidth, minHeight) {
            imagePlaceable.placeRelative(
                x = (minWidth / 2) - imagePlaceable.width / 2,
                y = 0,
                zIndex = 3f
            )
            boxPlaceable.placeRelative(
                (minWidth / 2) - boxPlaceable.width / 2,
                imagePlaceable.height - penetrationPadding,
                zIndex = 1f
            )
            namePlaceable.placeRelative(
                x = (minWidth / 2) - namePlaceable.width / 2,
                y = minHeight - namePlaceable.height - textPadding,
                zIndex = 2f
            )
        }
    }
}

@Composable
fun TextBalloon(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF050505),
    pointerLocation: PointerLocation = PointerLocation.LEFT
) {
    BoxWithConstraints(
        modifier = modifier
            .requiredHeight(126.dp)
    ) {

        val backgroundCornerRadius = 10.dp
        val pointerCornerRadius = 2.dp
        val pointerSize = 23.54.dp
        val pointerOffsetX = 6.dp
        val pointerOffsetY = 44.dp
        val bodyOffsetX = 11.dp
        val maxHeight = this.maxHeight
        val maxWidth = this.maxWidth
        val isPointerLeft = pointerLocation == PointerLocation.LEFT

        Canvas(
            modifier = Modifier
                .requiredHeight(maxHeight)
                .requiredWidthIn(maxWidth)
        ) {
            drawRoundRect(
                color = backgroundColor,
                size = Size(
                    width = (maxWidth - bodyOffsetX).toPx(),
                    height = maxHeight.toPx()
                ),
                topLeft = Offset(
                    x = if (isPointerLeft) bodyOffsetX.toPx()
                    else 0f,
                    y = 0f
                ),
                cornerRadius = CornerRadius(
                    x = backgroundCornerRadius.toPx(),
                    y = backgroundCornerRadius.toPx()
                )
            )
            rotate(
                degrees = 45f,
                pivot = Offset(
                    x = if (isPointerLeft) pointerOffsetX.toPx() +
                            (pointerSize
                                    / 2f).toPx()
                    else pointerOffsetX.toPx() + maxWidth.toPx() -
                            bodyOffsetX.toPx()
                            - pointerSize.toPx() + (pointerSize / 2).toPx(),
                    y = this@Canvas.size.height - pointerOffsetY.toPx() + (
                            pointerSize
                                    / 2f).toPx()
                )
            ) {
                drawRoundRect(
                    color = backgroundColor,
                    size = Size(pointerSize.toPx(), pointerSize.toPx()),
                    topLeft = Offset(
                        x = if (isPointerLeft)
                            pointerOffsetX.toPx()
                        else pointerOffsetX.toPx() + maxWidth.toPx() -
                                bodyOffsetX.toPx()
                                - pointerSize.toPx(),
                        y = this@Canvas.size.height - pointerOffsetY.toPx()
                    ),
                    cornerRadius = CornerRadius(
                        pointerCornerRadius.toPx(),
                        pointerCornerRadius.toPx(),
                    ),
                )
            }
        }

        Text(
            text = text,
            style = MaterialTheme.localTextStyles.poppins14Medium,
            color = MaterialTheme.localColors.white,
            modifier = Modifier
                .width(this.maxWidth)
                .height(this.maxHeight)
                .padding(
                    start = if (isPointerLeft) bodyOffsetX + 22.5.dp else 23.dp,
                    top = 25.5.dp,
                    end = if (isPointerLeft) 13.5.dp else bodyOffsetX + 13.dp,
                    bottom = 13.5.dp,
                )
                .verticalScroll(rememberScrollState())
        )

    }
}

enum class PointerLocation {
    LEFT,
    RIGHT
}

data class InteractiveOptions(
    val option: String,
    val nextLineId: String
)

@Composable
fun InteractiveTextBox(
    model: DialogueXml,
    episode: ShowEpisode?,
    modifier: Modifier = Modifier,
    onClick: (nextLineId: String) -> Unit
) {
    Box(
        modifier
            .height(dimensionResource(id = R.dimen.dp236))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dp170))
                .background(MaterialTheme.localColors.black)
                .align(Alignment.BottomCenter)
        ) {
            Column {
                model.talker?.let {
                    Text(
                        text = "Talker: $it",
                        style = MaterialTheme.localTextStyles.poppins14Medium,
                        color = MaterialTheme.localColors.white,
                        modifier = Modifier.padding(
                            top = dimensionResource(id = R.dimen.dp9),
                            start = dimensionResource(id = R.dimen.dp31)
                        )
                    )
                }
                Row {
                    Text(
                        text = model.text.orEmpty(),
                        style = MaterialTheme.localTextStyles.poppins14Regular,
                        color = MaterialTheme.localColors.white_alpha08,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(
                                start = dimensionResource(id = R.dimen.dp31),
                                top = dimensionResource(id = R.dimen.dp15)
                            )
                    )
                    if (model.optionCount == null) {
                        SimpleIcon(
                            iconResId = R.drawable.ic_arrow_forward,
                            modifier = Modifier
                                .padding(
                                    bottom = dimensionResource(id = R.dimen.dp18),
                                    end = dimensionResource(id = R.dimen.dp22)
                                )
                                .size(dimensionResource(id = R.dimen.dp19))
                                .fillMaxHeight()
                                .align(Alignment.Bottom)
                                .clickable { onClick.invoke(model.nextLineId.orEmpty()) }
                        )
                    }
                }
            }
        }
        if (model.talker != null) {
            Talker(
                modifier = Modifier.align(Alignment.TopEnd),
                imgRes = episode
                    ?.bundleAssets
                    ?.firstOrNull { it.type == "talker" && it.typeId == model.talker?.toInt() }
                    ?.path.orEmpty()
            )
        }
    }
}

@Composable
fun Talker(
    imgRes: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = imgRes,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        loading = { CircularProgressIndicator() },
        error = { SimpleImage(imgResId = R.drawable.hitreads_placeholder) },
        modifier = modifier
            .size(dimensionResource(id = R.dimen.dp111))
            .clip(CircleShape)
            .background(MaterialTheme.localColors.white)
    )
}

@Preview
@Composable
fun InteractiveScreenPreview() {
}


/*@Composable
fun FirstInteractiveContent(
    model: DialogueXml,
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean,
    onNextClick: (nextLineId: String) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = model.text.toString(),
            style = MaterialTheme.localTextStyles.poppins14Regular,
            color = MaterialTheme.localColors.white_alpha08,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.localShapes.roundedDp10)
                .background(MaterialTheme.localColors.black)
                .padding(
                    vertical = dimensionResource(id = R.dimen.dp17),
                    horizontal = dimensionResource(id = R.dimen.dp21)
                )
        )
        if (isButtonEnabled) {
            VerticalSpacer(height = dimensionResource(id = R.dimen.dp15))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
                    .padding(end = dimensionResource(id = R.dimen.dp4))
                    .clip(MaterialTheme.localShapes.roundedDp8_5)
                    .background(MaterialTheme.localColors.black)
                    .padding(
                        start = dimensionResource(id = R.dimen.dp19),
                        end = dimensionResource(id = R.dimen.dp13),
                        top = 8.dp,
                        bottom = 8.dp
                    )
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    style = MaterialTheme.localTextStyles.poppins13Medium,
                    color = MaterialTheme.localColors.white,
                    modifier = Modifier.clickable { onNextClick.invoke(model.nextLineId.toString()) }
                )
                HorizontalSpacer(width = dimensionResource(id = R.dimen.dp10))
                SimpleIcon(iconResId = R.drawable.ic_arrow_forward)
            }
        }
    }
}*/
