package com.baltroid.ui.screens.menu.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.components.IconlessMenuBar
import com.baltroid.ui.screens.viewmodels.AuthenticationViewModel
import com.baltroid.ui.theme.Orange
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles
import com.baltroid.util.conditional
import com.hitreads.core.model.Avatar

@Composable
fun AvatarsScreen(
    viewModel: AuthenticationViewModel,
    onBackPressed: () -> Unit
) {

    val state by viewModel.profileState.collectAsStateWithLifecycle()
    val updateAvatar by viewModel.uiStateUpdateAvatar.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadAvatars()
    }
    LaunchedEffect(updateAvatar) {
        if (updateAvatar == true) {
            onBackPressed.invoke()
        }
    }
    AvatarScreenContent(
        state.avatars,
        onAvatarSelected = viewModel::updateUserAvatar,
        onBackPressed = onBackPressed
    )
}

@Composable
fun AvatarScreenContent(
    avatars: List<Avatar>,
    onAvatarSelected: (id: Int) -> Unit,
    onBackPressed: () -> Unit,
) {

    var selectedId by remember {
        mutableStateOf(-1)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        VerticalSpacer(height = dimensionResource(id = R.dimen.dp36))
        IconlessMenuBar(title = stringResource(id = R.string.pick_avatar)) {
            onBackPressed.invoke()
        }
        VerticalSpacer(height = R.dimen.dp36)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp20)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp36)),
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.dp45))
                .padding(
                    bottom = dimensionResource(
                        id = R.dimen.dp50
                    )
                )
                .weight(1f)
        ) {
            items(avatars) { avatar ->
                AsyncImage(
                    model = avatar.url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.hitreads_placeholder),
                    placeholder = painterResource(id = R.drawable.hitreads_placeholder),
                    modifier = Modifier
                        .clickable {
                            selectedId = avatar.id
                        }
                        .clip(MaterialTheme.localShapes.roundedDp45)
                        .conditional(selectedId == avatar.id) {
                            border(width = 1.dp, Orange, RoundedCornerShape(45.dp))
                        }
                )
            }
        }
        Text(
            text = stringResource(id = R.string.pick),
            style = MaterialTheme.localTextStyles.spaceGrotesk22Medium,
            color = MaterialTheme.localColors.white,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.dp50))
                .padding(horizontal = dimensionResource(id = R.dimen.dp50))
                .clickable { onAvatarSelected.invoke(selectedId) }
                .fillMaxWidth()
                .clip(MaterialTheme.localShapes.roundedDp24)
                .background(MaterialTheme.localColors.black)
                .border(
                    dimensionResource(id = R.dimen.dp1),
                    color = MaterialTheme.localColors.white_alpha05,
                    shape = MaterialTheme.localShapes.roundedDp24
                )
                .padding(vertical = dimensionResource(id = R.dimen.dp15))
        )
    }
}

@Preview
@Composable
fun PreviewAvatarScreen() {
}