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
        // bold_28: 가장 크고 중요한 제목 (예: 화면 타이틀)
        headlineLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
        ),
        // medium_24: 중간 크기 제목
        headlineMedium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        ),
        // black_20: 강조하고 싶은 중간 크기 텍스트
        headlineSmall = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
        ),
        // medium_20: 본문과 연관된 제목
        titleLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        // regular_20: 큰 본문 텍스트
        bodyLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        ),
        // medium_16: 리스트 아이템 제목 등 일반적인 제목
        titleMedium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        // regular_16: 가장 일반적인 본문 텍스트
        bodyMedium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),
        // light_16: 약간 가벼운 느낌의 본문 텍스트
        labelLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
        ),
        // medium_12: 버튼이나 캡션 등 작은 크기의 텍스트
        titleSmall = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
        ),
        // regular_12: 작은 본문, 설명 텍스트
        bodySmall = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        // light_12: 가장 작고 가벼운 보조 텍스트
        labelSmall = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        )
    )
}
