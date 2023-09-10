package com.baltroid.ui.screens.interactive

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility.Companion.Gone
import androidx.constraintlayout.compose.Visibility.Companion.Visible
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
import com.baltroid.ui.screens.reading.ReadingUiState
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.orEmpty
import com.baltroid.util.orZero
import com.hitreads.core.domain.model.OriginalType
import com.hitreads.core.model.Episode
import com.hitreads.core.model.IndexAuthor
import com.hitreads.core.model.IndexOriginal
import com.hitreads.core.model.IndexPackage
import com.hitreads.core.model.IndexUserData
import com.hitreads.core.model.ShowEpisode

@Composable
fun InteractiveScreen(
    viewModel: OriginalViewModel
) {

    val readingUiState by viewModel.uiStateReading.collectAsStateWithLifecycle()
    val original by viewModel.uiStateDetail.collectAsStateWithLifecycle()

    SetLoadingState(isLoading = readingUiState.isLoading)

    LaunchedEffect(viewModel.selectedEpisodeId.value) {
        viewModel.setSelectedEpisodeId(761)
        viewModel.showEpisode(OriginalType.INTERACTIVE)
    }

    InteractiveScreenContent(
        original = original.original,
        readingUiState = readingUiState,
        action = viewModel::handleUiEvent,
    )
}

