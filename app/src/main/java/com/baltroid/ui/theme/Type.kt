package com.baltroid.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    val hashTag: TextStyle,
    val subtitle: TextStyle,
    val interactiveEpisode: TextStyle,
    val menuBarTitle: TextStyle,
    val menuBarSubTitle: TextStyle,
    val subtitleGrotesk: TextStyle,
    val storyItemTitle: TextStyle,
    val body: TextStyle,
    val episodeText: TextStyle,
    val authorText: TextStyle,
    val summaryInfoText: TextStyle,
    val detailSummaryText: TextStyle,
    val sideBarIconText: TextStyle,
    val menuButtonText: TextStyle,
    val topBarIconText: TextStyle,
    val summaryIconText: TextStyle,
    val episodeSectionIconText: TextStyle,
    val episodeSelectedText: TextStyle,
    val episodeUnselectedText: TextStyle,
    val homeScreenTabText: TextStyle,
    val splashScreenText: TextStyle,
    val isStoryNewText: TextStyle,
    val dateText: TextStyle,
    val commentText: TextStyle,
    val interactiveHashtag: TextStyle,
    val imageCardText: TextStyle,
    val profileScreenUserInfo: TextStyle,
    val forgotPassword: TextStyle,
    val writingCardInfo: TextStyle,
    val writingCardBody: TextStyle,
    val writingCardButtonText: TextStyle,
    val signInTextWhite: TextStyle,
    val signUpTextOrangeGrotesk: TextStyle,
    val signUpTextOrange: TextStyle,
    val filterScreenPinkText: TextStyle,
    val commentsScreenTabText: TextStyle,
    val passwordInfo: TextStyle,
    val readingPurpleSubTitle: TextStyle
)

val DarkThemeTextStyles = TextStyles(
    title = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Light,
        fontSize = 17.sp,
        color = White
    ),
    mediumTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        color = White
    ),
    authorText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        color = White_alpha09
    ),
    detailSummaryText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 13.5.sp,
        color = White
    ),
    menuBarTitle = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        color = White_alpha09
    ),
    storyItemTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 11.5.sp,
        color = White_alpha05,
        lineHeight = 13.sp,
        textAlign = TextAlign.Center
    ),
    menuBarSubTitle = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        color = White_alpha09
    ),
    hashTag = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Light,
        fontSize = 100.sp,
        color = White
    ),
    extraBoldTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        color = White
    ),
    subtitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.5.sp,
        color = White
    ),
    interactiveEpisode = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.5.sp,
        color = White_alpha07
    ),
    subtitleGrotesk = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        color = White
    ),
    body = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.5.sp,
        color = White_alpha08
    ),
    summaryIconText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        color = White
    ),
    summaryInfoText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = White
    ),
    episodeText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = White
    ),
    sideBarIconText = TextStyle(
        fontFamily = Poppins,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        color = White
    ),
    menuButtonText = TextStyle(
        fontFamily = Poppins,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = White
    ),
    topBarIconText = TextStyle(
        fontFamily = Poppins,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = Black
    ),
    episodeSectionIconText = TextStyle(
        fontFamily = Poppins,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = White_alpha04
    ),
    episodeSelectedText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = White
    ),
    episodeUnselectedText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = White_alpha05
    ),
    homeScreenTabText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = White_alpha07
    ),
    splashScreenText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        color = White
    ),
    isStoryNewText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = Black
    ),
    dateText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        color = White_alpha07
    ),
    commentText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = White_alpha08
    ),
    interactiveHashtag = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        color = White
    ),
    imageCardText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = White
    ),
    profileScreenUserInfo = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = White
    ),
    forgotPassword = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Orange
    ),
    writingCardBody = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = White_alpha08
    ),
    writingCardInfo = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = White_alpha06
    ),
    writingCardButtonText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = Black
    ),
    signInTextWhite = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        color = White_alpha09
    ),
    signUpTextOrange = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        color = Orange
    ),
    filterScreenPinkText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Pink_alpha09
    ),
    commentsScreenTabText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = White_alpha09
    ),
    signUpTextOrangeGrotesk = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Orange
    ),
    passwordInfo = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = White_alpha03
    ),
    readingPurpleSubTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.5.sp,
        color = Purple_80
    )
)

