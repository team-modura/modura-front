package com.modura.app.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Gray100 = Color(0xF4F4F4)
val Gray500 = Color(0xA8A8A8)

val LightColorScheme = lightColorScheme(
    background = Gray100,      // 앱의 기본 배경색
    onBackground = Black,      // 배경색 위에 표시될 텍스트/아이콘 색상
    surface = White,         // Card, Sheet 등 Surface 컴포넌트의 배경색
    onSurface = Black
)