package com.baltroid.ui.screens.interactive

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.core.common.model.DialogueXml
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.IconWithTextNextTo
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.HitReadsTopBar
import com.baltroid.ui.screens.reading.Titles
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orEmpty
import com.hitreads.core.domain.model.OriginalType
import com.hitreads.core.model.Episode
import com.hitreads.core.model.Original

@Composable
fun InteractiveScreen(
    viewModel: OriginalViewModel,
    openMenuScreen: () -> Unit
) {

    val original = viewModel.sharedUIState.collectAsStateWithLifecycle().value

    LaunchedEffect(original) {
        original?.id?.let { viewModel.showEpisode(it, OriginalType.INTERACTIVE) }
    }

    val interactiveContent =
        viewModel.uiStateReading.collectAsStateWithLifecycle().value.episode?.xmlContent
            ?.episode?.dialogue

    val episode = viewModel.uiStateReading.collectAsStateWithLifecycle().value.episode

    var currentDialogue by remember(interactiveContent) {
        mutableStateOf(interactiveContent?.firstOrNull())
    }

    Box(
        modifier = Modifier.navigationBarsPadding()
    ) {
        CroppedImage(
            imgResId = R.drawable.woods_image,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            HitReadsTopBar(
                iconResId = R.drawable.ic_bell,
                numberOfNotification = 12,
                onMenuClick = openMenuScreen,
                modifier = Modifier
                    .padding(top = MaterialTheme.localDimens.dp11)
            ) {}
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = MaterialTheme.localDimens.dp20)
            ) {
                currentDialogue?.let { dialogue ->
                    if (dialogue.optionCount != null) {
                        SecondInteractiveContent(
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
                            )
                        ) { nextLineId ->
                            currentDialogue =
                                interactiveContent?.firstOrNull { it?.lineId == nextLineId }
                        }
                    }
                    if (dialogue.lineType == "NOT" || dialogue.lineType == null) {
                        FirstInteractiveContent(
                            model = dialogue,
                            isButtonEnabled = dialogue.optionCount == null,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(Alignment.Bottom)
                        ) { nextLineId ->
                            currentDialogue =
                                interactiveContent?.firstOrNull { it?.lineId == nextLineId }
                        }
                    } else if (dialogue.lineType == "SMS") {
                        RemindingInfo(
                            model = dialogue,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight(Alignment.Bottom)
                        ) { nextLineId ->
                            currentDialogue =
                                interactiveContent?.firstOrNull { it?.lineId == nextLineId }
                        }
                    }
                }
            }

            InteractiveScreenBottomSection(
                original = original,
                episode = episode,
                modifier = Modifier
                    .background(MaterialTheme.localColors.black_alpha02)
            )
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
                start = MaterialTheme.localDimens.dp35,
                end = MaterialTheme.localDimens.dp20
            )
    ) {
        ImageWithNameCard()
        HorizontalSpacer(width = MaterialTheme.localDimens.dp13)
        TextBalloon(
            text = model.text.toString(),
            modifier = Modifier.align(Alignment.Bottom)
        )
    }
}

@Composable
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
            style = MaterialTheme.localTextStyles.body,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.localShapes.roundedDp10)
                .background(MaterialTheme.localColors.black)
                .padding(
                    vertical = MaterialTheme.localDimens.dp17,
                    horizontal = MaterialTheme.localDimens.dp21
                )
        )
        if (isButtonEnabled) {
            VerticalSpacer(height = MaterialTheme.localDimens.dp15)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
                    .padding(end = MaterialTheme.localDimens.dp4)
                    .clip(MaterialTheme.localShapes.roundedDp8_5)
                    .background(MaterialTheme.localColors.black)
                    .padding(
                        start = MaterialTheme.localDimens.dp19,
                        end = MaterialTheme.localDimens.dp13,
                        top = MaterialTheme.localDimens.dp8,
                        bottom = MaterialTheme.localDimens.dp8
                    )
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    style = MaterialTheme.localTextStyles.subtitle,
                    modifier = Modifier.clickable { onNextClick.invoke(model.nextLineId.toString()) }
                )
                HorizontalSpacer(width = MaterialTheme.localDimens.dp10)
                SimpleIcon(iconResId = R.drawable.ic_arrow_forward)
            }
        }
    }
}

