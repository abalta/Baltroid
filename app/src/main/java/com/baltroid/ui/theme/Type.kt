package com.baltroid.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.baltroid.apps.R

val Poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
    Font(R.font.poppins_light, FontWeight.Light)
)

val SpaceGrotesk = FontFamily(
    Font(R.font.space_grotesk_medium, FontWeight.Medium),
    Font(R.font.space_grotesk_regular, FontWeight.Normal),
    Font(R.font.space_grotesk_light, FontWeight.Light)
)

data class TextStyles(
    val title: TextStyle,
    val mediumTitle: TextStyle,
    val extraBoldTitle: TextStyle,
    val subtitle: TextStyle,
    val menuBarTitle: TextStyle,
    val menuBarSubTitle: TextStyle,
    val subtitleGrotesk: TextStyle,
    val storyItemTitle: TextStyle,
    val body: TextStyle,
    val episodeText: TextStyle,
    val authorText: TextStyle,
    val detailSummaryText: TextStyle,
    val sideBarIconText: TextStyle,
    val menuButtonText: TextStyle,
    val topBarIconText: TextStyle,
    val summaryIconText: TextStyle,
    val episodeSelectedText: TextStyle,
    val homeScreenTabText: TextStyle,
    val splashScreenText: TextStyle,
    val isStoryNewText: TextStyle,
    val dateText: TextStyle,
    val interactiveHashtag: TextStyle,
    val imageCardText: TextStyle,
    val profileScreenUserInfo: TextStyle,
    val writingCardInfo: TextStyle,
    val writingCardBody: TextStyle,
    val writingCardButtonText: TextStyle,
    val signInTextWhite: TextStyle,
    val originalItemTitle: TextStyle,
    val episodesSheet: TextStyle,
    val episodeSheetEpisode: TextStyle
)

val HitReadsTextStyles = TextStyles(
    title = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Light,
        fontSize = 17.sp
    ),
    mediumTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp
    ),
    authorText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    ),
    detailSummaryText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 13.5.sp
    ),
    menuBarTitle = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    storyItemTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 11.5.sp,
        lineHeight = 13.sp
    ),
    menuBarSubTitle = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    extraBoldTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    ),
    subtitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.5.sp
    ),
    subtitleGrotesk = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.sp
    ),
    body = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.5.sp
    ),
    summaryIconText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp
    ),
    episodeText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    sideBarIconText = TextStyle(
        fontFamily = Poppins,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal
    ),
    menuButtonText = TextStyle(
        fontFamily = Poppins,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    ),
    topBarIconText = TextStyle(
        fontFamily = Poppins,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium
    ),
    episodeSelectedText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    ),
    homeScreenTabText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium
    ),
    splashScreenText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    isStoryNewText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium
    ),
    dateText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
    interactiveHashtag = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    ),
    imageCardText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    profileScreenUserInfo = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    writingCardBody = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    writingCardInfo = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    writingCardButtonText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    signInTextWhite = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    originalItemTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    episodesSheet = TextStyle(
        fontFamily = Poppins,
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal
    ),
    episodeSheetEpisode = TextStyle(
        fontFamily = Poppins,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    )
)

val LocalTextStyles = compositionLocalOf {
    HitReadsTextStyles
}

val MaterialTheme.localTextStyles
    @Composable
    @ReadOnlyComposable
    get() = LocalTextStyles.current