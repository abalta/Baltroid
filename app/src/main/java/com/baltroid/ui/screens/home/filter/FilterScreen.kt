package com.baltroid.ui.screens.home.filter

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.baltroid.apps.R
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilterScreen(
    onBackClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .navigationBarsPadding()
    ) {
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
        MenuBar(
            title = stringResource(id = R.string.filter),
            iconResId = R.drawable.ic_filter,
            onBackClick = onBackClick,
            modifier = Modifier
        )
    }
}

@Composable
private fun FilterScreenGenreItem(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = MaterialTheme.localDimens.default,
    selectedColor: Color = MaterialTheme.localColors.purple,
    unSelectedColor: Color = MaterialTheme.localColors.white_alpha05
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.localTextStyles.isStoryNewText,
        modifier = modifier
            .clip(MaterialTheme.localShapes.roundedDp4)
            .background(if (isSelected) selectedColor else unSelectedColor)
            .padding(
                vertical = MaterialTheme.localDimens.dp6,
                horizontal = horizontalPadding
            )
    )
}

@Preview
@Composable
fun FilterScreenPreview() {
    ConstraintLayout(
        modifier = Modifier.width(300.dp)
    ) {
        val localDimens = MaterialTheme.localDimens
        val (
            genre1, genre2, genre3, genre4,
            genre5, genre6, genre7, genre8,
            genre9, genre10, genre11, genre12
        ) = createRefs()

        createHorizontalChain(genre1, genre2, genre3, genre4, chainStyle = ChainStyle.SpreadInside)

        FilterScreenGenreItem(
            text = "ROMANTİK",
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier
                .constrainAs(genre1) {
                    top.linkTo(parent.top)
                }
        )
        FilterScreenGenreItem(
            text = "GENÇLİK",
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre2) {
                top.linkTo(genre1.top)
            }
        )
        FilterScreenGenreItem(
            text = "MACERA",
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre3) {
                top.linkTo(genre1.top)
            }
        )
        FilterScreenGenreItem(
            text = "GİZEM",
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre4) {
                top.linkTo(genre1.top)
            }
        )
        FilterScreenGenreItem(
            text = "YOLCULUK",
            isSelected = true,
            modifier = Modifier.constrainAs(genre5) {
                start.linkTo(genre1.start)
                end.linkTo(genre1.end)
                top.linkTo(genre1.bottom, margin = localDimens.dp9)
                width = Dimension.fillToConstraints
            }
        )
        FilterScreenGenreItem(
            text = "EFSANE",
            isSelected = true,
            modifier = Modifier.constrainAs(genre6) {
                start.linkTo(genre2.start)
                end.linkTo(genre2.end)
                top.linkTo(genre5.top)
                width = Dimension.fillToConstraints
            }
        )
        FilterScreenGenreItem(
            text = "POLİSİYE",
            isSelected = true,
            modifier = Modifier.constrainAs(genre7) {
                start.linkTo(genre3.start)
                end.linkTo(genre3.end)
                top.linkTo(genre5.top)
                width = Dimension.fillToConstraints
            }
        )
        FilterScreenGenreItem(
            text = "AŞK",
            isSelected = true,
            modifier = Modifier.constrainAs(genre8) {
                start.linkTo(genre4.start)
                end.linkTo(genre4.end)
                top.linkTo(genre5.top)
                width = Dimension.fillToConstraints
            }
        )
        FilterScreenGenreItem(
            text = "LİSE",
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre9) {
                start.linkTo(genre5.start)
                top.linkTo(genre5.bottom, margin = localDimens.dp9)
            }
        )
        FilterScreenGenreItem(
            text = "BİLİM KURGU",
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre10) {
                end.linkTo(genre6.end)
                top.linkTo(genre9.top)
            }
        )
        FilterScreenGenreItem(
            text = "UZAY",
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre11) {
                start.linkTo(genre7.start)
                top.linkTo(genre9.top)
            }
        )
        FilterScreenGenreItem(
            text = "EĞLENCE",
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre12) {
                end.linkTo(genre8.end)
                top.linkTo(genre9.top)
            }
        )
    }
}