@Composable
fun InteractiveScreenContent(
    original: IndexOriginal,
    readingUiState: ReadingUiState,
    action: (InteractiveScreenAction) -> Unit,
) {

    val interactiveContent = readingUiState.episode?.xmlContents?.episode?.dialogue
    var currentDialogue by remember(interactiveContent) {
        mutableStateOf(interactiveContent?.firstOrNull())
    }

    val focusImg by remember(currentDialogue) {
        derivedStateOf {
            // todo ?.firstOrNull { it.type == "focus" && it.typeId.toString() == currentDialogue?.focus }
            readingUiState.episode
                ?.bundleAssets
                ?.firstOrNull { it.type == "talker" && it.typeId.toString() == currentDialogue?.talker }
                ?.path.orEmpty()
        }
    }

    val talker by remember(currentDialogue) {
        derivedStateOf {
            readingUiState.episode
                ?.bundleAssets
                ?.firstOrNull { it.type == "talker" && it.typeId.toString() == currentDialogue?.talker }
                ?.path.orEmpty()
        }
    }

    val isEndOfEpisode by remember(currentDialogue) {
        derivedStateOf {
            currentDialogue?.nextLineId == currentDialogue?.lineId
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        val (toolbar, bottombar, focusCharacter,
            textBox, options, comments,
            episodes, note, smsBar,
            talkerCircle, episodeEndButtons) = createRefs()

        val guideLine = createGuidelineFromTop(0.3f)

        CroppedImage(
            imgResId = R.drawable.woods_image,
            modifier = Modifier.fillMaxSize()
        )

        AsyncImage(
            model = talker,
            error = painterResource(id = R.drawable.mock_talker),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp120))
                .clip(CircleShape)
                .background(MaterialTheme.localColors.black)
                .constrainAs(talkerCircle) {
                    bottom.linkTo(guideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    visibility = if (currentDialogue?.lineType == "SMS") Visible else Gone
                }
        )

        AsyncImage(
            model = focusImg,
            error = painterResource(id = R.drawable.mock_focus),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(focusCharacter) {
                    bottom.linkTo(bottombar.top)
                    top.linkTo(toolbar.bottom)
                    height = Dimension.fillToConstraints
                    visibility =
                        if (currentDialogue?.focus != null &&
                            currentDialogue?.lineType == null &&
                            currentDialogue?.optionCount == null
                        ) Visible
                        else Gone
                }
        )

        InteractiveNote(
            dialogue = currentDialogue,
            modifier = Modifier.constrainAs(note) {
                top.linkTo(guideLine)
                start.linkTo(parent.start, 60.dp)
                end.linkTo(parent.end, 60.dp)
                width = Dimension.fillToConstraints
                visibility = if (currentDialogue?.lineType == "NOT") Visible else Gone
            }
        ) { nextLineId ->
            currentDialogue =
                interactiveContent?.firstOrNull { it?.lineId == nextLineId }
        }

        Options(
            items = listOf(
                InteractiveOptions(
                    currentDialogue?.optionOne.toString(),
                    currentDialogue?.optionOneNextLineId.toString()
                ),
                InteractiveOptions(
                    currentDialogue?.optionTwo.toString(),
                    currentDialogue?.optionTwoNextLineId.toString()
                ),
                InteractiveOptions(
                    currentDialogue?.optionThree.toString(),
                    currentDialogue?.optionThreeNextLineId.toString()
                ),
            ),
            modifier = Modifier.constrainAs(options) {
                top.linkTo(guideLine)
                start.linkTo(parent.start, 20.dp)
                end.linkTo(parent.end, 20.dp)
                bottom.linkTo(textBox.top)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
                visibility = if (currentDialogue?.optionCount != null) Visible else Gone
            }
        ) { nextLineId ->
            currentDialogue =
                interactiveContent?.firstOrNull { it?.lineId == nextLineId }
        }

        EpisodeEndButtons(
            isLastEpisode = readingUiState.episode?.isLastEpisode == true,
            onClick = action,
            modifier = Modifier
                .constrainAs(episodeEndButtons) {
                    bottom.linkTo(parent.bottom, 56.dp)
                    start.linkTo(note.start)
                    end.linkTo(note.end)
                    width = Dimension.fillToConstraints
                    visibility = if (isEndOfEpisode) Visible
                    else Gone
                }
        )

        InteractiveSmsBox(
            dialogue = currentDialogue,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(smsBar) {
                    bottom.linkTo(parent.bottom)
                    visibility = if (currentDialogue?.lineType == "SMS") Visible else Gone
                }
        ) { nextLineId ->
            currentDialogue =
                interactiveContent?.firstOrNull { it?.lineId == nextLineId }

        }

        InteractiveScreenBottomSection(
            indexOriginal = original,
            episode = readingUiState.episode,
            createComment = {},
            createFavorite = {},
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottombar) {
                    bottom.linkTo(parent.bottom)
                    visibility =
                        if (currentDialogue?.lineType == "SMS" || isEndOfEpisode) Gone else Visible
                }
        )

        InteractiveText(
            model = currentDialogue,
            talker = talker,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(textBox) {
                    bottom.linkTo(bottombar.top)
                    visibility =
                        if (currentDialogue?.lineType == null && currentDialogue != null) Visible else Gone
                }
        ) { nextLineId ->
            currentDialogue =
                interactiveContent?.firstOrNull { it?.lineId == nextLineId }
        }

        InteractiveScreenToolbar(
            gemCount = 1500,
            modifier = Modifier
                .statusBarsPadding()
                .constrainAs(toolbar) {
                    top.linkTo(parent.top)
                }
        ) {

        }
    }
}

@Composable
fun EpisodeEndButtons(
    isLastEpisode: Boolean,
    modifier: Modifier = Modifier,
    onClick: (action: InteractiveScreenAction) -> Unit
) {
    if (isLastEpisode) {
        Column(modifier) {
            EpisodeButton(
                buttonTitle = stringResource(id = R.string.add_favorite),
                modifier = Modifier.fillMaxWidth()
            ) {
                onClick.invoke(InteractiveScreenAction.CREATE_FAVORITE)
            }
            VerticalSpacer(height = R.dimen.dp17)
            EpisodeButton(
                buttonTitle = stringResource(id = R.string.share),
                modifier = Modifier.fillMaxWidth()
            ) {
                onClick.invoke(InteractiveScreenAction.SHARE)
            }
            VerticalSpacer(height = R.dimen.dp17)
            EpisodeButton(
                buttonTitle = stringResource(id = R.string.go_to_beginning),
                modifier = Modifier.fillMaxWidth()
            ) {
                onClick.invoke(InteractiveScreenAction.GO_TO_BEGINNING)
            }
        }
    } else {
        Column(modifier) {
            EpisodeButton(
                buttonTitle = stringResource(id = R.string.go_to_beginning),
                modifier = Modifier.fillMaxWidth()
            ) {
                onClick.invoke(InteractiveScreenAction.GO_TO_BEGINNING)
            }
            VerticalSpacer(height = R.dimen.dp17)
            EpisodeButton(
                buttonTitle = stringResource(id = R.string.next_episode),
                modifier = Modifier.fillMaxWidth()
            ) {
                onClick.invoke(InteractiveScreenAction.NEXT_EPISODE)
            }
        }
    }
}

