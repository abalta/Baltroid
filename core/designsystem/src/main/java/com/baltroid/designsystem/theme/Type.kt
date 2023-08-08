package com.baltroid.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.baltroid.core.designsystem.R

internal val sfProFamily = FontFamily(
    Font(R.font.sf_pro_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.sf_pro_bold, FontWeight.Bold),
    Font(R.font.sf_pro_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.sf_pro_medium, FontWeight.Medium),
    Font(R.font.sf_pro_regular, FontWeight.Normal),
    Font(R.font.sf_pro_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.sf_pro_thin_italic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.sf_pro_ultra_light_italic, FontWeight.ExtraLight, FontStyle.Italic)
)

internal val Typography.h1TitleStyle: TextStyle
    get() = TextStyle(
        fontFamily = sfProFamily,
        fontSize = 34.sp,
        lineHeight = 42.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (0.22).sp
    )

internal val Typography.h2TitleStyle: TextStyle
    get() = TextStyle(
        fontFamily = sfProFamily,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = (0.18).sp
    )

internal val Typography.h3TitleStyle: TextStyle
    get() = TextStyle(
        fontFamily = sfProFamily,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = (0.14).sp
    )

internal val Typography.headlineStyle: TextStyle
    get() = TextStyle(
        fontFamily = sfProFamily,
        fontSize = 30.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (0.32).sp
    )

internal val Typography.bodyStyle: TextStyle
    get() = TextStyle(
        fontFamily = sfProFamily,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal,
    )

internal val Typography.subheadStyle: TextStyle
    get() = TextStyle(
        fontFamily = sfProFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (0.44).sp
    )

internal val Typography.captionStyle: TextStyle
    get() = TextStyle(
        fontFamily = sfProFamily,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
    )