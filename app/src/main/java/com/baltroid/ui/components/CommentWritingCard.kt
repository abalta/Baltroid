package com.baltroid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baltroid.apps.R

@Composable
fun CommentWritingCard(
    author: String,
    hashTag: String,
    onBackClick: () -> Unit,
    sendComment: (comment: String) -> Unit
) {
    var comment by rememberSaveable {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.dp20),
                vertical = dimensionResource(id = R.dimen.dp22)
            )
            .systemBarsPadding()
            .fillMaxSize()
            .clip(MaterialTheme.localShapes.roundedDp10)
            .border(
                width = dimensionResource(id = R.dimen.dp1),
                color = MaterialTheme.localColors.white,
                shape = MaterialTheme.localShapes.roundedDp10
            )
            .background(MaterialTheme.localColors.black)
            .imePadding()
    ) {
        SimpleImage(
            imgResId = R.drawable.ic_close,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = dimensionResource(id = R.dimen.dp14),
                    end = dimensionResource(id = R.dimen.dp22)
                )
                .clickable { onBackClick.invoke() }
        )
        Column {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.dp29),
                            top = dimensionResource(id = R.dimen.dp27)
                        )
                ) {
                    CroppedImage(
                        imgResId = R.drawable.woods_image,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.dp48))
                            .clip(MaterialTheme.localShapes.circleShape)
                    )
                    HorizontalSpacer(width = dimensionResource(id = R.dimen.dp13))
                    Column(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                    ) {
                        Text(
                            text = hashTag,
                            style = MaterialTheme.localTextStyles.writingCardInfo,
                            color = MaterialTheme.localColors.white_alpha06
                        )
                        Text(
                            text = author,
                            style = MaterialTheme.localTextStyles.subtitle,
                            color = MaterialTheme.localColors.white
                        )
                    }
                }
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp15))
                Divider(
                    color = MaterialTheme.localColors.white,
                    thickness = dimensionResource(id = R.dimen.dp1),
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.dp25),
                        end = dimensionResource(id = R.dimen.dp42)
                    )
                )
                VerticalSpacer(height = dimensionResource(id = R.dimen.dp15))
                BasicTextField(
                    value = comment,
                    onValueChange = { comment = if (comment.length < 1200) it else comment },
                    textStyle = MaterialTheme.localTextStyles.writingCardBody,
                    cursorBrush = SolidColor(MaterialTheme.localColors.white_alpha08),
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .padding(
                            start = dimensionResource(id = R.dimen.dp27),
                            end = dimensionResource(id = R.dimen.dp39)
                        )
                        .fillMaxSize()
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(
                    text = "${comment.length}/1200",
                    style = MaterialTheme.localTextStyles.writingCardInfo,
                    color = MaterialTheme.localColors.white_alpha06,
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(
                            start = dimensionResource(id = R.dimen.dp29),
                            bottom = dimensionResource(id = R.dimen.dp16)
                        )
                )
                Text(
                    text = stringResource(id = R.string.send),
                    style = MaterialTheme.localTextStyles.writingCardButtonText,
                    color = MaterialTheme.localColors.black,
                    modifier = Modifier
                        .padding(
                            end = dimensionResource(id = R.dimen.dp20),
                            bottom = dimensionResource(id = R.dimen.dp16)
                        )
                        .clip(MaterialTheme.localShapes.roundedDp4)
                        .background(MaterialTheme.localColors.white_alpha08)
                        .clickable { sendComment.invoke(comment) }
                        .padding(
                            vertical = 8.dp,
                            horizontal = dimensionResource(id = R.dimen.dp18)
                        )

                )
            }
        }
    }
}

@Preview
@Preview(widthDp = 300, heightDp = 400)
@Composable
fun CommentWritingCardPreview() {
    CommentWritingCard("SELEN PEKMEZCİ", "#KGD B12", {}) {}
}