@Composable
fun EpisodeButton(
    buttonTitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Text(
        text = buttonTitle,
        style = MaterialTheme.localTextStyles.spaceGrotesk22Medium,
        color = MaterialTheme.localColors.white,
        textAlign = TextAlign.Center,
        modifier = modifier
            .clip(MaterialTheme.localShapes.roundedDp24)
            .clickable(onClick = onClick)
            .background(MaterialTheme.localColors.black)
            .border(
                dimensionResource(id = R.dimen.dp1),
                color = MaterialTheme.localColors.white_alpha05,
                shape = MaterialTheme.localShapes.roundedDp24
            )
            .padding(vertical = dimensionResource(id = R.dimen.dp15))
    )
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
    }
}

@Composable
fun InteractiveSmsBox(
    dialogue: DialogueXml?,
    modifier: Modifier = Modifier,
    onClick: (nextLineId: String) -> Unit
) {
    Column(modifier.clickable { onClick.invoke(dialogue?.nextLineId.orEmpty()) }) {
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
                modifier = Modifier.background(MaterialTheme.localColors.black)
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
    dialogue: DialogueXml?,
    modifier: Modifier = Modifier,
    onClick: (nextLineId: String) -> Unit,
) {
    Text(
        text = dialogue?.text.toString(),
        style = MaterialTheme.localTextStyles.spaceGrotesk22Medium,
        color = MaterialTheme.localColors.yellow,
        textAlign = TextAlign.Center,
        modifier = modifier
            .border(dimensionResource(id = R.dimen.dp1), color = MaterialTheme.localColors.white)
            .fillMaxWidth()
            .clickable { onClick.invoke(dialogue?.nextLineId.orEmpty()) }
            .background(MaterialTheme.localColors.black)
            .padding(vertical = dimensionResource(id = R.dimen.dp15))
    )
}

@Composable
fun InteractiveScreenBottomSection(
    indexOriginal: IndexOriginal?,
    episode: ShowEpisode?,
    createComment: () -> Unit,
    createFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
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
                modifier = Modifier.clickable(onClick = createFavorite)
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

/*@Composable
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
}*/

/*enum class PointerLocation {
    LEFT,
    RIGHT
}*/

data class InteractiveOptions(
    val option: String,
    val nextLineId: String
)

@Composable
fun InteractiveText(
    model: DialogueXml?,
    talker: String,
    modifier: Modifier = Modifier,
    onClick: (nextLineId: String) -> Unit
) {
    ConstraintLayout(modifier = modifier) {

        val (background, title, content, image, icon) = createRefs()
        Box(
            modifier = Modifier
                .background(MaterialTheme.localColors.black)
                .constrainAs(background) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.top, (-23).dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Text(
            text = "Talker: ${model?.talker}",
            style = MaterialTheme.localTextStyles.poppins14Medium,
            color = MaterialTheme.localColors.white,
            modifier = Modifier.constrainAs(title) {
                bottom.linkTo(content.top, 16.dp)
                start.linkTo(parent.start, 31.dp)
            }
        )
        Text(
            text = model?.text.orEmpty(),
            style = MaterialTheme.localTextStyles.poppins14Regular,
            color = MaterialTheme.localColors.white_alpha08,
            modifier = Modifier.constrainAs(content) {
                start.linkTo(title.start)
                bottom.linkTo(icon.bottom, goneMargin = 18.dp)
                end.linkTo(icon.start, goneMargin = 22.dp)
                width = Dimension.fillToConstraints
            }
        )

        SimpleIcon(
            iconResId = R.drawable.ic_arrow_forward,
            modifier = Modifier
                .constrainAs(icon) {
                    bottom.linkTo(parent.bottom, 18.dp)
                    end.linkTo(parent.end, 22.dp)
                    visibility = if (model?.optionCount == null) Visible else Gone
                }
                .size(dimensionResource(id = R.dimen.dp19))
                .clickable { onClick.invoke(model?.nextLineId.orEmpty()) }
        )

        Talker(
            imgRes = talker,
            modifier = Modifier.constrainAs(image) {
                bottom.linkTo(content.top, 9.dp)
                end.linkTo(parent.end)
                visibility = if (model?.talker != null) Visible else Gone
            }
        )
    }
}

@Composable
fun InteractiveTextBox(
    model: DialogueXml?,
    talker: String,
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
                model?.talker?.let {
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
                        text = model?.text.orEmpty(),
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
                    if (model?.optionCount == null) {
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
                                .clickable { onClick.invoke(model?.nextLineId.orEmpty()) }
                        )
                    }
                }
            }
        }
        if (model?.talker != null) {
            Talker(
                modifier = Modifier.align(Alignment.TopEnd),
                imgRes = talker
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

@Composable
fun InteractiveScreenToolbar(
    gemCount: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.dp36),
                end = dimensionResource(id = R.dimen.dp46)
            )
            .fillMaxWidth()
    ) {
        SimpleImage(imgResId = R.drawable.ic_hitreads)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(MaterialTheme.localShapes.roundedDp4)
                .border(
                    width = dimensionResource(id = R.dimen.dp1),
                    color = MaterialTheme.localColors.white,
                    MaterialTheme.localShapes.roundedDp4
                )
                .background(MaterialTheme.localColors.black)
                .height(IntrinsicSize.Min)
                .clickable(onClick = onClick)
        ) {
            SimpleIcon(
                iconResId = R.drawable.ic_diamond,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dp6))
            )
            Divider(
                color = MaterialTheme.localColors.white,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dimensionResource(id = R.dimen.dp1))
            )
            Text(
                text = gemCount.toString(),
                style = MaterialTheme.localTextStyles.poppins12Bold,
                color = MaterialTheme.localColors.white,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dp6))
            )
        }
    }
}

