package com.modura.app.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Gray100 = Color(0xFFF4F4F4)
val Gray300 = Color(0xffDADADA)
val Gray500 = Color(0xFFA8A8A8)
val Gray600 = Color(0xff868686)
val Gray800 = Color(0xFF383838)
val Gray900 = Color(0xFF272727)

val Blue500 = Color(0xff5376CE)

val LightColorScheme = lightColorScheme(
    background = Gray100,      // 앱의 기본 배경색
    onBackground = Black,      // 배경색 위에 표시될 텍스트/아이콘 색상
    surface = White,         // Card, Sheet 등 Surface 컴포넌트의 배경색
    onSurface = Black
)