val LightThemeTextStyles = TextStyles(
    title = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Light,
        fontSize = 17.sp,
        color = White
    ),
    mediumTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        color = White
    ),
    authorText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        color = White_alpha09
    ),
    detailSummaryText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 13.5.sp,
        color = White
    ),
    menuBarTitle = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        color = White_alpha09
    ),
    storyItemTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 11.5.sp,
        color = White_alpha05,
        lineHeight = 13.sp,
        textAlign = TextAlign.Center
    ),
    menuBarSubTitle = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        color = White_alpha09
    ),
    hashTag = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Light,
        fontSize = 100.sp,
        color = White
    ),
    extraBoldTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        color = White
    ),
    subtitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.5.sp,
        color = White
    ),
    interactiveEpisode = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.5.sp,
        color = White_alpha07
    ),
    subtitleGrotesk = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.sp,
        color = White
    ),
    body = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.5.sp,
        color = White_alpha08
    ),
    summaryIconText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        color = White
    ),
    summaryInfoText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = White
    ),
    episodeText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = White
    ),
    sideBarIconText = TextStyle(
        fontFamily = Poppins,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        color = White
    ),
    menuButtonText = TextStyle(
        fontFamily = Poppins,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = White
    ),
    topBarIconText = TextStyle(
        fontFamily = Poppins,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = Black
    ),
    episodeSectionIconText = TextStyle(
        fontFamily = Poppins,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = White_alpha04
    ),
    episodeSelectedText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = White
    ),
    episodeUnselectedText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = White_alpha05
    ),
    homeScreenTabText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = White_alpha07
    ),
    splashScreenText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        color = White
    ),
    isStoryNewText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = Black
    ),
    dateText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        color = White_alpha07
    ),
    commentText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = White_alpha08
    ),
    interactiveHashtag = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        color = White
    ),
    imageCardText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = White
    ),
    profileScreenUserInfo = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = White
    ),
    forgotPassword = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Orange
    ),
    writingCardBody = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = White_alpha08
    ),
    writingCardInfo = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = White_alpha06
    ),
    writingCardButtonText = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = Black
    ),
    signInTextWhite = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        color = White_alpha09
    ),
    signUpTextOrange = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        color = Orange
    ),
    filterScreenPinkText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Pink_alpha09
    ),
    commentsScreenTabText = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = White_alpha09
    ),
    signUpTextOrangeGrotesk = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = Orange
    ),
    passwordInfo = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = White_alpha03
    ),
    readingPurpleSubTitle = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.5.sp,
        color = Purple_80
    )
)

data class Dimensions(
    val default: Dp = 0.dp,
    val minScreenHeight: Dp = 700.dp,
    val minSideBarHeight: Dp = 500.dp,
    val minDetailScreenHeight: Dp = 650.dp,
    val dp0_5: Dp = 0.5.dp,
    val dp1: Dp = 1.dp,
    val dp2: Dp = 2.dp,
    val dp1_5: Dp = 1.5.dp,
    val dp3: Dp = 3.dp,
    val dp3_5: Dp = 3.5.dp,
    val dp4: Dp = 4.dp,
    val dp4_5: Dp = 4.5.dp,
    val dp5: Dp = 5.dp,
    val dp6: Dp = 6.dp,
    val dp6_5: Dp = 6.5.dp,
    val dp7: Dp = 7.dp,
    val dp8: Dp = 8.dp,
    val dp9: Dp = 9.dp,
    val dp10: Dp = 10.dp,
    val dp10_5: Dp = 10.5.dp,
    val dp11: Dp = 11.dp,
    val dp11_5: Dp = 11.5.dp,
    val dp12: Dp = 12.dp,
    val dp12_5: Dp = 12.5.dp,
    val dp13: Dp = 13.dp,
    val dp13_5: Dp = 13.5.dp,
    val dp14: Dp = 14.dp,
    val dp14_5: Dp = 14.5.dp,
    val dp15: Dp = 15.dp,
    val dp16: Dp = 16.dp,
    val dp16_5: Dp = 16.5.dp,
    val dp17: Dp = 17.dp,
    val dp18: Dp = 18.dp,
    val dp18_5: Dp = 18.5.dp,
    val dp19: Dp = 19.dp,
    val dp20: Dp = 20.dp,
    val dp20_5: Dp = 20.5.dp,
    val dp21: Dp = 21.dp,
    val dp22: Dp = 22.dp,
    val dp23: Dp = 23.dp,
    val dp24: Dp = 24.dp,
    val dp25: Dp = 25.dp,
    val dp26: Dp = 26.dp,
    val dp27: Dp = 27.dp,
    val dp28: Dp = 28.dp,
    val dp28_5: Dp = 28.5.dp,
    val dp29: Dp = 29.dp,
    val dp29_5: Dp = 29.5.dp,
    val dp30: Dp = 30.dp,
    val dp31: Dp = 31.dp,
    val dp32: Dp = 32.dp,
    val dp33: Dp = 33.dp,
    val dp34: Dp = 34.dp,
    val dp35: Dp = 35.dp,
    val dp36: Dp = 36.dp,
    val dp38: Dp = 38.dp,
    val dp39: Dp = 39.dp,
    val dp40: Dp = 40.dp,
    val dp42: Dp = 42.dp,
    val dp43: Dp = 43.dp,
    val dp44: Dp = 44.dp,
    val dp47: Dp = 47.dp,
    val dp48: Dp = 48.dp,
    val dp50: Dp = 50.dp,
    val dp51: Dp = 51.dp,
    val dp54: Dp = 54.dp,
    val dp54_5: Dp = 54.5.dp,
    val dp67: Dp = 67.dp,
    val dp70: Dp = 70.dp,
    val dp76: Dp = 76.dp,
    val dp78: Dp = 78.dp,
    val dp102: Dp = 102.dp,
    val dp107: Dp = 107.dp,
    val dp111: Dp = 111.dp,
    val dp117: Dp = 117.dp,
    val dp127: Dp = 127.dp,
    val dp155: Dp = 155.dp,
    val dp177: Dp = 177.dp,
    val dp244: Dp = 244.dp,
    val dp249: Dp = 249.dp,
    val dp348: Dp = 348.dp,
)

val LocalTextStyles = compositionLocalOf {
    DarkThemeTextStyles
}

val LocalDimensions = staticCompositionLocalOf {
    Dimensions()
}

val MaterialTheme.localDimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current

val MaterialTheme.localTextStyles
    @Composable
    @ReadOnlyComposable
    get() = LocalTextStyles.current