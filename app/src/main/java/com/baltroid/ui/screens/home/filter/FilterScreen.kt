package com.baltroid.ui.screens.home.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.baltroid.apps.R
import com.baltroid.ui.components.MenuBar
import com.baltroid.ui.screens.menu.login.TextBetweenDividers
import com.baltroid.ui.screens.viewmodels.OriginalViewModel
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.hitreads.core.model.Tag

@Composable
fun FilterScreen(
    viewModel: OriginalViewModel,
    onBackClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.loadFilters()
    }

    val filter = viewModel.filter

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
            viewModel.uiStateFilter.collectAsStateWithLifecycle().value.tagUiModels,
            selectedIds = filter,
            remove = { id ->
                filter.remove(id)
            },
            add = { id ->
                filter.add(id)
            },
            modifier = Modifier.constrainAs(genreSection) {
                top.linkTo(topBar.bottom, localDimens.dp44)
                start.linkTo(topBar.start)
                end.linkTo(topBar.end)
                width = Dimension.percent(0.7f)
            }
        )

        TextBetweenDividers(
            text = stringResource(id = R.string.txt_filter),
            textStyle = MaterialTheme.localTextStyles.signInTextWhite,
            onClick = {
                onBackClick.invoke()
            },
            modifier = Modifier
                .constrainAs(apply) {
                    bottom.linkTo(bottomGuideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
private fun FilterScreenGenreItem(
    tag: Tag,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.localColors.purple,
    unSelectedColor: Color = MaterialTheme.localColors.white_alpha05,
    onClick: (Int) -> Unit
) {
    Text(
        text = tag.name,
        textAlign = TextAlign.Center,
        style = MaterialTheme.localTextStyles.isStoryNewText,
        modifier = modifier
            .clickable { onClick.invoke(tag.id) }
            .clip(MaterialTheme.localShapes.roundedDp4)
            .background(if (isSelected) selectedColor else unSelectedColor)
            .padding(vertical = MaterialTheme.localDimens.dp12_5)
    )
}

@Composable
fun GenreSection(
    tagList: List<Tag>,
    selectedIds: List<Int>,
    remove: (Int) -> Unit,
    add: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(MaterialTheme.localDimens.dp54_5),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp8),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp10),
        modifier = modifier
    ) {
        items(tagList) { item ->
            FilterScreenGenreItem(
                tag = item, isSelected = selectedIds.contains(item.id)
            ) { id ->
                if (selectedIds.contains(id)) {
                    remove(id)
                } else {
                    add(id)
                }
            }
        }
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(heightDp = 650)
@Preview(widthDp = 360, heightDp = 540)
@Composable
fun FilterScreenPreview() {

}

