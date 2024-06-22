package com.baltroid.designsystem.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.baltroid.core.designsystem.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

val montserratFamily = FontFamily(
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
)
internal val Typography.captionStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )

internal val Typography.captionMediumStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.captionSmallStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.smallBoldStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.mediumBoldStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.bodyStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 18.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.rowTitleStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.buttonTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.subTitleTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 17.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.smallTextStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.headerStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.headerBoldStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.ratingBoldStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.badgeStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 8.sp,
        fontWeight = FontWeight.Bold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.mediumTitleStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.menuStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
val Typography.regularStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.mediumStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
internal val Typography.mediumSmallStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
val Typography.mediumBigStyle: TextStyle
    get() = TextStyle(
        fontFamily = montserratFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )


