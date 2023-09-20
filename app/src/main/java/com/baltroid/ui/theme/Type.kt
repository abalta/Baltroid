package com.baltroid.ui.theme

import androidx.compose.material3.MaterialTheme
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
    val poppins10Regular: TextStyle,
    val poppins10SemiBold: TextStyle,
    val poppins11Medium: TextStyle,
    val poppins12Regular: TextStyle,
    val poppins12Medium: TextStyle,
    val poppins12Bold: TextStyle,
    val poppins13Medium: TextStyle,
    val poppins14Regular: TextStyle,
    val poppins14Medium: TextStyle,
    val poppins15Medium: TextStyle,
    val poppins15Bold: TextStyle,
    val poppins16Regular: TextStyle,
    val poppins17Light: TextStyle,
    val poppins17Regular: TextStyle,
    val poppins17Medium: TextStyle,
    val poppins18Regular: TextStyle,
    val poppins20ExtraBold: TextStyle,
    val spaceGrotesk11Medium: TextStyle,
    val spaceGrotesk13Medium: TextStyle,
    val spaceGrotesk14Regular: TextStyle,
    val spaceGrotesk14Medium: TextStyle,
    val spaceGrotesk15Medium: TextStyle,
    val spaceGrotesk16Medium: TextStyle,
    val spaceGrotesk18Regular: TextStyle,
    val spaceGrotesk18Medium: TextStyle,
    val spaceGrotesk20Medium: TextStyle,
    val spaceGrotesk22Medium: TextStyle,
    val spaceGrotesk24Medium: TextStyle,
)

val HitReadsTextStyles = TextStyles(
    poppins17Light = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Light,
        fontSize = 17.sp
    ),
    poppins17Medium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp
    ),
    spaceGrotesk15Medium = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    ),
    spaceGrotesk14Regular = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    spaceGrotesk24Medium = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    poppins12Medium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 13.sp
    ),
    spaceGrotesk20Medium = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    poppins20ExtraBold = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp
    ),
    poppins13Medium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp
    ),
    poppins14Regular = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 19.sp
    ),
    poppins10SemiBold = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp
    ),
    poppins12Regular = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    poppins10Regular = TextStyle(
        fontFamily = Poppins,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal
    ),
    poppins11Medium = TextStyle(
        fontFamily = Poppins,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium
    ),
    spaceGrotesk16Medium = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium
    ),
    spaceGrotesk13Medium = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium
    ),
    spaceGrotesk18Regular = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    spaceGrotesk11Medium = TextStyle(
        fontFamily = SpaceGrotesk,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium
    ),
    poppins15Medium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp
    ),
    poppins14Medium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    poppins18Regular = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    poppins16Regular = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    spaceGrotesk14Medium = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    spaceGrotesk18Medium = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    spaceGrotesk22Medium = TextStyle(
        fontFamily = SpaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    ),
    poppins12Bold = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    poppins17Regular = TextStyle(
        fontFamily = Poppins,
        fontSize = 17.sp,
        fontWeight = FontWeight.Normal
    ),
    poppins15Bold = TextStyle(
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