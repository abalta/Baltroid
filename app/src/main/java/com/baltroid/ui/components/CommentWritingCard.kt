package com.baltroid.ui.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.ui.common.CroppedImage
import com.baltroid.ui.common.HorizontalSpacer
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun CommentWritingCard(
    onBackClick: () -> Unit,
    sendComment: (comment: String) -> Unit
) {
    var comment by rememberSaveable {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.localDimens.dp20,
                vertical = MaterialTheme.localDimens.dp22
            )
            .systemBarsPadding()
            .fillMaxSize()
            .clip(MaterialTheme.localShapes.roundedDp10)
            .border(
                width = MaterialTheme.localDimens.dp1,
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
                .padding(top = MaterialTheme.localDimens.dp14, end = MaterialTheme.localDimens.dp22)
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
                            start = MaterialTheme.localDimens.dp29,
                            top = MaterialTheme.localDimens.dp27
                        )
                ) {
                    CroppedImage(
                        imgResId = R.drawable.woods_image,
                        modifier = Modifier
                            .size(MaterialTheme.localDimens.dp48)
                            .clip(MaterialTheme.localShapes.circleShape)
                    )
                    HorizontalSpacer(width = MaterialTheme.localDimens.dp13)
                    Column(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                    ) {
                        Text(
                            text = "#KGD B12",
                            style = MaterialTheme.localTextStyles.writingCardInfo
                        )
                        Text(
                            text = "SELEN PEKMEZCİ",
                            style = MaterialTheme.localTextStyles.subtitle
                        )
                    }
                }
                VerticalSpacer(height = MaterialTheme.localDimens.dp15)
                Divider(
                    color = MaterialTheme.localColors.white,
                    thickness = MaterialTheme.localDimens.dp0_5,
                    modifier = Modifier.padding(
                        start = MaterialTheme.localDimens.dp25,
                        end = MaterialTheme.localDimens.dp42
                    )
                )
                VerticalSpacer(height = MaterialTheme.localDimens.dp15)
                BasicTextField(
                    value = "First of all please publish this so I can buy it for my library! second, there definitely needs to be a part 2 or second book because I’m hooked.",
                    onValueChange = { comment = if (comment.length < 1200) it else comment },
                    textStyle = MaterialTheme.localTextStyles.writingCardBody,
                    cursorBrush = SolidColor(MaterialTheme.localColors.white_alpha08),
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.localDimens.dp27,
                            end = MaterialTheme.localDimens.dp39
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
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(
                            start = MaterialTheme.localDimens.dp29,
                            bottom = MaterialTheme.localDimens.dp16
                        )
                )
                Text(
                    text = stringResource(id = R.string.send),
                    style = MaterialTheme.localTextStyles.writingCardButtonText,
                    modifier = Modifier
                        .padding(
                            end = MaterialTheme.localDimens.dp20,
                            bottom = MaterialTheme.localDimens.dp16
                        )
                        .clip(MaterialTheme.localShapes.roundedDp4)
                        .background(MaterialTheme.localColors.white_alpha08)
                        .clickable { sendComment.invoke(comment) }
                        .padding(
                            vertical = MaterialTheme.localDimens.dp8,
                            horizontal = MaterialTheme.localDimens.dp18
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
    CommentWritingCard({}) {}
}

