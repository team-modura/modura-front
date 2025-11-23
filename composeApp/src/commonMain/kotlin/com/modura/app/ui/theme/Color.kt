package com.modura.app.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val BlackTransparent = Color(0x000C0C0C)
val WhiteTransparent = Color(0x00F4F4F4)
val Black50P = Color(0x80000000)
val White = Color(0xFFFFFFFF)
val Gray100 = Color(0xFFF4F4F4)
val Gray300 = Color(0xffDADADA)
val Gray500 = Color(0xFFA8A8A8)
val Gray600 = Color(0xff868686)
val Gray700 = Color(0xff5D5D5D)
val Gray800 = Color(0xFF383838)
val Gray900 = Color(0xFF0C0C0C)
val Blue500 = Color(0xff5376CE)
val Green700 = Color(0xff8EC28E)
val Main500 = Color(0xff36A7A7)
val Main100 = Color(0xffC7DDDD)
val Main900 = Color(0xff003B3B)




val LightColorScheme = lightColorScheme(
    primary = Main500,
    background = Gray100,
    onBackground = Gray900,
    surface = White,
    surfaceVariant = Gray300,
    onSurfaceVariant = Gray700,
    onSurface = WhiteTransparent,
    onPrimary = Main100
)

val DarkColorScheme = darkColorScheme(
    primary = Main500,
    background = Gray900,
    onBackground = Gray100,
    surface = Black,
    surfaceVariant = Gray700,
    onSurfaceVariant = Gray300,
    onSurface = BlackTransparent,
    onPrimary = Main900
)