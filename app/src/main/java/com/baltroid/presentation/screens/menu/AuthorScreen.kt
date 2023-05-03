package com.baltroid.presentation.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.baltroid.apps.R
import com.baltroid.presentation.common.VerticalSpacer
import com.baltroid.presentation.components.IconlessMenuBar
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localDimens
import com.baltroid.ui.theme.localTextStyles

@Composable
fun AuthorScreen() {

    val scrollState = rememberScrollState()
    var selectedTab: AuthorScreenTabs by rememberSaveable {
        mutableStateOf(AuthorScreenTabs.Stories)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.localColors.black)
            .verticalScroll(scrollState)
            .navigationBarsPadding()
    ) {
        VerticalSpacer(height = MaterialTheme.localDimens.dp36)
        IconlessMenuBar(
            title = "ZEYNEP SEY",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally)
        ) {}
        VerticalSpacer(height = MaterialTheme.localDimens.dp22)
        NamelessAuthorItem(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        VerticalSpacer(height = MaterialTheme.localDimens.dp44)
        StoryAndCommentTabs(
            selectedTab = selectedTab,
            modifier = Modifier.padding(start = MaterialTheme.localDimens.dp54)
        ) { tabToSelect ->
            selectedTab = tabToSelect
        }
        VerticalSpacer(height = MaterialTheme.localDimens.dp34)
        LazyRow(
            modifier = Modifier.padding(start = MaterialTheme.localDimens.dp54_5),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp30)
        ) {
            item { StoryItemFavorites("KİMSE GERÇEK DEĞİL") }
            item { StoryItemFavorites("KİMSE GERÇEK DEĞİL") }
            item { StoryItemFavorites("KİMSE GERÇEK DEĞİL") }
        }
    }
}

@Composable
fun StoryAndCommentTabs(
    selectedTab: AuthorScreenTabs,
    modifier: Modifier = Modifier,
    onTabSelected: (AuthorScreenTabs) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.localDimens.dp33)
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .clickable {
                    onTabSelected.invoke(AuthorScreenTabs.Stories)
                }
        ) {
            Text(
                text = stringResource(id = R.string.stories),
                style = MaterialTheme.localTextStyles.menuBarSubTitle,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.localDimens.dp3)
                    .background(
                        if (selectedTab == AuthorScreenTabs.Stories) {
                            MaterialTheme.localColors.white_alpha07
                        } else {
                            MaterialTheme.localColors.transparent
                        }
                    )
            )
        }
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .clickable {
                    onTabSelected.invoke(AuthorScreenTabs.Comments)
                }
        ) {
            Text(
                text = stringResource(id = R.string.comments),
                style = MaterialTheme.localTextStyles.menuBarSubTitle,
                modifier = Modifier.width(IntrinsicSize.Max)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.localDimens.dp3)
                    .background(
                        if (selectedTab == AuthorScreenTabs.Comments) {
                            MaterialTheme.localColors.white_alpha07
                        } else {
                            MaterialTheme.localColors.transparent
                        }
                    )
            )
        }
    }
}

@Preview
@Composable
fun AuthorScreenPreview() {
    AuthorScreen()
}

enum class AuthorScreenTabs {
    Stories,
    Comments
}