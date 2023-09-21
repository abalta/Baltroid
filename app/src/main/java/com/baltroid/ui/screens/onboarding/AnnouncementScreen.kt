package com.baltroid.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.baltroid.apps.R
import com.baltroid.ui.common.SetLoadingState
import com.baltroid.ui.common.SimpleIcon
import com.baltroid.ui.common.SimpleImage
import com.baltroid.ui.common.VerticalSpacer
import com.baltroid.ui.theme.localColors
import com.baltroid.ui.theme.localShapes
import com.baltroid.ui.theme.localTextStyles

@Composable
fun AnnouncementScreen(
    viewModel: OnboardingViewModel,
    onClick: () -> Unit
) {
    val state by viewModel.uiStateOnboarding.collectAsStateWithLifecycle()
    SetLoadingState(isLoading = state.isLoading)
    AnnouncementScreenContent(
        state,
        onClick = onClick
    )
}

@Composable
fun AnnouncementScreenContent(
    screenState: OnboardingState,
    onClick: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (icon, announcement) = createRefs()
        val guideLine = createGuidelineFromTop(0.1f)

        AsyncImage(
            model = screenState.welcomeModel?.path,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        SimpleImage(
            imgResId = R.drawable.ic_hitreads,
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(guideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(
                    dimensionResource(id = R.dimen.dp107),
                    dimensionResource(id = R.dimen.dp102)
                )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = dimensionResource(id = R.dimen.dp24))
                .constrainAs(announcement) {
                    top.linkTo(icon.bottom, 35.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = dimensionResource(id = R.dimen.dp22))
                .clip(MaterialTheme.localShapes.roundedDp20)
                .background(MaterialTheme.localColors.white)
        ) {
            VerticalSpacer(height = R.dimen.dp43)
            Text(
                text = screenState.announcementModel?.title.toString(),
                style = MaterialTheme.localTextStyles.poppins20ExtraBold,
                color = MaterialTheme.localColors.black
            )
            VerticalSpacer(height = R.dimen.dp24)
            AsyncImage(
                model = screenState.announcementModel?.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(
                    id = R.drawable.hitreads_placeholder
                ),
                modifier = Modifier
                    .size(
                        dimensionResource(id = R.dimen.dp145),
                        dimensionResource(id = R.dimen.dp204)
                    )
                    .clip(MaterialTheme.localShapes.roundedDp9)
            )
            VerticalSpacer(height = R.dimen.dp22)
            Text(
                text = screenState.announcementModel?.message.toString(),
                style = MaterialTheme.localTextStyles.spaceGrotesk13Medium,
                color = MaterialTheme.localColors.black,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dp35))
            )
            VerticalSpacer(height = R.dimen.dp33)
            SimpleIcon(
                iconResId = R.drawable.ic_arrow_right,
                tint = MaterialTheme.localColors.black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .navigationBarsPadding()
                    .clickable { onClick.invoke() }
            )
        }
    }
}