enum class InteractiveScreenAction {
    CREATE_FAVORITE,
    SHARE,
    GO_TO_BEGINNING,
    NEXT_EPISODE,
    CREATE_COMMENT
}

@Preview
@Composable
fun InteractiveScreenPreview() {
    InteractiveScreenContent(original = IndexOriginal(
        indexAuthor = IndexAuthor(id = 1716, name = "Thurman Hancock"),
        banner = "curae",
        cover = "audire",
        description = "tamquam",
        id = 5159,
        isActual = false,
        isLocked = false,
        likeCount = 5176,
        commentCount = 2756,
        viewCount = 6357,
        indexPackage = IndexPackage(
            id = 6462,
            price = 7301,
            priceType = "legere"
        ),
        sort = 6620,
        status = false,
        title = "praesent",
        type = "duis",
        indexUserData = IndexUserData(isFav = false, isPurchase = false),
        indexTags = listOf(),
        hashtag = "blandit",
        subtitle = "ferri",
        episodeCount = 7855,
        isNew = false,
        barcode = "dolore",
        continueReadingEpisode = null,
        episodes = listOf()
    ), readingUiState = ReadingUiState(
        episode = null,
        allComments = listOf(),
        commentsLikedByMe = listOf(),
        isLoading = false,
        error = null
    ), action = {})
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
