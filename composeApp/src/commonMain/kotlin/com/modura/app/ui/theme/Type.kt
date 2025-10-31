package com.modura.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import modura.composeapp.generated.resources.Res
import modura.composeapp.generated.resources.pretendard_black
import modura.composeapp.generated.resources.pretendard_bold
import modura.composeapp.generated.resources.pretendard_light
import modura.composeapp.generated.resources.pretendard_medium
import modura.composeapp.generated.resources.pretendard_regular
import org.jetbrains.compose.resources.Font

@Composable
fun pretendardFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.pretendard_light, FontWeight.Light),
        Font(Res.font.pretendard_regular, FontWeight.Normal), // Normal == Regular (W400)
        Font(Res.font.pretendard_medium, FontWeight.Medium),
        Font(Res.font.pretendard_bold, FontWeight.Bold),
        Font(Res.font.pretendard_black, FontWeight.Black)
    )
}

@Composable
fun AppTypography(): Typography {
    val pretendard = pretendardFontFamily()

    return Typography(
        headlineLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
        ),

        headlineMedium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        ),

        headlineSmall = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
        ),

        titleLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),

        bodyLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        ),

        titleMedium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),

        bodyMedium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),

        labelLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
        ),

        titleSmall = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
        ),

        bodySmall = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),

        labelSmall = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        )
    )
}

val Typography.light8: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = pretendardFontFamily(),
        fontWeight = FontWeight.Light,
        fontSize = 8.sp
    )
