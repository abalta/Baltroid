package com.baltroid.ui.screens.home.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.baltroid.apps.R
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.screens.menu.login.TextBetweenDividers
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun FilterScreen(
    applyFilter: (selectedGenres: List<Int>) -> Unit,
    onBackClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .systemBarsPadding()
    ) {

        val (topBar, genreSection, info, apply) = createRefs()
        val bottomGuideLine = createGuidelineFromBottom(0.1f)
        val localDimens = MaterialTheme.localDimens

        MenuBar(
            title = stringResource(id = R.string.filter),
            iconResId = R.drawable.ic_filter,
            onBackClick = onBackClick,
            modifier = Modifier.constrainAs(topBar) {
                top.linkTo(parent.top, margin = localDimens.dp36)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        GenreSection(
            modifier = Modifier.constrainAs(genreSection) {
                start.linkTo(topBar.start, margin = localDimens.dp11)
                end.linkTo(topBar.end, margin = localDimens.dp11)
                top.linkTo(topBar.bottom, margin = localDimens.dp47)
                width = Dimension.fillToConstraints
            }
        )
        TextBetweenDividers(
            text = "Seçili Kategorilerde 24 Hikaye Var",
            textStyle = MaterialTheme.localTextStyles.filterScreenPinkText,
            modifier = Modifier.constrainAs(info) {
                bottom.linkTo(apply.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        TextBetweenDividers(
            text = "SEÇİMLERE GÖRE LİSTELE",
            textStyle = MaterialTheme.localTextStyles.signInTextWhite,
            modifier = Modifier
                .constrainAs(apply) {
                    bottom.linkTo(bottomGuideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable { applyFilter.invoke(listOf()) }
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
    unSelectedColor: Color = MaterialTheme.localColors.white_alpha05,
    onClick: () -> Unit
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.localTextStyles.isStoryNewText,
        modifier = modifier
            .clip(MaterialTheme.localShapes.roundedDp4)
            .background(if (isSelected) selectedColor else unSelectedColor)
            .padding(
                vertical = MaterialTheme.localDimens.dp6, horizontal = horizontalPadding
            )
    )
}

@Composable
fun GenreSection(
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val localDimens = MaterialTheme.localDimens
        val (genre1, genre2, genre3, genre4, genre5, genre6, genre7, genre8, genre9, genre10, genre11, genre12) = createRefs()

        createHorizontalChain(genre1, genre2, genre3, genre4, chainStyle = ChainStyle.SpreadInside)

        FilterScreenGenreItem(text = stringResource(id = R.string.romantic),
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre1) {
                top.linkTo(parent.top)
            }) {

        }
        FilterScreenGenreItem(text = stringResource(id = R.string.teen),
            isSelected = false,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre2) {
                top.linkTo(genre1.top)
            }) {}
        FilterScreenGenreItem(text = stringResource(id = R.string.adventure),
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre3) {
                top.linkTo(genre1.top)
            }) {}
        FilterScreenGenreItem(text = stringResource(id = R.string.mystery),
            isSelected = false,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre4) {
                top.linkTo(genre1.top)
            }) {}
        FilterScreenGenreItem(text = stringResource(id = R.string.journey),
            isSelected = true,
            modifier = Modifier.constrainAs(genre5) {
                start.linkTo(genre1.start)
                end.linkTo(genre1.end)
                top.linkTo(genre1.bottom, margin = localDimens.dp9)
                width = Dimension.fillToConstraints
            }) {}
        FilterScreenGenreItem(text = stringResource(id = R.string.legend),
            isSelected = false,
            modifier = Modifier.constrainAs(genre6) {
                start.linkTo(genre2.start)
                end.linkTo(genre2.end)
                top.linkTo(genre5.top)
                width = Dimension.fillToConstraints
            }) {}
        FilterScreenGenreItem(text = stringResource(id = R.string.detective),
            isSelected = true,
            modifier = Modifier.constrainAs(genre7) {
                start.linkTo(genre3.start)
                end.linkTo(genre3.end)
                top.linkTo(genre5.top)
                width = Dimension.fillToConstraints
            }) {}
        FilterScreenGenreItem(text = stringResource(id = R.string.love),
            isSelected = true,
            modifier = Modifier.constrainAs(genre8) {
                start.linkTo(genre4.start)
                end.linkTo(genre4.end)
                top.linkTo(genre5.top)
                width = Dimension.fillToConstraints
            }) {}
        FilterScreenGenreItem(text = stringResource(id = R.string.high_school),
            isSelected = false,
            horizontalPadding = MaterialTheme.localDimens.dp16,
            modifier = Modifier.constrainAs(genre9) {
                start.linkTo(genre5.start)
                top.linkTo(genre5.bottom, margin = localDimens.dp9)
            }) {}
        FilterScreenGenreItem(text = stringResource(id = R.string.sci_fi),
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre10) {
                end.linkTo(genre6.end)
                top.linkTo(genre9.top)
            }) {}
        FilterScreenGenreItem(text = stringResource(id = R.string.space),
            isSelected = true,
            horizontalPadding = MaterialTheme.localDimens.dp14,
            modifier = Modifier.constrainAs(genre11) {
                start.linkTo(genre7.start)
                top.linkTo(genre9.top)
            }) {}
        FilterScreenGenreItem(text = stringResource(id = R.string.funn),
            isSelected = false,
            horizontalPadding = MaterialTheme.localDimens.dp10,
            modifier = Modifier.constrainAs(genre12) {
                end.linkTo(genre8.end)
                top.linkTo(genre9.top)
            }) {}
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun FilterScreenPreview() {
    FilterScreen(onBackClick = {}, applyFilter = {})
}