@Composable
fun SecondInteractiveContent(
    items: List<InteractiveOptions>,
    modifier: Modifier = Modifier,
    onClick: (nextLineId: String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(
                space = MaterialTheme.localDimens.dp27,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.localDimens.dp12)
        ) {
            items(items) { dialogue ->
                if (dialogue.option != "null") {
                    Text(
                        text = dialogue.option,
                        style = MaterialTheme.localTextStyles.imageCardText,
                        modifier = Modifier
                            .clickable { onClick.invoke(dialogue.nextLineId) }
                            .fillMaxWidth()
                            .clip(MaterialTheme.localShapes.roundedDp10)
                            .background(MaterialTheme.localColors.black)
                            .padding(
                                vertical = MaterialTheme.localDimens.dp14,
                                horizontal = MaterialTheme.localDimens.dp21
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun InteractiveScreenBottomSection(
    original: Original?,
    episode: Episode?,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column {
            VerticalSpacer(height = MaterialTheme.localDimens.dp30)
            InteractiveScreenBottomBar(
                original = original,
                episode = episode,
                onAddCommentClicked = {},
                onBannerClicked = {},
                onCommentsClicked = {}
            )
            VerticalSpacer(height = MaterialTheme.localDimens.dp12)
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalSpacer(width = MaterialTheme.localDimens.dp31)
                Titles(
                    title = original?.title.orEmpty(),
                    subtitle = original?.author?.name.orEmpty(),
                    modifier = Modifier.weight(1f)
                )
                SimpleIcon(
                    iconResId = R.drawable.ic_star_outlined,
                    modifier = Modifier.align(Alignment.Bottom)
                )
                HorizontalSpacer(width = MaterialTheme.localDimens.dp19)
                SimpleIcon(
                    iconResId = R.drawable.ic_menu,
                    modifier = Modifier.align(Alignment.Bottom)
                )
                HorizontalSpacer(
                    width = MaterialTheme.localDimens.dp21
                )
            }
            VerticalSpacer(height = MaterialTheme.localDimens.dp50)
        }
    }
}

@Composable
fun InteractiveScreenBottomBar(
    original: Original?,
    episode: Episode?,
    onCommentsClicked: () -> Unit,
    onAddCommentClicked: () -> Unit,
    onBannerClicked: () -> Unit
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
                text = original?.hashtag.orEmpty(),
                style = MaterialTheme.localTextStyles.interactiveHashtag
            )
            IconWithTextNextTo(
                iconResId = R.drawable.ic_eye,
                text = original?.viewCount.toString(),
                textStyle = MaterialTheme.localTextStyles.sideBarIconText,
                spacedBy = MaterialTheme.localDimens.dp9,
            ) {}
            IconWithTextNextTo(
                iconResId = R.drawable.ic_comment,
                text = original?.commentCount.toString(),
                textStyle = MaterialTheme.localTextStyles.sideBarIconText,
                spacedBy = MaterialTheme.localDimens.dp9,
                modifier = Modifier
                    .clickable { onCommentsClicked.invoke() }
            ) {}
            Row {
                Divider(
                    color = MaterialTheme.localColors.white,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(MaterialTheme.localDimens.dp0_5)
                )
                SimpleIcon(
                    iconResId = R.drawable.ic_add_comment, modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            horizontal = MaterialTheme.localDimens.dp12,
                            vertical = MaterialTheme.localDimens.dp8
                        )
                )
                Divider(
                    color = MaterialTheme.localColors.white,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(MaterialTheme.localDimens.dp0_5)
                )
            }
            Text(
                text = "BÖLÜM ${episode?.id}",
                style = MaterialTheme.localTextStyles.interactiveEpisode
            )
            SimpleIcon(
                iconResId = R.drawable.ic_banner_filled,
                tint = MaterialTheme.localColors.purple
            )
        }
        Divider(
            thickness = MaterialTheme.localDimens.dp0_5,
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
                style = MaterialTheme.localTextStyles.imageCardText,
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
            style = MaterialTheme.localTextStyles.imageCardText,
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
    text: String,
    title: String? = null,
    isNextButtonEnabled: Boolean = true
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(MaterialTheme.localDimens.dp225)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.localDimens.dp155)
                .background(MaterialTheme.localColors.black)
                .align(Alignment.BottomCenter)
        ) {
            Column {
                title?.let {
                    Text(
                        text = title,
                        style = MaterialTheme.localTextStyles.imageCardText,
                        modifier = Modifier.padding(
                            top = MaterialTheme.localDimens.dp9,
                            start = MaterialTheme.localDimens.dp31
                        )
                    )
                }
                Row {
                    Text(
                        text = text,
                        style = MaterialTheme.localTextStyles.body,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(
                                start = MaterialTheme.localDimens.dp31,
                                top = MaterialTheme.localDimens.dp15
                            )
                    )
                    if (isNextButtonEnabled) {
                        SimpleIcon(
                            iconResId = R.drawable.ic_arrow_forward,
                            modifier = Modifier
                                .padding(
                                    bottom = MaterialTheme.localDimens.dp18,
                                    end = MaterialTheme.localDimens.dp22
                                )
                                .size(MaterialTheme.localDimens.dp19)
                                .fillMaxHeight()
                                .align(Alignment.Bottom)
                        )
                    }
                }
            }
        }
        CroppedImage(
            imgResId = R.drawable.woods_image,
            modifier = Modifier
                .padding(end = MaterialTheme.localDimens.dp20)
                .size(MaterialTheme.localDimens.dp111)
                .clip(
                    CircleShape
                )
                .align(Alignment.TopEnd)
        )
    }
}

@Preview
@Composable
fun InteractiveScreenPreview() {
    InteractiveTextBox(
        text = "Far far away, behind the word mountains, far from the countries Vokalia abehind the word mountains, far from the countries Vokalia abehind the word mountains, far from the countries Vokalia abehind the word mountains, far from the countries Vokalia abehind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean",
        title = "ANLATICI"
    )
}

