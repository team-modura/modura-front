package com.modura.app.util.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun rememberCameraManager(onResult: (ImageBitmap?) -> Unit): () -> Unit