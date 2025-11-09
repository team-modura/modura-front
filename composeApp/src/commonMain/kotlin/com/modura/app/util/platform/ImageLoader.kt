package com.modura.app.util.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun rememberImageBitmapFromUrl(
    url: String,
    onSuccess: (ImageBitmap) -> Unit,
    onFailure: (String) -> Unit
)