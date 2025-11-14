package com.modura.app.util.platform

import androidx.compose.ui.graphics.ImageBitmap

expect fun saveBitmapToFile(context: Any, bitmap: ImageBitmap): String