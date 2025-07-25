package com.soyhenry.library.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import com.soyhenry.library.R

val UrbanistFontFamily = FontFamily(
    Font(R.font.urbanist_regular, FontWeight.Normal),
    Font(R.font.urbanist_bold, FontWeight.Bold),
    Font(R.font.urbanist_medium, FontWeight.Medium),
    Font(R.font.urbanist_bold, FontWeight.SemiBold),
    Font(R.font.urbanist_thin, FontWeight.Thin),
    Font(R.font.urbanist_light, FontWeight.Light),
    Font(R.font.urbanist_extrabold, FontWeight.ExtraBold),
    Font(R.font.urbanist_black, FontWeight.Black),
    Font(R.font.urbanist_extralight, FontWeight.ExtraLight),
    Font(R.font.urbanist_semibold, FontWeight.SemiBold),
    Font(R.font.urbanist_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.urbanist_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.urbanist_thinitalic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.urbanist_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.urbanist_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.urbanist_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.urbanist_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.urbanist_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.urbanist_regularitalic, FontWeight.Normal, FontStyle.Italic),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = UrbanistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = UrbanistFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
)

